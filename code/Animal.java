import java.util.Random;

/**
 * Class governs the structure of an animal density update.
 * 
 * @author Matt, Simon Put your name here if you work on this class
 * 
 */
public class Animal {

	/**
	 * Density grid for this animal.
	 */
	private double[][] densities;

	/**
	 * Used to store the densities after the next time step;
	 */
	private double[][] nextDensities;

	/**
	 * Diffusion rate of this animal;
	 */
	private double diffusionRate;

	/**
	 * Coefficients scaling how amounts of the different animals effect this
	 * animal. Matrix is rectangular N*N+1 where N is the number of types of
	 * animals First row are linear coefficients and the rest are cross
	 * quadratic terms Most of the grid will be 0 e.g. birth and death rates.
	 */
	private double[] diffCoefficients;

	/**
	 * The name of the animal
	 */
	private String name;

	/**
	 * USE THIS Creates an animal with no parameters initially input as they are
	 * all input by the user in the GUI Only the number of animals in the
	 * simulation needs to be initially given.
	 */
	public Animal(int numAnimals) {
		diffCoefficients = new double[numAnimals];
	}

	/**
	 * The initial density array and diffusion rate must be specified when an
	 * animal is created. If available the array or coefficients should be
	 * provided also.
	 * 
	 * @param densities
	 *            Initial density array
	 * @param diffusionRate
	 *            Rate this animal moves out/in to the cell
	 * @param diffCo
	 *            The coefficients which scale how the number of each animal
	 *            effects this animal over time.
	 */
	public Animal(double[][] densities, double diffusionRate, double[] diffCo) {
		setDensities(densities);
		setDiffusionRate(diffusionRate);
		setDiffCo(diffCo);
	}

	/**
	 * The initial density array and diffusion rate must be specified when an
	 * animal is created.
	 * 
	 * @param densities
	 *            Initial density array
	 * @param diffusionRate
	 *            Rate this animal moves out/in to the cell
	 */
	public Animal(double[][] densities, double diffusionRate) {
		setDensities(densities);
		setDiffusionRate(diffusionRate);
	}

	/**
	 * The initial density array and diffusion rate must be specified when an
	 * animal is created.
	 * 
	 * @param densities
	 *            Initial density array
	 * @param diffusionRate
	 *            Rate this animal moves out/in to the cell
	 */
	public Animal(Animal animal) {
		setDensities(animal.getDensities());
		setDiffusionRate(animal.getDiffusionRate());
	}

	/**
	 * Calculates the density at location i, j after a time step of dt. Stores
	 * the result in the 2D nextDensites array.
	 * 
	 * @param i
	 *            Row index
	 * @param j
	 *            Column index
	 * @param dt
	 *            Time step
	 * @param animals
	 *            Array of animal densities the update may rely on.
	 * @param neighbours
	 *            The number of land neighbours cell i, j has.
	 */
	public void calcNextDensity(int i, int j, double dt, Animal[] animals,
			int neighbours) {
		double oldDensity = getDensity(i, j);
		double newDensity = oldDensity;

		/**
		 * Runs through the coefficients
		 */
		for (int k = 0; k < animals.length; k++) {
			
			if (animals[k] == this) {
				newDensity += dt * getDiffCo()[k] * oldDensity;
			} else {
				newDensity += dt * getDiffCo()[k] * animals[k].getDensity(i, j)
				* oldDensity;
			}

		}

		/**
		 * Calculates the diffusion of the animal
		 */
		//if(j-1>0){
			newDensity += dt*getDiffusionRate()*getDensity(i,j-1);
	//	}
		//if(j+1<getDensities()[i].length){
			newDensity += dt*getDiffusionRate()*getDensity(i,j+1);
		//}	
	//	if(i-1>0){
			newDensity += dt*getDiffusionRate()*getDensity(i-1,j);
		//}
	//	if(i+1<getDensities().length){
			newDensity += dt*getDiffusionRate()*getDensity(i+1,j);
		//}	
		newDensity -= dt*getDiffusionRate()*neighbours*oldDensity;

		//System.out.println(oldDensity);
		//System.out.println(dt*getDiffusionRate()*neighbours*oldDensity);
		//System.out.println(dt*getDiffusionRate()*neighbours*oldDensity);

		nextDensities[i][j] = newDensity;
	}

	/**
	 * Replaces the contents of the 2D densities array with that of the 2D
	 * nextDensities array.
	 */
	public void applyTimeStep() {

		for (int i = 0; i < densities.length; i++) {
			for (int j = 0; j < densities[0].length; j++) {
				densities[i][j] = nextDensities[i][j];
			}
		}

	}

	/**
	 * Sets the name of the animal
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the animal
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the densities of all grid points to those in an array.
	 * 
	 * @param densities
	 *            The densities of the animals being set across the grid.
	 */
	public void setDensities(double[][] densities) {

		this.densities = densities;

	}

	/**
	 * Sets the densities of all grid points from the distributions defined in
	 * the 'customrand' method
	 * 
	 * @param densities
	 *            The densities of the animals being set across the grid.
	 */
	public void initiateDensities(int i, int j) {

		this.densities = new double[i][j];
		this.nextDensities = new double[i][j];

	}

	/**
	 * Sets the densities of all grid points from a premade array
	 * 
	 * @param densities
	 *            The densities of the animals being set across the grid.
	 */
	public double[][] getDensities() {
		return densities;
	}

	/**
	 * Sets the density at location i, j.
	 * 
	 * @param i
	 *            Row index
	 * @param j
	 *            Column index
	 * @param value
	 *            Density value the grid point is being set to
	 */
	public void setDensity(int i, int j, double value) {
		densities[i][j] = value;
	}

	/**
	 * Returns density at location i, j.
	 * 
	 * @param i
	 *            Row index
	 * @param j
	 *            Column index
	 * @return Value of density at grid point
	 */
	public double getDensity(int i, int j) {
		return densities[i][j];
	}

	/**
	 * Sets the rate of diffusion for occurrence of animal
	 * 
	 * @param rate
	 *            the rate the animal density diffuses
	 */
	public void setDiffusionRate(double rate) {
		this.diffusionRate = rate;
	}

	/**
	 * Returns the animals diffusion rate
	 * 
	 * @return Diffusion rate for the animal
	 */
	public double getDiffusionRate() {
		return this.diffusionRate;
	}

	/**
	 * Sets the coefficients controlling the changes of population with time.
	 */
	public void setDiffCo(double[] diffCo) {
		this.diffCoefficients = diffCo;
	}

	/**
	 * Returns the coefficients controlling the changes of population with time.
	 */
	public double[] getDiffCo() {
		return diffCoefficients;
	}

	/**
	 * Returns new densities after applying time step delta t.
	 * 
	 * @return Array of double precision densities.
	 */
	public double[][] getNextDensities() {
		return nextDensities;
	}

	/**
	 * new densities holds the density values after applying time step delta t.
	 * 
	 * @param Array
	 *            of densities to copy from.
	 */
	public void setNextDensities(double[][] nextDensitiesIn) {
		nextDensities = nextDensitiesIn;
	}
}