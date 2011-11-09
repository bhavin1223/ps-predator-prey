package com.pred.prey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Read a .dat file in and parse the data into (an) array(s).
 * 
 * @author Jorge M.
 * @version 2.1, November, 9th 2011
 * @since 1.0
 */
public class MapReader {
	private int[][] intBuffer;
	private int[][] neighbours;
	private int cols, rows;
	/**
	 * Dimensions of the data file and the storage arrays. /** Path to the data
	 * file to be read. It file must be outside the "src" folder, in the level
	 * just above.
	 */
	private String defaultFile = "./small.dat";
	// private String defaultFile = "./islands.dat";

	/**
	 * Constructor
	 */
	public MapReader(String pathToFile) {
		loadLandscape(pathToFile);
		countNeighbours();
	}

	public MapReader() {
		loadLandscape(defaultFile);
		countNeighbours();
	}

	/**
	 * Load the bitmask landscape file, parse it, read the file dimensions and
	 * stores the data into multidimensional arrays
	 * 
	 * @param maskFileIn
	 *            The absolute path to the file to be read
	 * 
	 */
	public String loadLandscape(String maskFileIn) {
		File maskFile = null;
		String[] tokens = null;
		String s;
		int lineNo = 0; // line counter
		InputStreamReader isr = null;

		if (maskFileIn == null) {
			System.out.println("Default: small.dat");
			maskFile = new File(defaultFile);
		}

		try {
			if (maskFileIn.length() > 0) {
				maskFile = new File(maskFileIn);
			}
		} catch (NullPointerException npe) {
			npe.getMessage();
		}

		try {
			FileInputStream fis = new FileInputStream(maskFile);
			BufferedReader br = null;

			try {
				isr = new InputStreamReader(fis, "UTF8");
			} catch (UnsupportedEncodingException uee) {
				uee.getMessage();
			}

			br = new BufferedReader(isr);

			try {
				while ((s = br.readLine()) != null) {
					tokens = s.split(" ");

					if (lineNo == 0) {
						// Parse the dimensions of the file
						cols = Integer.parseInt(tokens[0]);
						rows = Integer.parseInt(tokens[1]);

						intBuffer = new int[rows + 2][cols + 2];
						lineNo++;

						initialiseIntBuffer();
					} else if (lineNo > 0) {
						for (int x = 1; x < tokens.length - 1; x++) {
							intBuffer[lineNo][x] = Integer
									.parseInt(tokens[x - 1]);
						}

						lineNo++;
					}
				}

				br.close();
			} catch (IOException ioe) {
				ioe.getMessage();
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println("File Not Found Exception =: "
					+ fnfe.getMessage());
		}
		return "ok";
	}

	/**
	 * Set the array storage for the data to zeroes
	 */
	public void initialiseIntBuffer() {
		for (int x = 0; x < intBuffer.length; x++) {
			for (int y = 0; y < intBuffer[0].length; y++) {
				intBuffer[x][y] = 0;
			}
		}
	}
	
	/**
	 * Calculate the number of neighbours at each location (vertically and
	 * horizontally) and fill in the array
	 */
	public void countNeighbours() {
		neighbours = new int[rows + 2][cols + 2];

		// Set halos to zeros
		for (int x = 0; x < neighbours.length; x++) {
			for (int y = 0; y < neighbours[0].length; y++) {
				neighbours[x][y] = -1;
			}
		}

		// Sum up neighbours
		for (int x = 1; x < intBuffer.length - 1; x++) {
			for (int y = 1; y < intBuffer[0].length - 1; y++) {
				if (intBuffer[x][y] == 1) {
					neighbours[x][y] = intBuffer[x - 1][y]
							+ intBuffer[x][y - 1] + intBuffer[x][y + 1]
							+ intBuffer[x + 1][y];
				}
			}
		}
	}

	/**
	 * Setter for the array that stores the number of neighbours of each
	 * element. If the value in the data mask is land = 1 then the algorithm
	 * calculates the number of neighbours (vertically and horizontally) and
	 * stores it in the array.
	 * 
	 * @param neighboursIn
	 *            An array to be copied from
	 */
	public void setNeighbours(int[][] neighboursIn) {
		System.arraycopy(neighboursIn, 0, neighbours, 0, neighboursIn.length);
	}

	/**
	 * Getter for the array that stores the number of neighbours of each
	 * element. If the value in the data mask is land = 1 then the algorithm
	 * calculates the number of neighbours (vertically and horizontally) and
	 * stores it in the array.
	 * 
	 * @return The reference to the array of neighbours
	 */
	public int[][] getNeighbours() {
		return neighbours;
	}

	/**
	 * Get the current value of the dimension m. m is the first dimension of the
	 * data file being read and will be used to build arrays to store the data.
	 * 
	 * @return The first dimension read from the data file
	 */
	public int getcols() {
		return cols;
	}

	/**
	 * Set the current value of the dimension m. m is the first dimension of the
	 * data file being read and will be used to build arrays to store the data.
	 */
	public void setcols(int cols) {
		this.cols = cols;
	}

	/**
	 * Get the current value of the dimension n. n is the second dimension of
	 * the data file being read and will be used to build arrays to store the
	 * data.
	 * 
	 * @return The second dimension read from the data file
	 */
	public int getrows() {
		return rows;
	}

	/**
	 * Set the current value of the dimension n. n is the second dimension of
	 * the data file being read and will be used to build arrays to store the
	 * data.
	 */
	public void setrows(int nrows) {
		this.rows = rows;
	}

	/**
	 * Get intBuffer: a double dimensional array that stores the values read
	 * from the data file
	 * 
	 * @return The data from the file in a double dimensional array without
	 *         spaces
	 */
	public int[][] getIntBuffer() {
		return intBuffer;
	}

	/**
	 * Set intBuffer: a double dimensional array that stores the values read
	 * from the data file
	 * 
	 * @return The data from the file in a double dimensional array without
	 *         spaces
	 */
	public void setIntBuffer(int[][] intBuffer) {
		this.intBuffer = intBuffer;
	}
}
