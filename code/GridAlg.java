import java.util.Random;


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
	
	/**
	 * Constructor for grid class taking inputs passed from input class.
	 * @param grid
	 */
	public GridAlg(int[][] grid, Animal[] animals) {
		setGrid(grid);
		setNeighbours();
		setAnimals(animals);
		initaliseDensities();
	}
	
	/**
	 * Sets the local grid values to integer input values
	 * @param grid Value of cells 1 for land and 0 for water.
	 */
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the local grid values
	 * @return Returns the value of each cell in the grid array.
	 */
	public int[][] getGrid(){
		return this.grid;		
	}
		
	/**
	 * Sets the array of animals to an input density array
	 * @param
	 */
	public void setAnimals(Animal[] animals) {
		this.animals = animals;
	}
	
	/**
	 * Returns the array of animals
	 * @return
	 */
	public Animal[] getAnimals(){
		return this.animals;		
	}
	
	/**
	 * Returns the number of land neighbours the grid point i, j has.
	 * @param i Row index
	 * @param j Column index
	 * @return neighbours Number of land neighbours point i,j has.
	 */
	public int getNeighbours(int i,int j) {	
		return neighbours[i][j];		
	}
	
	/**
	 * Sets the number of land neighbours each cell has in an array.
	 */
	public void setNeighbours() {
		
		int[][] neighbours = new int[grid.length][grid[0].length];
		
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {				

				neighbours[i][j] += grid[i-1][j];
				neighbours[i][j] += grid[i+1][j];
				neighbours[i][j] += grid[i][j-1];
				neighbours[i][j] += grid[i][j+1];
				
			}
		}		
		
		this.neighbours = neighbours;
		
	}
		
	/**
	 * Initialises the densities of all the animals to a pseudo-normally distributed number.
	 * Only the land squares are filled with animals.
	 */
	public void initaliseDensities() {
		
		double randomValue;
		
		for(int i=0;i<getAnimals().length;i++) {
			for(int j=0;j<getGrid().length;j++) {
				for(int k=0;k<getGrid().length;k++) {
			
					if(getGrid()[i][j] ==0) {
						randomValue = 0;
					}
					else {
						randomValue = distributedRandom(0,1);
					}
					getAnimals()[i].setDensity(j, k, randomValue);
					
				}
			}			
		}			
	}
	
	/**
	 * Method for creating random densities which will fill the density array. 
	 * @param dMin Bottom of the range of possible numbers
	 * @param dMax Top of the range of numbers
	 * @return
	 */
	public double distributedRandom(double dMin, double dMax) {
		Random random = new Random(2011);
		double ranNum = 0;
		
		for(int i=0;i<3;i++){
			ranNum += random.nextDouble()*(dMax-dMin);
		}
		
		ranNum = ranNum/3;
		ranNum += dMin;
		
		return ranNum;
	}
	
	
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
					if(grid[j][k]!=0) {
						animals[i].calcNextDensity(j, k, dt, animals, neighbours[j][k]);
					}
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
	 * Two different methods to see how the algorithm behaves under each update order.
	 * The one above is possibly more correct as animals are eaten, reproduce and die in the same step.
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
				for(int k=0; k<grid[j].length;k++) {		
					if(grid[j][k]!=0) {		
						animals[i].calcNextDensity(j, k, dt, animals,neighbours[j][k]);	
					}
				}
			}
			
			// Apply densities update while looping through animals
			animals[i].applyTimeStep();		
			
		}                                 
	}
	
}
