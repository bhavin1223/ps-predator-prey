import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;

/**
 * Read a .dat file in and parse the data into (an) array(s).
 * 
 * @author Jorge M.
 * @version 2.0, October, 29th 2011
 * @since 1.0
 */

/*
 * May split into additional classes especially for GUI (thinking of
 * actionlisteners JPanel extensions etc.
 */
public class InOut {
	private static final int MAX = 2000;
	private int[][] intBuffer;
	private int[][] neighbours;
	private int m, n; /** Dimensions of the data file and the storage arrays.
	/**
	 * Path to the data file to be read. It file must be outside the "src" folder, in the level just above.
	 */
	private String defaultFile = "./small.dat";
	//private String defaultFile = "./islands.dat";

	/**
	 * Constructor
	 */
	public InOut(String pathToFile) {
		loadLandscape(pathToFile);
		countNeighbours();
	}
	
	public InOut() {
		loadLandscape(defaultFile);
		countNeighbours();
	}

	/**
	 * Load the bitmask landscape file, parse it, read the file dimensions and stores the data into multidimensional arrays
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
		
		if (maskFileIn.length() > 0) {
			maskFile = new File(maskFileIn);
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
						n = Integer.parseInt(tokens[0]);						
						m = Integer.parseInt(tokens[1]);
						
						intBuffer = new int[m + 2][n + 2];
						lineNo++;
						
						initialiseIntBuffer();						
					} else if (lineNo > 0 ) {
						for (int j = 1; j < tokens.length - 1 ; j++) {
							intBuffer[lineNo][j] = Integer.parseInt(tokens[j - 1]);							
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
		for (int i = 0; i < intBuffer.length; i++) {
			for (int j = 0; j < intBuffer[0].length; j++) {
				intBuffer[i][j] = 0;
			}
		}
	}

	/**
	 * Calculate the number of neighbours at each
	 * location (vertically and horizontally) and fill in the array
	 */
	public void countNeighbours() {
		neighbours = new int[m + 2][n + 2];
		
		// Set halos to zeros
		for (int i = 0; i < neighbours.length; i++) { 														
			for (int j = 0; j < neighbours[0].length; j++) {
				neighbours[i][j] = -1;
			}
		}
		
		// Sum up neighbours
		for (int i = 1; i < intBuffer.length - 1; i++) {
			for (int j = 1; j < intBuffer[0].length - 1; j++) {
				if (intBuffer[i][j] == 1) {
					neighbours[i][j] = intBuffer[i - 1][j]
							+ intBuffer[i][j - 1] + intBuffer[i][j + 1]
							+ intBuffer[i + 1][j];
				}
				else {
					neighbours[i][j] = -1;
				}				
			}
		}
		/*
		for (int i = 0; i < intBuffer.length; i++) { 			
			for (int j = 0; j < intBuffer[0].length; j++) {
				System.out.print(intBuffer[i][j]);
			}
			System.out.println();
		}*/
	}

	/**
	 * Setter for the array that stores the number of neighbours of each element.
	 * If the value in the data mask is land = 1 then the algorithm calculates the number of neighbours (vertically and horizontally) 
	 * and stores it in the array.
	 * 
	 * @param neighboursIn
	 *            An array to be copied from
	 */
	public void setNeighbours(int[][] neighboursIn) {
		System.arraycopy(neighboursIn, 0, neighbours, 0, neighboursIn.length);
	}

	/**
	 * Getter for the array that stores the number of neighbours of each element.
	 * If the value in the data mask is land = 1 then the algorithm calculates the number of neighbours (vertically and horizontally) 
	 * and stores it in the array.
	 * 
	 * @return The reference to the array of neighbours
	 */
	public int[][] getNeighbours() {
		return neighbours;
	}

	/**
	 * Get the current value of the dimension m.
	 * m is the first dimension of the data file being read
	 * and will be used to build arrays to store the data.
	 *  
	 * @return The first dimension read from the data file
	 */
	public int getM() {
		return m;
	}

	/**
	 * Set the current value of the dimension m.
	 * m is the first dimension of the data file being read
	 * and will be used to build arrays to store the data.
	 */
	public void setM(int m) {
		this.m = m;
	}

	/**
	 * Get the current value of the dimension n.
	 * n is the second dimension of the data file being read
	 * and will be used to build arrays to store the data.
	 *  
	 * @return The second dimension read from the data file
	 */
	public int getN() {
		return n;
	}

	/**
	 * Set the current value of the dimension n.
	 * n is the second dimension of the data file being read
	 * and will be used to build arrays to store the data.
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * Get intBuffer: a double dimensional array that stores the values read from the data file
	 * 
	 * @return The data from the file in a double dimensional array without spaces
	 */
	public int[][] getIntBuffer() {
		return intBuffer;
	}

	/**
	 * Set intBuffer: a double dimensional array that stores the values read from the data file
	 * 
	 * @return The data from the file in a double dimensional array without spaces
	 */
	public void setIntBuffer(int[][] intBuffer) {
		this.intBuffer = intBuffer;
	}
}
