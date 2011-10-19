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
 * @version 1.0, October, 19th 2011
 * @since 1.0 
 * 
 */

/*
 * May split into additional classes especially for GUI (thinking of
 * actionlisteners JPanel extensions etc.
 */
public class InOut {
	public static void main(String arg[]) {
		File fileIn = null;
		// TODO : arrays declarations

		// Get the file from the argument line.
		if (arg.length > 0) {
			fileIn = new File(arg[0]);
		}

		if (fileIn == null) {
			System.out.println("Default: small.dat");
			fileIn = new File("small.dat");
		}

		try {
			// Wrap the FileInputStream with a DataInputStream
			FileInputStream fis = new FileInputStream(fileIn);
			DataInputStream dis = new DataInputStream(fis);

			while (true) {
				try {
					// TODO actual reading of data

				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}

			}

			dis.close();
		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
	}

}
