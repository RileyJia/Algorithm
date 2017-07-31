/**
 * Created by jia on 7/28/17.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid, backWash;
    private boolean[] state;
    private int N;
    private int siteCount;
    private int openSiteCount = 0;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N should be a positive number!");

        N = n;
        siteCount = n * n;
        state = new boolean[siteCount + 2];

        grid = new WeightedQuickUnionUF(siteCount+2);
        backWash = new WeightedQuickUnionUF(siteCount+2);

        for (int i = 1; i <= siteCount; i++) {
            state[i] = false;
        }
        state[0] = true;
        state[siteCount + 1] = true;
    }

    // open site (row, col) if it is not open already
    // First, it should validate the indices of the site that it receives.
    // Second, it should somehow mark the site as open.
    // Third, it should perform some sequence of WeightedQuickUnionUF operation
    // that links the site in question to its open neighbors.
    // The constructor and instance variables should facilitate the open() method's ability to do its job.
    public    void open(int row, int col) {
        validate(row, col);
        int position = xyTo1D(row, col);
        if (!isOpen(row, col)) {
            state[position] = true;
            openSiteCount++;

            if (row == 1) {
                grid.union(0, position);
                backWash.union(0, position);
            }

            if (row == N) { //&& grid.find(position) == grid.find(0)){
                // if (grid.connected(position, 0)) {
                //     backWash.union(siteCount+1, position);
                // }
                grid.union(siteCount+1, position);
            }

            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(xyTo1D(row - 1, col), position);
                backWash.union(xyTo1D(row - 1, col), position);
            }
            if (row < N && isOpen(row + 1, col)) {
                grid.union(xyTo1D(row + 1, col), position);
                backWash.union(xyTo1D(row + 1, col), position);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(xyTo1D(row, col - 1), position);
                backWash.union(position - 1, position);
            }
            if (col < N && isOpen(row, col+1)) {
                grid.union(xyTo1D(row, col + 1), position);
                backWash.union(position + 1, position);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return state[xyTo1D(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && backWash.connected(xyTo1D(row, col), 0);
    }

    // number of open sites
    public     int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, siteCount+1);
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * N + col;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > N) {
            throw new IllegalArgumentException("row " + row +  " is not between 0 and " + N);
        }
        if (col <= 0 || col > N) {
            throw new IllegalArgumentException("column" + col + " is not between 0 and " + N);
        }
    }

    public static void main(String[] args) {

    }
}

