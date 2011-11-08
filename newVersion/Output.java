import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Used to save density distributions to ppm files.
 * @author
 */
public class Output {
	
	private Grid map;
	
	/**
	 * Constructor stores map in order to determine where water cells are.
	 * @param map The land/water mask
	 */
	public Output(Grid map) {
		this.map = map;
	}
	
	/**
	 * Takes the file names and densities and creates 3 ppms (1 for hare, 1 for puma
	 * and 1 comparison)
	 * @param fileNameH Hare output file name
	 * @param fileNameP Puma output file name
	 * @param fileNameHP Comparison file name
	 * @param H Hare densities
	 * @param P Puma densities
	 * @return True if no error
	 */
	public boolean densitiesOut(String fileNameH, String fileNameP, String fileNameHP, double[][] H, double[][] P) {
		return genSinglePPM(fileNameH, H) && genSinglePPM(fileNameP, P) && genPPMs(fileNameHP, H, P);
	}
	
	/**
	 * Generates a PPM file showing a single density distribution.
	 * @param fileName
	 * @param densities
	 * @return True if no errors
	 */
	private boolean genSinglePPM(String fileName, double[][] densities) {
		
		int numRows = densities.length - 2, numColumns = densities[0].length - 2, colourVal;
		double minDensity = 0, maxDensity = 0, colourStep;
		
		// Get the density of any cell
		int i, j;
		for( i = 1; i < numRows+1; i++) {
			for( j = 0; j < numColumns; j++) {
				if(map.isLand(i, j)) {
					minDensity = densities[i][j];
					maxDensity = densities[i][j];
					break;
				}
			}
		}
		
		// Get the minimum and maximum densities
		for( i = 1; i < numRows+1; i++) {
			for( j = 0; j < numColumns; j++) {
				
				// Only check land cells
				if(map.isLand(i, j)) {
					if((densities[i][j] > maxDensity)) maxDensity = densities[i][j];
					else if((densities[i][j] <= minDensity)) minDensity = densities[i][j];
				}
			}
		}
		
		// The color step determines what shade each cell will be represented by
		colourStep = 255.0/(maxDensity - minDensity);
		
		// Create the PPM file and write the densities
		BufferedWriter bufferedWriter;
		try {
			
			// Print the file properties (NOT APPEND!)
			bufferedWriter = new BufferedWriter(new FileWriter(fileName, false));
			bufferedWriter.write("P3");
			bufferedWriter.newLine();
			bufferedWriter.write(numColumns + " " + numRows);
			bufferedWriter.newLine();
			bufferedWriter.write("255");
			bufferedWriter.newLine();

			// Loop over all cells, printing the correct colour
			for (i = 1; i < (numRows + 1); i++) {
				for (j = 1; j < (numColumns + 1); j++) {

					
					// Fill water cells with blue
					if (!map.isLand(i, j)) bufferedWriter.write("0 0 255 ");
					else {
						colourVal = (int)Math.round((densities[i][j] - minDensity) * colourStep);
						bufferedWriter.write(colourVal + " " + colourVal + " " + colourVal + " ");								
					}

				}
				bufferedWriter.newLine();
			}
			
			// Close the writer
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		
		// Return value depends on whether there were any errors
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean genPPMs(String fileName, double[][] H, double P[][]) {
		
		int numRows = H.length - 2, numColumns = H[0].length - 2;
		double minDensity = 0, maxDensity = 0, colourStep;
		
		// Get the density of any cell
		int i, j;
		for( i = 1; i < numRows+1; i++) {
			for( j = 0; j < numColumns; j++) {
				if(map.isLand(i, j)) {
					minDensity = H[i][j];
					maxDensity = H[i][j];
					break;
				}
			}
		}
		
		// Get the minimum and maximum densities (checking both H and P)
		for( i = 1; i < numRows+1; i++) {
			for( j = 0; j < numColumns; j++) {
				
				// Only check land cells
				if(map.isLand(i, j)) {
				
					// Check hare densities
					if((H[i][j] > maxDensity)) maxDensity = H[i][j];
					else if((H[i][j] <= minDensity)) minDensity = H[i][j];
					
					// Check puma densities
					if((P[i][j] > maxDensity)) maxDensity = P[i][j];
					else if((P[i][j] <= minDensity)) minDensity = P[i][j];
				}
			}
		}
		
		// The color step determines what shade each cell will be represented by
		colourStep = 255.0/(maxDensity - minDensity);
		
		// Create the PPM file and write the densities
		BufferedWriter bufferedWriter;
		try {
			
			// Print the file properties (NOT APPEND!)
			bufferedWriter = new BufferedWriter(new FileWriter(fileName, false));
			bufferedWriter.write("P3");
			bufferedWriter.newLine();
			bufferedWriter.write(numColumns + " " + numRows);
			bufferedWriter.newLine();
			bufferedWriter.write("255");
			bufferedWriter.newLine();

			// Loop over all cells, printing the correct colour
			for (i = 1; i < (numRows + 1); i++) {
				for (j = 1; j < (numColumns + 1); j++) {

					
					// Fill water cells with blue
					if (!map.isLand(i, j)) bufferedWriter.write("0 0 255 ");
					else {
						bufferedWriter.write((int)Math.round((H[i][j] - minDensity) * colourStep) + 
								" " + (int)Math.round((H[i][j] - minDensity) * colourStep) + " 0 ");									
					}

				}
				bufferedWriter.newLine();
			}
			
			// Close the writer
			bufferedWriter.flush();
			bufferedWriter.close();
		}
		
		// Return value depends on whether there were any errors
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
