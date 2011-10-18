
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
	
	
	// TODO - Class body
	
	/*
	 * Here we can choose in what order we update sites e.t.c. 1 method for 
	 * each possible algorithm. Method used to be specified by user.
	 */

}
