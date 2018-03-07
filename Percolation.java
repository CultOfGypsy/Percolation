package a01;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * Class Percolation models a percolation system using an N-by-N grid of sites.
 * @Authors: Laurel Miller and Jason Carter
 * Date 1/2/2018
 */

public class Percolation {
	int N;
	private boolean[][] grid;
	private WeightedQuickUnionUF connector;
	private int top; // N * N + 1
	private int bottom; // N * N + 2
	private int counterForOpenSites = 0;

	/**
	 * Creates N-­by-­N grid, with all sites blocked
	 * 
	 * @param N The N represents number of vertical/horizontal sites in the grid.
	 */
	public Percolation(int N) {
		if (N <= 0) {
			throw new java.lang.IllegalArgumentException("Size must be greater than 0");
		}
		this.N = N;
		connector = new WeightedQuickUnionUF(N * N + 2);
		grid = new boolean[N][N];
		top = N * N;
		bottom = N * N + 1;
	}

	/**
	 * open site (row i, column j) if it is not open already
	 * 
	 * @param i The i represents a row.
	 * @param j The j represents a column.
	 */
	public void open(int i, int j) {
		if (grid[i][j] == true) {
			return;
		}
		grid[i][j] = true;
		counterForOpenSites++;
		
		// converts from i, j to the matching index on grid
		int thisSpace = convertToOneDimension(i, j);

		if (i == 0) {
			connector.union(thisSpace, top);
		}
		if (i == N - 1) {
			connector.union(thisSpace, bottom);
		}
		// check left neighbor
		if (i > 0 && isOpen(i - 1, j)) {
			int checkLeft = convertToOneDimension(i - 1, j);
			connector.union(thisSpace, checkLeft);
		}
		// check right neighbor
		if (i < N - 1 && isOpen(i + 1, j)) {
			int checkRight = convertToOneDimension(i + 1, j);
			connector.union(thisSpace, checkRight);
		}
		// check top neighbor
		if (j > 0 && isOpen(i, j - 1)) {
			int checkUp = convertToOneDimension(i, j - 1);
			connector.union(thisSpace, checkUp);
		}
		// check bottom neighbor
		if (j < N - 1 && isOpen(i, j + 1)) {
			int checkDown = convertToOneDimension(i, j + 1);
			connector.union(thisSpace, checkDown);
		}

	}

	/*
	 * isOpen checks whether site (row i, column j) is open
	 * @param i The i represents a row. 
	 * @param j The j represents a column.
	 * @returns true or false if open
	 */
	public boolean isOpen(int i, int j) {
		return grid[i][j];
	}

	/*
	 * This checks whether the site (row i, column j) is full.
	 * @param i The i represents a row.
	 * @param j The j represents a column.
	 * 
	 * @returns true or false if full
	 */
	public boolean isFull(int i, int j) {
		int thisSpace = convertToOneDimension(i, j);
		return connector.connected(thisSpace, top);
	}

	/*
	 * This checks whether the system percolates.
	 * @returns true or false based on if the top and bottom are connected
	 */
	public boolean percolates() {
	
		return connector.connected(top, bottom);
	}

	//This method converts from our 2D array to 1D array
	private int convertToOneDimension(int i, int j) {
		return i * N + j;

	}

	/**
	 *  This counts number of open sites calculated in percolation.
	 * @returns integer number of open sites
	 */
	public int numberOfOpenSites() {
		return counterForOpenSites;
	}

	/**********************************************************************/
	public static void main(String[] args) {
		int size = 10;
		int i = 0;
		int j = 0;
		Percolation perc = new Percolation(size);
		while (!perc.percolates()) {
			perc.open(i, j);
			i = StdRandom.uniform(0, size);
			j = StdRandom.uniform(0, size);
			StdOut.println("" + i + " " + j);
		}
	}
}
