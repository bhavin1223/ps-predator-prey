
/**
 * Abstract class governs the structure of an animal density update.
 * @author Matt, Simon Put your name here if you work on this class
 *
 */
public abstract class Animal {
	
	/**
	 * Density grid for this animal.
	 */
	private double[][] densities;
	
	/**
	 * Diffusion rate of this animal;
	 */
	private double diffusionRate;
	
	/**
	 * Coefficients scaling how amounts of the different animals effect this animal.
	 * Matrix is rectangular N*N+1 where N is the number of types of animals
	 * First row are linear coefficients and the rest are cross quadratic terms
	 * Most of the grid will be 0
	 * e.g. birth and death rates.
	 */
	private double[][] diffCoefficients;
		
	/**
	 * The initial density array and diffusion rate must be specified when an 
	 * animal is created. If available the array or coefficients should be provided also. 
	 * @param densities Initial density array
	 * @param diffusionRate Rate this animal moves out/in to the cell
	 * @param diffCo The coefficients which scale how the number of each animal effects this animal over time.
	 */
	public Animal(double[][] densities, double diffusionRate, double[][] diffCo) {
		setDensities(densities);
		setDiffusionRate(diffusionRate);
		setDiffCo(diffCo);
	}
	
	/**
	 * The initial density array and diffusion rate must be specified when an 
	 * animal is created.
	 * @param densities Initial density array
	 * @param diffusionRate Rate this animal moves out/in to the cell
	 */
	public Animal(double[][] densities, double diffusionRate) {
		setDensities(densities);
		setDiffusionRate(diffusionRate);
	}
	
	/**
	 * Returns the density at location i, j after a time step of dt. Implementation
	 * provided by subclasses.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time step
	 * @param animals Array of animal densities the update may rely on.
	 */
	public double getNextDensity(int i, int j, double dt, Animal[] animals) {
		
		double oldDensity = getDensity(i, j);
		double newDensity = oldDensity;	
				
		/**
		 * Runs through the first line of coefficients
		 */
		for(int k=0; k<animals.length; k++) {
			
			newDensity += dt*getDiffCo()[0][k]*animals[k].getDensity(i, j);
			
		}
		
		/**
		 * Runs through the rest of the coefficient matrix
		 */
		for(int k=0; k<animals.length; k++) {
			for(int l=1;l<=animals.length; l++) {
								
				newDensity += dt*getDiffCo()[l][k]*animals[k].getDensity(i, j)*animals[l].getDensity(i, j);
			
			}
		}
		
		// TODO - Proper neighbour diffusion update
		
		/**
		 * Calculates the diffusion of the animal
		 */
		newDensity += getDiffusionRate()*(getDensity(i,j-1)+getDensity(i,j+1) + getDensity(i-1,j)+getDensity(i+1,j)-4*getDensity(i,j));
				
		return newDensity;
				
	}
	
	/**
	 * Sets the densities of all grid points from a premade array
	 * @param densities The densities of the animals being set across the grid.
	 */
	public void setDensities(double[][] densities) {
		this.densities = densities;
	}
	
	/**
	 * Sets the density at location i, j.
	 * @param i Row index
	 * @param j Column index
	 * @param value Density value the grid point is being set to
	 */
	public void setDensity(int i, int j, double value) {
		densities[i][j] = value;
	}
		
	/**
	 * Returns density at location i, j.
	 * @param i Row index
	 * @param j Column index
	 * @return Value of density at grid point
	 */
	public double getDensity(int i, int j) {
		return densities[i][j];		
	}
	
	/**
	 * Sets the rate of diffusion for occurrence of animal
	 * @param rate the rate the animal density diffuses
	 */
	public void setDiffusionRate(double rate) {
		this.diffusionRate = rate;
	}	

	/**
	 * Returns the animals diffusion rate
	 * @return Diffusion rate for the animal
	 */
	public double getDiffusionRate() {
		return this.diffusionRate;
	}
	
	/**
	 * Sets the coefficients controlling the changes of population with time.
	 */
	public void setDiffCo(double[][] diffCo) {
		this.diffCoefficients = diffCo;
	}
	
	/**
	 * 	Returns the coefficients controlling the changes of population with time.
	 */
	public double[][] getDiffCo() {
		return diffCoefficients;		
	}	
}
