
/**
 * Runs the algorithm corresponding to the user's input with the user defined settings.
 * Also performs measurements on the efficiency of the chosen algorithm.
 * @author Put your name here if you work on this class
 */
public class GridAlg {
	
	/**
	 * Array containing the density distribution of all the animals
	 */
	private Animal[] animals;
	
	/**
	 * Array containing the layout of the grid, land and water as 1's and 0's.
	 */
	private int[][] grid;
	
	/**
	 * Array containing the neighbours each grid point should check for density updates based on land and water.
	 */
	private int[][] neighbours;
	
	/**
	 * Update frequency
	 */
	private double dt = 0.1;
	
	public GridAlg() {
		
	}
	
	
	/**
	 * Returns the value of the neighbours of grid point i, j.
	 * @param i Row index
	 * @param j Column index
	 * @return Array of values contained by neighbours 
	 */
	public int[] getNeighbours(int i,int j) {
		
		int[] neighbours = new int[4]; 
		
		neighbours[0] = grid[i-1][j];	
		neighbours[1] = grid[i+1][j];	
		neighbours[2] = grid[i][j-1];	
		neighbours[3] = grid[i][j+1];			
		
		return neighbours;
		
	}
	public void setNeighbours() {
		
		int[] neighbours = new int[4];
		
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				if(grid[i][j] != 0) {
				
					
					
					
				}				
			}
		}
	}
	
	//TODO fix the way the new animals array is made so that it actually duplicates the old array
	
	/**
	 * Updates the grid and animals in parallel.
	 * runs sequentially through the cells before replacing at end.
	 */
	public void syncUpdate() {
		
		// Loop over all of the animals
		for(int i=0;i<animals.length;i++) {
			
			// Loop over all of the cells, calculating the next densities
			for(int j=0; j<grid.length;j++) {
				for(int k=0; k<grid[0].length;k++) {				
					animals[i].calcNextDensity(j, k, dt, animals);										
				}
			}			
		}
		
		// Apply change at end of all calculations
		for(int i=0;i<animals.length;i++) {
			animals[i].applyTimeStep();		
		}
		
	}
	
	/*
	 * Not sure why we need two different methods here. The one below is best? 
	 */
	
	
	/**
	 * Updates the grid and in parallel.
	 * Runs sequentially through the cells for one animal then replaces
	 * Goes through all animals in turn.
	 */
	public void gridSyncUpdate() {
		
		// Loop over all of the animals
		for(int i=0;i<animals.length;i++) {
			
			// Loop over all of the cells, calculating the next densities
			for(int j=0; j<grid.length;j++) {
				for(int k=0; k<grid[0].length;k++) {				
					animals[i].calcNextDensity(j, k, dt, animals);										
				}
			}
			
			// Apply densities update while looping through animals
			animals[i].applyTimeStep();		
			
		}                                 
	}
	
}
