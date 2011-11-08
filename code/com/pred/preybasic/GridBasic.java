package com.pred.preybasic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Contains the tools to read the land/water mask from the input file and calculate the
 * number of land neighbours at each site.
 * @author
 *
 */
public class GridBasic {
	
	// The land water mask to be read from the file and the land neightbours at each site
	private int[][] landWater, neighbours;
	private int numRows, numColumns;
	
	/**
	 * Copies the land water mask into a 2D array or integer with the correct dimensions.
	 * @param fileName
	 * @return True if no error.
	 */
	public boolean readFromFile(String fileName) {
		
		String inLine;
		String[] tokens;
		
		// Perform the read in a catch block in case of exceptions
		try {
		
			File inFile = new File(fileName);
			FileInputStream fis = new FileInputStream(inFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			BufferedReader br = new BufferedReader(isr);
			
			// Read the first line and store the dimensions of the grid
			inLine = br.readLine();
			tokens = inLine.split(" ");
			numColumns = Integer.parseInt(tokens[0]);
			numRows = Integer.parseInt(tokens[1]);
			landWater = new int[numRows+2][numColumns+2];
			neighbours = new int[numRows+2][numColumns+2];
			
			// Set the edges of both grid arrays to 0
			int i, j;
			for(i = 0; i < numRows+2; i++) {
				landWater[i][0] = 0;
				landWater[i][numColumns+1] = 0;
				neighbours[i][0] = 0;
				neighbours[i][numColumns+1] = 0;
			}
			for(j = 1; j < numColumns+1; j++) {
				landWater[0][j] = 0;
				landWater[numRows+1][j] = 0;
				neighbours[0][j] = 0;
				neighbours[numRows+1][j] = 0;
			}
			
			// Loop over the rows, reading in the lines
			for(i = 1; i < numRows+1; i++) {
				inLine = br.readLine();
				tokens = inLine.split(" ");
				
				// Loop over the tokens, storing in the land/water mask array
				for(j = 1; j < numColumns+1; j++) {
					landWater[i][j] = Integer.parseInt(tokens[j-1]);
				}
			}
			br.close();
		}
		
		// Return false if exception occurs
		catch(Exception e) {
			return false;
		}
		
		// Return true if no errors
		return true;
	}
	
	/**
	 * Calculates the number of land neighbours for each cell in the grid and stores in array
	 * to be accessed later.
	 */
	public void calculateNeighbours() {
		
		for(int i = 1; i < numRows+1; i++) {
			for(int j = 1; j < numColumns+1; j++) {
				neighbours[i][j] += landWater[i][j+1];
				neighbours[i][j] += landWater[i][j-1];
				neighbours[i][j] += landWater[i+1][j];
				neighbours[i][j] += landWater[i-1][j];
			}
		}
	}
	
	/**
	 * Returns true if a coordinate on the grid is land
	 * @param i Row index
	 * @param j Column index
	 * @return Result of query
	 */
	public boolean isLand(int i, int j) {
		if(landWater[i][j] == 1) return true;
		else return false;
	}
	
	public int getAdjacentLand(int i, int j) {
		return neighbours[i][j];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

}
