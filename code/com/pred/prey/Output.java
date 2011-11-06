package com.pred.prey;

import java.io.*;

/**
 * Method to write output in ppm files.
 * 
 * @author Milena, Tom Put your name here if you work on this class
 * @version 6.0, October, 28th 2011
 * @since 1.0
 */

public class Output {
	int x, y;
	double maxValue, minValue;

	int[][][] cell;
	double[][] density;

	public Output() {
	}

	/**
	 * Method to write densities into a PPM file In the final output, water will
	 * be seen as blue and land as green cells. The change in density of animals
	 * with position will be represented by different shades of grey (the higher
	 * the density the darker the colour).
	 * 
	 * @param outputName
	 * @param density
	 * @param colour
	 *            optional
	 **/
	public void printPpm(String outputName, double[][] density,
			int[][] neighbours) {
		/**
		 * Calculate the maximum density.
		 **/
		maxValue = 0;
		minValue = 10; // have to guess at a good starting point here
		for (x = 1; x < (density.length - 1); x++) {
			for (y = 1; y < (density[0].length - 1); y++) {
				if (density[y][x] > maxValue) {
					maxValue = density[y][x];
				}
				if ((density[y][x] < minValue) && (neighbours[y][x] != -1)) {
					minValue = density[y][x];
				}
			}
		}
		double scale = 250 / (maxValue - minValue); // actual max will be 255,
													// leave room for error

		/**
		 * Print the values in a ppm file.
		 **/

		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(
					new FileWriter(outputName, true));
			bufferedWriter.write("P3");

			bufferedWriter.newLine();
			bufferedWriter.write((density.length - 2) + " "
					+ (density[0].length - 2));
			bufferedWriter.newLine();
			bufferedWriter.write("255");
			bufferedWriter.newLine();

			for (y = 1; y < (density[0].length - 1); y++) {
				for (x = 1; x < (density.length - 1); x++) {
					/**
					 * Fill water cells with blue.
					 **/
					if (neighbours[y][x] == -1) {
						bufferedWriter.write("0 0 255 ");
					} else {
						bufferedWriter
								.write((int) ((density[y][x] - minValue) * scale)
										+ " "
										+ (int) ((density[y][x] - minValue) * scale)
										+ " "
										+ (int) ((density[y][x] - minValue) * scale)
										+ " ");
					}

				}
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	/**
	 * Method to write average density with corresponding time to a file
	 * 
	 * @param outputName
	 * @param density
	 * @param time
	 **/
	public void printMeanDensity(String outputName, double[][] density,
			double time) {		
		double sum = 0;
		double average;
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(new FileWriter(outputName,
					true));
		} catch (IOException ioe) {
			ioe.getMessage();
		}

		for (x = 1; x < (density.length - 1); x++) {
			for (y = 1; y < (density[x].length - 1); y++) {
				sum += density[y][x];
			}
		}

		average = sum
				/ ((double) (density.length - 1) * (double) (density[0].length - 1));

		out.printf("%f %f\n", time, average);
		out.close();
	}

}
