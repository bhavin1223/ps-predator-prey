package com.pred.prey;

import java.io.*;

/**
 * This class contains methods to create/clean output directories and to write data to output files. 
 * 
 * @author Milena, Tom 
 * @version 6.0, October, 28th 2011
 * @since 1.0
 **/

public class Output {
	
	int x, y;
	double maxValue, minValue;
	boolean direrror = false;
	double[][] density;

	public Output() {
	}

  /**
  * Method to create a directory 'dirname' if it doesn't exists already,
  * or to clean it if it does.
  * @param dirname
  **/
	
	public void cleanDirectory(String dirname) {
	
		File directory = new File(dirname);

		/**
		 *Check if directory 'dirname' exists and if it does, delete all files it contains.
		 **/
		if(directory.exists()) 
		{
			File[] files = directory.listFiles();
		
			System.out.println("Cleaning "+dirname+" directory...");
		
			for (File file : files) 
			{
				if (!file.delete())
				{
					//failed to delete file
					System.out.println("Failed to delete "+file);
				}
			}	

		}
		/**
		 *Otherwise create directory 'dirname'.
		 **/
		else 
		{
			System.out.println("No output directory found, creating "+dirname+" directory.");
			
			if(!directory.mkdir())
			{
				direrror=true;
			}
		}
	
	}
	
	
	 /**
	 * Method to write densities of animals into a PPM file.
	 * The densities are rescaled so that the maximum value is 255
	 * and used as intensity values (three for each pixel, for red, green and blue). 
	 * @param outputName
	 * @param density
	 * @param colour
	 **/
	
	public void printPpm(String outputName, double[][] density, int[][] neighbours) {
		
		
		maxValue = 0;
		minValue = 10; // have to guess at a good starting point here
		
		for (x = 1; x < density.length - 1; x++) {
			for (y = 1; y < density[0].length - 1; y++) {
				if (density[x][y] > maxValue) {
					maxValue = density[x][y];
				}
				
				if ((density[x][y] < minValue) && (neighbours[x][y] != -1)) {
					minValue = density[x][y];
				}
			}
		}
		
		/**
		 * Compute the scale factor.
		 **/
		double scale = 255 / (maxValue - minValue); 
													// leave room for error

		/**
		 * Print the values in a PPM file.
		 **/
		BufferedWriter bufferedWriter = null;
		
		try {
				bufferedWriter = new BufferedWriter(new FileWriter(outputName, true));
			
				bufferedWriter.write("P3");
				bufferedWriter.newLine();
			
				bufferedWriter.write((density.length - 2) + " " + (density[0].length - 2));
				bufferedWriter.newLine();
			
				bufferedWriter.write("255");
				bufferedWriter.newLine();

				for (x = 1; x < density.length - 1; x++) {
					for (y = 1; y < density[0].length - 1; y++) {

						/**
						 * Fill water cells with blue.
						 **/
						if (neighbours[x][y] == -1) {
							bufferedWriter.write("0 0 255 ");
						} else {
							bufferedWriter
									.write((int) ((density[x][y] - minValue) * scale)
											+ " "
											+ (int) ((density[x][y] - minValue) * scale)
											+ " "
											+ (int) ((density[x][y] - minValue) * scale)
											+ " ");
						}

					}
					bufferedWriter.newLine();
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			} 
		catch (IOException ioe) {
			ioe.getMessage();
			}
		}

	
	/**
	 * Method to write the average density with corresponding time to a file.
	 * @param outputName
	 * @param density
	 * @param time
	 **/
	
	public void printMeanDensity(String outputName, double[][] density,double time) {		
		
		double sum = 0;
		double average;
		
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(new FileWriter(outputName,true));
			} 
		catch (IOException ioe) {
			ioe.getMessage();
			}

		for (x = 1; x < (density.length - 1); x++) {
			for (y = 1; y < (density[0].length - 1); y++) {
				sum += density[x][y];
			}
		}

		average = sum/ ((double) (density.length - 2) * (double) (density[0].length - 2));

		out.printf("%f %f\n", time, average);
		out.close();
	}

}
