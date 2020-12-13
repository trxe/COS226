import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private final int n;
    private final int trials;
    private final double[] values;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials, int version) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        double[] vals = new double[trials];
        for (int i = 0; i < trials; i++) {
            Perc perc = PercolationStats.supplyPerc(n, version);
            vals[i] = ((double) perc.numberOfOpenSites()) / ((double) n * n);
        }
        this.values = vals;
    }

    private static Perc supplyPerc(int n, int ver) {
        Perc perc;
        switch (ver) {
            case 0:
                perc = new Percolation(n);
                break;
            case 1:
                perc = new PercolationWQU(n);
                break;
            default:
                throw new IllegalStateException();
        }

        while (!perc.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            perc.open(row, col);
        }
        return perc;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            int version = Integer.parseInt(args[2]);
            Stopwatch watch = new Stopwatch();
            PercolationStats ps = new PercolationStats(n, trials, version);
            double time = watch.elapsedTime();
            System.out.println("mean\t\t\t: " + ps.mean());
            System.out.println("stddev\t\t\t: " + ps.stddev());
            System.out.println("95% confidence interval\t: [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
            System.out.printf("time\t\t\t: %fs\n", time);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: n <= 0");
        } catch (IllegalStateException e) {
            System.err.println("Invalid version");
        }
    }

}
