import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by sl on 22.05.17.
 */
public class Percolation {
    // Grid size
    private int top = 0;
    private int bottom;
    private int n;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF qf;
    private boolean[][] lattice;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Wrong n : " + n);
        }
        this.n = n;
        lattice = new boolean[n + 1][n + 1];
        bottom = n * n + 1;
        qf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IndexOutOfBoundsException("row or col number is wrong");
        }
        if (isOpen(row, col)) {
            return;
        }
        lattice[row][col] = true;
        int currentCellQFNo = convertToQF(row, col);
        numberOfOpenSites++;

        if (row == 1) {
            qf.union(currentCellQFNo, top);
        }

        if (row == n) {
            qf.union(currentCellQFNo, bottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(currentCellQFNo, convertToQF(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            qf.union(currentCellQFNo, convertToQF(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(currentCellQFNo, convertToQF(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            qf.union(currentCellQFNo, convertToQF(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IndexOutOfBoundsException("row or col number is wrong");
        }
        return lattice[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IndexOutOfBoundsException("row or col number is wrong");
        }
        return qf.connected(convertToQF(row, col), top);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return qf.connected(top, bottom);
    }

    private boolean isValidInput(int... args) {
        for (int a : args) {
            if (!isValidInt(a)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInt(int a) {
        return a >= 1 && a <= n;
    }

    private int convertToQF(int row, int col) {
        return n * (row - 1) + col;
    }
}
