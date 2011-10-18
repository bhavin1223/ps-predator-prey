
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
	 * Rate the animal reproduces per animal
	 */
	private double birthRate;
	
	/**
	 * Rate the animal dies per animal
	 */
	private double deathRate;
	
	/**
	 * The initial density array and diffusion rate must be specified when an 
	 * animal is created.
	 * @param densities Initial density array
	 */
	public Animal(double[][] densities, double diffusionRate, double birthRate, double deathRate) {
		this.densities = densities;
		this.diffusionRate = diffusionRate;
	}
	
	/**
	 * Returns the density at location i, j after a time step of dt. Implementation
	 * provided by subclasses.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time step
	 * @param animals Array of animal densities the update may rely on.
	 */
	public abstract double getNextDensity(int i, int j, double dt, Animal[] animals);
	
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
	 * Sets the rate of death per animal
	 * @param deathRate Rate of death per animal
	 */
	public void setDeathRate(double deathRate) {
		this.deathRate = deathRate;
	}
	
	/**
	 * 	Returns the rate of death per animal
	 * @return Rate of death per animal
	 */
	public double getDeathRate() {
		return deathRate;		
	}
	
	/**
	 * Sets the rate of birth per animal
	 * @param birthRate Rate of birth per animal
	 */
	public void setBirthRate(double birthRate) {
		this.birthRate = birthRate;
	}
	
	/**
	 * 	Returns the rate of birth per animal
	 * @return Rate of birth per animal
	 */
	public double getBirthRate() {
		return birthRate;		
	}
		
		
	
	
}
