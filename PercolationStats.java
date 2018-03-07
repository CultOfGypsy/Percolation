package a01;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * PercolationStats perform a series of computational experiments on class Percolation
 * @authors Jason Carter & Laurel Miller
 * Date: 02/01/2018 
 */
public class PercolationStats {
	/**
	 * Performs T independent experiments on an N­by­N grid
	 * @param N The N is the number of sites on the grid
	 * @param T The T is the number or experiments or trials
	 */
	private int trials;
	private double[] thresholds;
	private double low, high, mean, stdDev;


	/**
	 * This constructor creates the PercolationStats class and also sets up the mean,
	 * stdev, confidence low & high methods.
	 * @param N The N represents the number of sites
	 * @param T The T represents the number of trials.
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException("The number needs to be greater than 0");
		}
		trials = T;
		thresholds = new double[T];

		//Iterates through trials and keeps track of results
		for (int i = 0; i < trials; i++) {
			Percolation newTrial = new Percolation(N);
			double openSites = 0.0;

			//Per trial
			while (newTrial.percolates() == false) {
				int randNum1 = StdRandom.uniform(N);
				int randNum2 = StdRandom.uniform(N);
				if (newTrial.isOpen(randNum1, randNum2) == false) {
					newTrial.open(randNum1, randNum2);
					openSites ++;
				}

			}
			//Percentage of openSites tally
			thresholds[i] = openSites / (N * N);
		}
		
		//This sets up the methods in use below
		mean = StdStats.mean(thresholds);
		stdDev = StdStats.stddev(thresholds);
		double limit = 1.96 * stdDev/ Math.sqrt(trials);
		low = mean - limit;
		high = mean + limit;
	}

	/**
	 *  Samples mean of percolation threshold 
	 * @return mean. The mean is the total / number of items.
	 */
	public double mean() {
		return mean;
	}

	/**
	 *  The sample standard deviation of percolation threshold
	 * @return This returns the standard deviation calculation.
	 */
	public double stddev() {
		return stdDev;
	}

	/**
	 * The low endpoint of 95% confidence interval
	 * @return This returns the low endpoint calculation in double form.
	 */
	public double confidenceLow() {
		return low;
	}

	/**
	 * The high endpoint of 95% confidence interval
	 * @return This returns the high endpoint calculation in double form.
	 */
	public double confidenceHigh() {
		return high;
	}
}
