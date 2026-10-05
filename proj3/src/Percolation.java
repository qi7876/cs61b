import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int N;
    WeightedQuickUnionUF dsPercolation;
    WeightedQuickUnionUF dsFull;
    int virtualTopID;
    int virtualBottomID;
    boolean[][] open_matrix;
    int openSitesCounter;

    public Percolation(int N) {
        this.N = N;
        this.dsPercolation = new WeightedQuickUnionUF(N * N + 2);
        this.dsFull = new WeightedQuickUnionUF(N * N + 1);
        this.virtualTopID = N * N;
        this.virtualBottomID = N * N + 1;
        this.open_matrix = new boolean[N][N];
        this.openSitesCounter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.open_matrix[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        open_matrix[row][col] = true;
        openSitesCounter += 1;

        SiteStatus[] neighborStats = checkNeighborSitesOpenAndFull(row, col);
        int siteID = getID(row, col);
        // sits on the first row should take the union with virtual top
        if (row == 0) {
            this.dsPercolation.union(siteID, this.virtualTopID);
            this.dsFull.union(siteID, this.virtualTopID);
        }
        // sits on the last row should take the union with virtual bottom
        if (row == this.N - 1) {
            this.dsPercolation.union(siteID, this.virtualBottomID);
        }

        // order: up, down, left, right
        if (neighborStats[0] == SiteStatus.OPEN) {
            this.dsPercolation.union(siteID, getID(row + 1, col));
            this.dsFull.union(siteID, getID(row + 1, col));
        }
        if (neighborStats[1] == SiteStatus.OPEN) {
            this.dsPercolation.union(siteID, getID(row - 1, col));
            this.dsFull.union(siteID, getID(row - 1, col));
        }
        if (neighborStats[2] == SiteStatus.OPEN) {
            this.dsPercolation.union(siteID, getID(row, col + 1));
            this.dsFull.union(siteID, getID(row, col + 1));
        }
        if (neighborStats[3] == SiteStatus.OPEN) {
            this.dsPercolation.union(siteID, getID(row, col - 1));
            this.dsFull.union(siteID, getID(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return this.open_matrix[row][col];
    }

    public boolean isFull(int row, int col) {
        if (this.dsFull.find(getID(row, col)) == this.dsFull.find(this.virtualTopID)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return this.openSitesCounter;
    }

    public boolean percolates() {
        if (this.dsPercolation.find(this.virtualBottomID) == this.dsPercolation.find(this.virtualTopID)) {
            return true;
        }
        return false;
    }

    private int getID(int row, int col) {
        return row * this.N + col;
    }

    private boolean verifyPosition(int row, int col) {
        if (row < 0 || row > this.N - 1) {
            return false;
        }
        if (col < 0 || col > this.N - 1) {
            return false;
        }
        return true;
    }

    private enum SiteStatus {
        ILLEGAL,
        BLOCKED,
        OPEN,
    }

    private SiteStatus[] checkNeighborSitesOpenAndFull(int row, int col) {
        SiteStatus up = checkOneSiteOpen(row + 1, col);
        SiteStatus down = checkOneSiteOpen(row - 1, col);
        SiteStatus left = checkOneSiteOpen(row, col + 1);
        SiteStatus right = checkOneSiteOpen(row, col - 1);
        return new SiteStatus[] { up, down, left, right };
    }

    private SiteStatus checkOneSiteOpen(int row, int col) {
        if (verifyPosition(row, col)) {
            if (isOpen(row, col)) {
                return SiteStatus.OPEN;
            }
            return SiteStatus.BLOCKED;
        }
        return SiteStatus.ILLEGAL;
    }
}
