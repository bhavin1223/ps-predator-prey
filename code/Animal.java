
/**
 * Abstract class governs the structure of an animal density update.
 * @author Matt, Put your name here if you work on this class
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
	 * The initial density array and diffusion rate must be specified when an 
	 * animal is created.
	 * @param densities Initial density array
	 */
	public Animal(double[][] densities, double diffusionRate) {
		this.densities = densities;
		this.diffusionRate = diffusionRate;
	}
	
	/**
	 * Returns the density at location i, j after a time step of dt. Implementation
	 * provided by subclasses.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time step
	 */
	public abstract double getNextDensity(int i, int j, double dt);

}
