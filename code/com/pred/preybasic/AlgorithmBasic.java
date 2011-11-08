package com.pred.preybasic;
/**
 * Contains the computational kernel. Takes the user options and runs the algorithm accordingly.
 * @author
 */
public class AlgorithmBasic {
	
	// Variables supplied by user and used in algorithms
	private double r, a, b, m, k, l, dt;
	private int numOutputs, stepsBlock, totalSteps;
	private String inputFile, outputFile;
	
	// Hare and puma densities copies used in calculation
	private double[][] H, P, HNext, PNext;
	int numRows, numColumns;
	
	// Land/water mask
	private GridBasic map;
	
	// Interface to print messages to
	private InputBasic GUI;
	
	// Some values in the computational kernel need only be calculated once to save time while running
	private double dtr, dta, dtk, dtb, dtm, dtl;
	
	/**
	 * Constructor stores GUI object so that messages may be printed to the display
	 * @param GUI
	 */
	public AlgorithmBasic(InputBasic GUI) {
		this.GUI = GUI;
	}

	/**
	 * Takes an array of options strings and converts them into the correct data types for
	 * use in calculations.
	 * @param options 
	 * @return
	 */
	public boolean setOptions(String[] options) {
		
		// Convert all of the parameters from the options string to the appropriate type
		try {
			r = Double.parseDouble(options[0]);
			a = Double.parseDouble(options[1]);
			b = Double.parseDouble(options[2]);
			m = Double.parseDouble(options[3]);
			k = Double.parseDouble(options[4]);
			l = Double.parseDouble(options[5]);
			dt = Double.parseDouble(options[6]);
			numOutputs = Integer.parseInt(options[7]);
			stepsBlock = Integer.parseInt(options[8]);
			inputFile = options[9];
			outputFile = options[10];
		}
		catch(Exception e) {
			return false;
		}
			
		// Return true if no error occurred during the conversions
		totalSteps = numOutputs*stepsBlock;
		return true;
	}

	/**
	 * Ceates a new Grid object and passes it the file input file name specified
	 * by the user. Returns true only if there are no errors.
	 * @return
	 */
	public boolean generateMap() {
		map = new GridBasic();
		if (!map.readFromFile(inputFile)) return false;
		numRows = map.getNumRows();
		numColumns = map.getNumColumns();
		
		// Initialise density arrays
		H = new double[numRows+2][numColumns+2];
		P = new double[numRows+2][numColumns+2];
		HNext = new double[numRows+2][numColumns+2];
		PNext = new double[numRows+2][numColumns+2];
		return true;
	}
	
	// Runs the algorithm
	public void runAlg() {
		
		// Set up the system
		map.calculateNeighbours();
		GUI.printMessage("Land neighbours calculated and stored.");
		setInitialDensities();
		GUI.printMessage("Initial densities set randomly between 0.0 and 5.0.");
		
		// Calculate the constants in used in the density equations
		dtr = dt*r;
		dta = dt*a;
		dtk = dt*k;
		dtb = dt*b;
		dtm = dt*m;
		dtl = dt*l;
		
		// Iterate over the blocks of steps
		OutputBasic out = new OutputBasic(map);
		int step = 0,stepsSoFar;
		String fileNameH, fileNameP, fileNameHP;
		for(int b = 0; b < numOutputs; b++) {
			
			// Loop over the number of steps in the block
			for(int s = 0; s < stepsBlock; s++) {
				step++;
				step();
			}
			stepsSoFar = (b+1)*stepsBlock;
		
			// Generate a new file name to output to based on the block number
			fileNameH = outputFile + "H" + stepsSoFar + ".ppm";
			fileNameP = outputFile + "P" + stepsSoFar + ".ppm";
			fileNameHP = outputFile + "HP" + stepsSoFar + ".ppm";
			
			if(out.densitiesOut(fileNameH, fileNameP, fileNameHP, H, P)) {
			GUI.printMessage("Block " + (b+1) + " completed (" + step + " steps performed).\n" +
					"Densities saved to file " + fileNameH + " and " + fileNameP + " and " + fileNameHP + ".");
			}
			else GUI.printMessage("Unable to write to output file(s).\n");
		}
	}
	
	/**
	 * Performs a single time step, updating all of the densities for both animals
	 */
	private void step() {
		
		// Loop over all of the cells (not the very edges as these are invalid)
		double sumAdjacentH, sumAdjacentP;
		int adjacentLand;
		for(int i = 1; i < numRows+1; i++) {
			for(int j = 1; j < numColumns+1; j++) {
				
				// Only perform calculations on cells that are land
				if(map.isLand(i, j)){
					
					// Sum the adjacent densities for both animals and count the adjacent land cells
					sumAdjacentH = H[i][j+1] + H[i][j-1] + H[i+1][j] + H[i-1][j];
					sumAdjacentP = P[i][j+1] + P[i][j-1] + P[i+1][j] + P[i-1][j];
					adjacentLand = map.getAdjacentLand(i, j);
					
					// Calculate the densities after the time step
					HNext[i][j] = H[i][j] + dtr*H[i][j] - dta*H[i][j]*P[i][j] 
							+ dtk*(sumAdjacentH - H[i][j]*adjacentLand);
					PNext[i][j] = P[i][j] + dtb*H[i][j]*P[i][j] - dtm*P[i][j] 
							+ dtl*(sumAdjacentP - P[i][j]*adjacentLand);
				}
			}
		}
		
		// Once calculations are complete, the step can be applied
		for(int i = 1; i < numRows+1; i++) {
			for(int j = 1; j < numColumns+1; j++) {
				H[i][j] = HNext[i][j];
				P[i][j] = PNext[i][j];
			}
		}
	}
	
	/**
	 * Loop over all of the sites on the grid, adding a random density of animals 
	 * (between 0.0 - 5.0) to all land cells.
	 */
	private void setInitialDensities() {
		
		// Set the edges of all grid arrays to 0
		int i, j;
		for(i = 0; i < numRows+2; i++) {
			H[i][0] = 0;
			H[i][numColumns+1] = 0;
			P[i][0] = 0;
			P[i][numColumns+1] = 0;
			HNext[i][0] = 0;
			HNext[i][numColumns+1] = 0;
			PNext[i][0] = 0;
			PNext[i][numColumns+1] = 0;
		}
		for(j = 1; j < numColumns+1; j++) {
			H[0][j] = 0;
			H[numRows+1][j] = 0;
			P[0][j] = 0;
			P[numRows+1][j] = 0;
			HNext[0][j] = 0;
			HNext[numRows+1][j] = 0;
			PNext[0][j] = 0;
			PNext[numRows+1][j] = 0;
		}
		
		// Loop over the (inner) cells, generating random densities
		for(i = 1; i < numRows+1; i++) {
			for(j = 1; j < numColumns+1; j++) {
				if(map.isLand(i, j)) {
					double rand = Math.random();
					H[i][j] = rand*5.0;
					P[i][j] = rand*5.0;
				}
				else {
					H[i][j] = 0.0;
					P[i][j] = 0.0;
				}
				HNext[i][j] = 0.0;
				PNext[i][j] = 0.0;
			}
		}	
	}

	public int getTotalSteps() {
		return totalSteps;
	}
	
	/**
	 * Prints densities to the terminal. Used for debugging.
	 */
	public void printH() {
		
		String line;
		for(int i = 0; i < H.length; i++) {
			line = "";
			for(int j = 0; j < H[0].length; j++) {
				line += H[i][j] + " ";
			}
			System.out.println(line);
		}
	}

}
