import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests popularity filtering through the same handler used by the browser. */
public class TestNonzeroKHyponyms {
    @TempDir
    Path dataDirectory;

    private NgordnetQueryHandler handler;

    @BeforeEach
    public void setUp() throws IOException {
        // fruit and snack share apple and cherry. date has zero counts;
        // elderberry is in WordNet but has no NGram history.
        Path synsets = dataDirectory.resolve("synsets.txt");
        Files.writeString(synsets, """
                0,fruit,dummy
                1,apple,dummy
                2,banana,dummy
                3,cherry,dummy
                4,date,dummy
                5,elderberry,dummy
                6,snack,dummy
                """);
        Path hyponyms = dataDirectory.resolve("hyponyms.txt");
        Files.writeString(hyponyms, """
                0,1,2,3,4,5
                6,1,3
                """);
        Path words = dataDirectory.resolve("words.csv");
        Files.writeString(words, """
                apple\t1999\t1000\t1
                apple\t2000\t30\t1
                apple\t2001\t30\t1
                apple\t2003\t1000\t1
                banana\t2000\t50\t1
                banana\t2001\t1\t1
                cherry\t2000\t5\t1
                cherry\t2001\t4\t1
                cherry\t2002\t100\t1
                date\t2000\t0\t1
                """);
        Path years = dataDirectory.resolve("years.csv");
        Files.writeString(years, """
                1999,10000,100,1
                2000,10000,100,1
                2001,10000,100,1
                2002,10000,100,1
                2003,10000,100,1
                """);
        handler = AutograderBuddy.getHyponymsHandler(
                words.toString(), years.toString(), synsets.toString(), hyponyms.toString());
    }

    @Test
    public void testTopKReturnedInAlphabeticalOrder() {
        // cherry totals 109, apple 60, banana 51; select the top two, then alphabetize.
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2000, 2002, 2)))
                .isEqualTo("[apple, cherry]");
    }

    @Test
    public void testCountsAreSummedAcrossYears() {
        // banana has the largest single count, but apple has the largest total (60 > 51).
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2000, 2001, 1)))
                .isEqualTo("[apple]");
    }

    @Test
    public void testYearRangeIsInclusiveAndExcludesOutsideCounts() {
        // The 2002 count makes cherry win; apple's large 1999/2003 counts are excluded.
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2001, 2002, 1)))
                .isEqualTo("[cherry]");
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2000, 2000, 1)))
                .isEqualTo("[banana]");
    }

    @Test
    public void testLargeKExcludesZeroAndMissingCounts() {
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2000, 2002, 10)))
                .isEqualTo("[apple, banana, cherry]");
    }

    @Test
    public void testNoCountsInYearRangeReturnsEmptyList() {
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2010, 2020, 3)))
                .isEqualTo("[]");
    }

    @Test
    public void testIntersectionIsComputedBeforeSelectingTopK() {
        // banana wins for fruit in 2000, but is not a hyponym of snack.
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit", "snack"), 2000, 2000, 1)))
                .isEqualTo("[apple]");
    }

    @Test
    public void testDisjointWordsReturnEmptyList() {
        assertThat(handler.handle(new NgordnetQuery(List.of("apple", "banana"), 2000, 2002, 2)))
                .isEqualTo("[]");
    }

    @Test
    public void testZeroKIgnoresCountsAndYearRange() {
        assertThat(handler.handle(new NgordnetQuery(List.of("fruit"), 2010, 2020, 0)))
                .isEqualTo("[apple, banana, cherry, date, elderberry, fruit]");
    }
}
