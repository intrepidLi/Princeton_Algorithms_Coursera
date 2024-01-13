import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static double[] p;
    private double mean_value;
    private double stddev;
    private double confidencehi;
    private double confidencelo;
    private int num;
    private  int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.num = n;
        this.trials = trials;
        p = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                // StdOut.print("i is " + i + "\n");
                int row;
                int col;
                do {
                    row = StdRandom.uniformInt(1, n + 1);
                    col = StdRandom.uniformInt(1, n + 1);
                } while (percolation.isOpen(row, col));

                 // StdOut.println("row is " + row + "\ncol is " + col);
                percolation.open(row, col);
            }
            // percolation.printFull();
            p[i] = ((double) percolation.numberOfOpenSites()) / (n * n);
            // StdOut.print("#############################\n");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean_value = StdStats.mean(p);
        return mean_value;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(p);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        confidencelo = mean() - (1.96 * stddev()) / Math.sqrt(p.length);
        return confidencelo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        confidencehi = mean() + (1.96 * stddev()) / Math.sqrt(p.length);
        return confidencehi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        // StdOut.print(n);
        // StdOut.print("\n");
        // StdOut.print(trials);
        // StdOut.print("\n");
        PercolationStats percolationStats = new PercolationStats(n, trials);
        String str1 = "mean                    = " + percolationStats.mean();
        String str2 = "stddev                  = " + percolationStats.stddev();
        String str3 = "95% confidence interval = [" +
                percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]";
        StdOut.println(str1);
        StdOut.println(str2);
        StdOut.println(str3);
    }
}
