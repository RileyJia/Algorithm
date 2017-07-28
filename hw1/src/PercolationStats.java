/**
 * Created by jia on 7/29/17.
 */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private Percolation grids;
    int times;
    private int[] result;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n<=0)
            throw new IllegalArgumentException("Trials should be a positive number!");
        times = trials;
        result = new int[times];

        for(int i = 0; i < times; i++) {
            grids = new Percolation(n);
            while (!grids.percolates()) {
                int row = StdRandom.uniform(1, n);
                int col = StdRandom.uniform(1, n);
                grids.open(row, col);
            }
            result[i] = grids.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean()  {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return  StdStats.stddev(result);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()  {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(200,100);
        
        System.out.println("mean   =  " + test.mean());
        System.out.println("stddev   =  " + test.stddev());
        System.out.println("confidence   =  [" + test.confidenceLo() + " ," + test.confidenceHi() + "]");
    }

}
