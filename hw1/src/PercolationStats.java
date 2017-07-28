/**
 * Created by jia on 7/29/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int times;
    private double[] result;
    private double x = 0;
    private double s = 0;
    private double low = 0;
    private double high = 0;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0)
            throw new IllegalArgumentException("Trials should be a positive number!");
        times = trials;
        result = new double[times];

        for (int i = 0; i < times; i++) {
            Percolation grids = new Percolation(n);
            while (!grids.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                grids.open(row, col);
            }
            result[i] = (double) grids.numberOfOpenSites() / (n * n);
        }

        x = StdStats.mean(result);
        s = StdStats.stddev(result);
        low = x - 1.96 * s / Math.sqrt(times);
        high = x + 1.96 * s / Math.sqrt(times);
    }

    // sample mean of percolation threshold
    public double mean()  {
        return x;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return  s;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()  {
        return low;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return high;
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(4, 1);

        System.out.println("mean   =  " + test.mean());
        System.out.println("stddev   =  " + test.stddev());
        System.out.println("confidence   =  [" + test.confidenceLo() + " ," + test.confidenceHi() + "]");
    }

}
