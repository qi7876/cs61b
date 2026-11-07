import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertThat(so.hashCode()).isEqualTo(hashCode);
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertThat(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10)).isTrue();
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            int M = StdRandom.uniformInt(1, 10);
            ArrayList<Integer> params = new ArrayList<>(M + 4);
            for (int j = 0; j < M; j += 1) {
                params.add(StdRandom.uniformInt(0, 256));
            }
            params.add(1);
            params.add(1);
            params.add(1);
            params.add(1);
            deadlyList.add(new ComplexOomage(params));
        }

        assertThat(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10)).isTrue();
    }
}
