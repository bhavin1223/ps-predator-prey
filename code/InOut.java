import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Inputs user settings (GUI) and water/land mask (.dat file). Outputs PPM
 * density files.
 * 
 * Read a .dat file in and parse the data into an array.
 * 
 * @author Jorge M.
 * @version 1.3, October, 23rd 2011
 * @since 1.0
 */

/*
 * May split into additional classes especially for GUI (thinking of
 * actionlisteners JPanel extensions etc.
 */
public class InOut {
	private static final int MAX = 2000;
	private char[][] charBuffer = new char[MAX + 2][MAX + 2];
	private int[][] landscape = new int[MAX + 2][MAX + 2];
	private int[][] neighbours = new int[MAX + 2][MAX + 2];
	private byte[] buffer;

	/**
	 * Constructor
	 */
	public InOut(String pathToFile) {
		loadLandscape(pathToFile);
		countNeighbours();
	}

	/**
	 * Load the bitmask lanscape file
	 * 
	 * @param maskFileIn
	 *            The absolute path to the file to be read
	 */
	public void loadLandscape(String maskFileIn) {
		File maskFile = null;

		if (maskFileIn.length() > 0) {
			maskFile = new File(maskFileIn);
		}

		if (maskFile == null) {
			System.out.println("Default: small.dat");
			maskFile = new File("small.dat");
		}

		initialiseCharBuffer();

		try {
			FileInputStream fis = new FileInputStream(maskFile);
			DataInputStream dis = new DataInputStream(fis);

			try {
				buffer = new byte[dis.available()];
				dis.read(buffer);

				for (int i = 6, j = 1, k = 1; i < buffer.length; i++) {
					char c = (char) buffer[i];

					if (c != '\n') {
						if (c == ' ') {
							continue;
						}

						charBuffer[j][k++] = c;
					} else if (c == '\n') {
						k = 1;
						++j;
					}
				}

				dis.close();
			} catch (IOException e) {
				System.out.println("IO Exception =: " + e.getMessage());
			} catch (NullPointerException npe) {
				System.out.println("Null Pointer Exception Exception =: "
						+ npe.getMessage());
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println("File Not Found Exception =: "
					+ fnfe.getMessage());
		}
	}

	/**
	 * Initialise the buffer that receives the bytes turned to characters
	 */
	public void initialiseCharBuffer() {
		for (int i = 0; i < charBuffer.length; i++) {
			for (int j = 0; j < charBuffer[0].length; j++) {
				charBuffer[i][j] = 'x';
			}
		}
	}

	/**
	 * Fill in the array of neighbours which the number of neighbours at each location (vertically and horizontally)
	 */
	public void countNeighbours() {
		for (int i = 0; i < landscape.length; i++) { // Set halos/ borders to
														// zeros
			for (int j = 0; j < landscape[0].length; j++) {
				landscape[i][j] = 0;
				neighbours[i][j] = 0;
			}
		}

		for (int i = 1, k = 1; i < charBuffer.length - 1; i++, k++) {
			for (int j = 1, l = 1; j < charBuffer[0].length - 1; j++, l++) {
				if (charBuffer[i][j] == 'x' || charBuffer[i][j] == '0') {
					landscape[i][j] = 0;
				} else if (charBuffer[i][j] == '1') {
					landscape[k][l] = 1;
				}
			}
		}

		for (int i = 1; i < landscape.length - 1; i++) {
			for (int j = 1; j < landscape[0].length - 1; j++) {
				if (landscape[i][j] == 1) {
					neighbours[i][j] = landscape[i - 1][j]
							+ landscape[i][j - 1] + landscape[i][j + 1]
							+ landscape[i + 1][j + 1];
				}
			}
		}

/*		for (int i = 0; i < landscape.length; i++) {
			System.out.println();
			for (int j = 0; j < landscape[0].length; j++) {
				System.out.print(landscape[i][j]);
			}
		}*/
	}

	/**
	 * Setter for the array of neighbours
	 * 
	 * @param neighboursIn 	An array to be copied
	 */
	public void setNeighbours(int[][] neighboursIn) {
		System.arraycopy(neighboursIn, 0, neighbours, 0, neighboursIn.length);
	}

	/**
	 * Getter for the array of neighbours
	 * 
	 * @return The reference to the array of neighbours
	 */
	public int[][] getNeighbours() {
		return neighbours;
	}
}
