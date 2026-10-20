import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Tests for the TimeSeries class.
 *
 * @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        // 1992: 100
        // 1994: 600
        // 1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testCopyTSEmpty() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries(catPopulation, 2005, 2026);

        assertThat(dogPopulation.years()).isEmpty();
        assertThat(dogPopulation.data()).isEmpty();
    }

    @Test
    public void testCopyTS() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(2005, 500.0);
        catPopulation.put(2016, 700.0);
        catPopulation.put(2026, 1000.0);

        TimeSeries dogPopulation = new TimeSeries(catPopulation, 2005, 2026);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(2005);
        expectedYears.add(2016);
        expectedYears.add(2026);

        assertThat(dogPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(500.0);
        expectedTotal.add(700.0);
        expectedTotal.add(1000.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(dogPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testDividedBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 50.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 150.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 2.0);
        dogPopulation.put(1992, 4.0);
        dogPopulation.put(1993, 3.0);
        dogPopulation.put(1994, 10.0);
        dogPopulation.put(1995, 1.0);

        TimeSeries result = catPopulation.dividedBy(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1993);
        expectedYears.add(1994);

        assertThat(result.years()).isEqualTo(expectedYears);

        List<Double> expectedResult = new ArrayList<>();
        expectedResult.add(25.0);
        expectedResult.add(25.0);
        expectedResult.add(50.0);
        expectedResult.add(20.0);

        for (int i = 0; i < expectedResult.size(); i += 1) {
            assertThat(result.data().get(i)).isWithin(1E-10).of(expectedResult.get(i));
        }
    }

    @Test
    public void testDividedByLackOfYears() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 50.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 150.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 2.0);
        dogPopulation.put(1992, 4.0);

        assertThrows(IllegalArgumentException.class, () -> {
            catPopulation.dividedBy(dogPopulation);
        });
    }
}
