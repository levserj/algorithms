import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by sl on 22.05.17.
 */
public class PercolationStats {
    private double[] experiments;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials is less than zero");
        }
        this.trials = trials;
        experiments = new double[trials];
        int row;
        int col;
        Percolation p;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            experiments[i] = (double) p.numberOfOpenSites() / (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(experiments);
    }

    public double stddev() {
        return StdStats.stddev(experiments);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
