import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Inputs user settings (GUI) and water/land mask (.dat file). Outputs PPM
 * density files.
 * 
 * Read a .dat file in and parse the data into (an) array(s).
 * 
 * @author Jorge M.
 * @version 1.1, October, 19th 2011
 * @since 1.0  
 */

/*
 * May split into additional classes especially for GUI (thinking of
 * actionlisteners JPanel extensions etc.
 */
public class InOut {
	private int[][] landscape;
	
	/**
	 * Constructor
	 */
	public InOut(String pathToFile) {
		loadLandscape(pathToFile);		
	}
	
	/**
	 * Load the bitmask lanscape file
	 * 
	 * @param	maskFileIn	The absolute path to the file to be read
	 */
	public void loadLandscape(String maskFileIn) {
		File maskFile = null;
		byte[] bufffer;
		// TODO : arrays declarations

		// Get the file from the argument line.
		if (maskFileIn.length() > 0) {
			maskFile = new File(maskFileIn);
		}

		if (maskFile == null) {
			System.out.println("Default: small.dat");
			maskFile = new File("small.dat");
		}

		try {
			// Wrap the FileInputStream with a DataInputStream
			FileInputStream fis = new FileInputStream(maskFile);
			DataInputStream dis = new DataInputStream(fis);

//			while (true) {
//				try {
					// TODO actual reading of data
				          byte[] buffer = new byte[dis.available()];
				          dis.read(buffer);

//				} catch (EOFException eof) {
					System.out.println("End of File");
	//				break;
//				}
//			}

			dis.close();
		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
	}
	
	public void setLanscape(int[][] landscapeIn) {
		System.arraycopy(landscapeIn, 0, landscape, 0, landscapeIn.length);
	}
	
	public int[][] getLanscape() {
		return landscape;
	}
}
