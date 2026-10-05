import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you
     * write tests.
     * <p>
     * (0) CLOSED: isOpen() returns false, isFull() returns false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     * (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                { 0, 1 },
                { 2, 0 },
                { 3, 1 },
                { 4, 1 },
                { 1, 0 },
                { 1, 1 }
        };
        Cell[][] expectedState = {
                { Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED },
                { Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED },
                { Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED },
                { Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED },
                { Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED }
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                { Cell.FULL }
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void regionBecomesFullWhenConnectedToTop() {
        Percolation p = new Percolation(3);
        p.open(2, 1);
        p.open(1, 1);

        assertThat(p.isFull(1, 1)).isFalse();
        assertThat(p.isFull(2, 1)).isFalse();
        assertThat(p.percolates()).isFalse();

        p.open(0, 1);

        assertThat(p.isFull(1, 1)).isTrue();
        assertThat(p.isFull(2, 1)).isTrue();
        assertThat(p.percolates()).isTrue();
        assertThat(p.numberOfOpenSites()).isEqualTo(3);
    }

    @Test
    public void isolatedBottomSiteDoesNotBecomeFull() {
        Percolation p = new Percolation(3);
        p.open(2, 2); // Isolated bottom site.

        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0); // Complete a separate top-to-bottom path.

        assertThat(p.percolates()).isTrue();
        assertThat(p.isOpen(2, 2)).isTrue();
        assertThat(p.isFull(2, 2)).isFalse();
    }

    @Test
    public void freshGrid() {
        Percolation p = new Percolation(10);
        assertThat(p.percolates()).isFalse();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertThat(p.isOpen(i, j)).isFalse();
                assertThat(p.isFull(i, j)).isFalse();
            }
        }
    }

    @Test
    public void openCounter() {
        Percolation p = new Percolation(3);
        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        p.open(2, 2);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        assertThat(p.numberOfOpenSites()).isEqualTo(4);
    }

    @Test
    public void noBackwash() {
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        p.open(2, 2);
        assertThat(p.isFull(2, 2)).isFalse();
    }

    @Test
    public void propertyTest() {
        // Enumerate every arrangement, including the empty and completely open grids.
        for (int N = 1; N <= 3; N++) {
            for (int mask = 0; mask < (1 << (N * N)); mask++) {
                Percolation p = new Percolation(N);
                for (int site = 0; site < N * N; site++) {
                    if ((mask & (1 << site)) != 0) {
                        p.open(site / N, site % N);
                    }
                }

                Cell[][] expected = getFloodFillState(N, mask);
                boolean expectedPercolation = false;
                for (int col = 0; col < N; col++) {
                    expectedPercolation |= expected[N - 1][col] == Cell.FULL;
                }

                assertWithMessage("State for N=%s, mask=%s", N, mask)
                        .that(getState(N, p)).isEqualTo(expected);
                assertWithMessage("Open count for N=%s, mask=%s", N, mask)
                        .that(p.numberOfOpenSites()).isEqualTo(Integer.bitCount(mask));
                assertWithMessage("Percolation for N=%s, mask=%s", N, mask)
                        .that(p.percolates()).isEqualTo(expectedPercolation);
            }
        }
    }

    @Test
    public void openingOrderDoesNotAffectState() {
        int N = 3;
        for (int mask = 0; mask < (1 << (N * N)); mask++) {
            Percolation forward = new Percolation(N);
            Percolation reverse = new Percolation(N);
            for (int site = 0; site < N * N; site++) {
                if ((mask & (1 << site)) != 0) {
                    forward.open(site / N, site % N);
                }
            }
            for (int site = N * N - 1; site >= 0; site--) {
                if ((mask & (1 << site)) != 0) {
                    reverse.open(site / N, site % N);
                }
            }

            assertWithMessage("Opening order for mask=%s", mask)
                    .that(getState(N, reverse)).isEqualTo(getState(N, forward));
            assertThat(reverse.numberOfOpenSites()).isEqualTo(forward.numberOfOpenSites());
            assertThat(reverse.percolates()).isEqualTo(forward.percolates());
        }
    }

    @Test
    public void repeatedOpeningDoesNotChangeState() {
        int N = 3;
        for (int mask = 0; mask < (1 << (N * N)); mask++) {
            Percolation p = new Percolation(N);
            for (int site = 0; site < N * N; site++) {
                if ((mask & (1 << site)) != 0) {
                    p.open(site / N, site % N);
                }
            }
            Cell[][] before = getState(N, p);
            int countBefore = p.numberOfOpenSites();
            boolean percolatedBefore = p.percolates();

            for (int site = 0; site < N * N; site++) {
                if ((mask & (1 << site)) != 0) {
                    p.open(site / N, site % N);
                    assertWithMessage("Reopening site=%s, mask=%s", site, mask)
                            .that(getState(N, p)).isEqualTo(before);
                    assertThat(p.numberOfOpenSites()).isEqualTo(countBefore);
                    assertThat(p.percolates()).isEqualTo(percolatedBefore);
                }
            }
        }
    }

    /** Computes fullness independently by exploring open paths from the top row. */
    private static Cell[][] getFloodFillState(int N, int mask) {
        Cell[][] state = new Cell[N][N];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                state[row][col] = (mask & (1 << (row * N + col))) == 0
                        ? Cell.CLOSED
                        : Cell.OPEN;
                if (row == 0 && state[row][col] == Cell.OPEN) {
                    state[row][col] = Cell.FULL;
                    queue.add(new int[] { row, col });
                }
            }
        }

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!queue.isEmpty()) {
            int[] site = queue.remove();
            for (int[] direction : directions) {
                int row = site[0] + direction[0];
                int col = site[1] + direction[1];
                if (row >= 0 && row < N && col >= 0 && col < N
                        && state[row][col] == Cell.OPEN) {
                    state[row][col] = Cell.FULL;
                    queue.add(new int[] { row, col });
                }
            }
        }
        return state;
    }
}
