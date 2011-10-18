
/**
 * Hair animal. Contains the tools for calculating the density of hairs in a grid square
 * after a given time interval.
 * @author Simon Put your name here if you work on this class
 */
public class Hair extends Animal {
	
	/**
	 * Constructor stores the values needed to calculate densities.
	 * @param H Hare densities
	 * @param k Hare diffustion rate
	 * @param r Reproduction rate of hares
	 * @param a Hare mortality rate
	 */
	public Hair(double[][] H, double k, double r, double a) {
		super(H, k, r, a);
	}

	/**
	 * Provides implementation of the inherited getNextDensity method according to the HijNew equation
	 * provided in the notes. Returns the new density value at grid position i, j after a time interval dt.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time interval
	 * @param animals Array of animal densities the update may rely on.
	 */
	@Override
	public double getNextDensity(int i, int j, double dt, Animal[] animals) {
		
		// TODO Auto-generated method stub
		
		return 0;
	}

}
