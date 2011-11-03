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
		nextDensities = new double[densities.length][densities[0].length];
		double oldDensity = getDensity(i, j);
		double newDensity = oldDensity;

		/**
		 * Runs through the coefficients
		 */
		for (int k = 0; k < animals.length; k++) {

			if (animals[k] == this) {
				newDensity += dt * getDiffCo()[k] * this.getDensity(i, j);
			} else {
				newDensity += dt * getDiffCo()[k] * animals[k].getDensity(i, j)
						* this.getDensity(i, j);
			}

		}

		/**
		 * Calculates the diffusion of the animal
		 */
		newDensity += getDiffusionRate()
				* (getDensity(i, j - 1) + getDensity(i, j + 1)
						+ getDensity(i - 1, j) + getDensity(i + 1, j) - neighbours
						* getDensity(i, j));

		nextDensities[i][j] = newDensity;
	}

	/**
	 * Replaces the contents of the 2D densities array with that of the 2D
	 * nextDensities array.
	 */
	public void applyTimeStep() {

		for (int i = 0; i < densities.length; i++) {
			for (int j = 0; j < densities[0].length; j++) {
				densities[i] = nextDensities[i];
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

	/**
	 * the genrand method generates random integers in a pseudo normal
	 * distribution between bmin and bmax, returning only values that fall
	 * between rmin and rmax. Larger n improves the approximation to a normal
	 * distribution, but 3 should be sufficient for our needs
	 */

	private int genrand(int bmin, int bmax, int rmin, int rmax, int n) {
		Random rand = new Random();
		int i, u, sum;
		do {
			sum = 0;
			for (i = 0; i < n; i++)
				sum += bmin + (rand.nextInt() % (bmax - bmin));
			if (sum < 0)
				sum -= n - 1; /* prevent pileup at 0 */
			u = sum / n;
		} while (!(rmin <= u && u < rmax));
		return u;
	}

	/**
	 * the customrand method makes calls to the genrand method with a
	 * distributed set of parameters. This means we can combine an arbitrary set
	 * of normal distributions or sections of normal distributions, and in
	 * theory produce any distribution we want! Min and max are the lower and
	 * upper indices of the density array for the animal. Method taken from
	 * Michael A. Covington 'How to Make a Lumpy Random-Number Generator'.
	 */

	/**
	 * at the moment it produces a single distrubution symmetric in x,y, but it
	 * should be very easy to extend this to different distributions for each
	 * animal/dimension using a few if() statements and an extra argument
	 */

	public int customrand(int min, int max) {
		Random rand2 = new Random();
		int d = rand2.nextInt(3); /* gives a number from 0,1,2 */
		int x;
		switch (d) {
		case 0:
			x = genrand(-200, 300, min, max, 3);
			break;
		case 1:
			x = genrand(0, 100, min, max, 3);
			break;
		case 2:
			x = genrand(80, 100, min, max, 3);
			break;
		default:
			x = genrand(-100, 200, min, max, 3);
			break;
		}
		return x;
	}

}