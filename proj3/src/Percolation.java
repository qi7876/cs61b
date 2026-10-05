import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int N;
    WeightedQuickUnionUF dsPercolation;
    WeightedQuickUnionUF dsFull;
    int virtualTopID;
    int virtualBottomID;
    boolean[][] openMatrix;
    int openSitesCounter;

    public Percolation(int N) {
        this.N = N;
        this.dsPercolation = new WeightedQuickUnionUF(N * N + 2);
        this.dsFull = new WeightedQuickUnionUF(N * N + 1);
        this.virtualTopID = N * N;
        this.virtualBottomID = N * N + 1;
        this.openMatrix = new boolean[N][N];
        this.openSitesCounter = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        openMatrix[row][col] = true;
        openSitesCounter += 1;

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
        checkOpenAndTakeUnion(siteID, row + 1, col);
        checkOpenAndTakeUnion(siteID, row - 1, col);
        checkOpenAndTakeUnion(siteID, row, col + 1);
        checkOpenAndTakeUnion(siteID, row, col - 1);
    }

    public boolean isOpen(int row, int col) {
        return this.openMatrix[row][col];
    }

    public boolean isFull(int row, int col) {
        if (this.isOpen(row, col) && this.dsFull.find(getID(row, col)) == this.dsFull.find(this.virtualTopID)) {
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

    private void checkOpenAndTakeUnion(int siteID, int row, int col) {
        if (verifyPosition(row, col) && isOpen(row, col)) {
            this.dsPercolation.union(siteID, getID(row, col));
            this.dsFull.union(siteID, getID(row, col));
        }
    }
}
