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
		
		for (x = 1; x < (density[0].length - 1); x++) 
		{
			for (y = 1; y < (density.length - 1); y++) 
			{
				/**
				 * Calculate maximum density.
				 **/
				if (density[y][x] > maxValue)
				{
					maxValue = density[y][x];
				}
				
				/**
				 * Calculate minimum density.
				 **/
				if ((density[y][x] < minValue) && (neighbours[y][x] != -1)) 
				{
					minValue = density[y][x];
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
			
				bufferedWriter.write((density[0].length - 2) + " " + (density.length - 2));
				bufferedWriter.newLine();
			
				bufferedWriter.write("255");
				bufferedWriter.newLine();

				for (y = 1; y < (density.length - 1); y++) 
				{
					for (x = 1; x < (density[0].length - 1); x++)
					{
						
						/**
						 * Fill water cells with blue.
						 **/
						if (neighbours[y][x] == -1) 
						{
							bufferedWriter.write("0 0 255 ");
						}
						
						/**
						 * Fill land cells with grey with higher intensity corresponding to 
						 * greater value of density in a given cell.
						 **/
						else
						{
							bufferedWriter.write((int) ((density[y][x] - minValue) * scale)
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

		for (x = 1; x < (density[0].length - 1); x++) 
		{
			for (y = 1; y < (density.length - 1); y++) 
			{
				sum += density[y][x];
			}
		}

		average = sum/ ((double) (density.length - 1) * (double) (density[0].length - 1));

		out.printf("%f %f\n", time, average);
		out.close();
	}

}
