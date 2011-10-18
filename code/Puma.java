
/**
 * Puma animal. Contains the tools for calculating the density of pumas in a grid square
 * after a given time interval.
 * @author Matt, Simon Put your name here if you work on this class
 */
public class Puma extends Animal {
	
	/**
	 * Constructor stores the values needed to calculate densities.
	 * @param P Puma densities
	 * @param l Puma diffustion rate
	 * @param b Reproduction rate of predators per one prey eaten
	 * @param m Predator mortality rate
	 */
	public Puma(double[][] P, double l, double b, double m) {
		super(P, l, b, m);
	}
	
	/**
	 * Provides implementation of the inherited getNextDensity method according to the PijNew equation
	 * provided in the notes. Returns the new density value at grid position i, j after a time interval dt.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time interval
	 * @param animals Array of animal densities the update may rely on.
	 */
	@Override
	public double getNextDensity(int i, int j, double dt, Animal[] animals) {
		
		// TODO Method body
		
		/*
		 * Note that there will be likely values in the equation that can be calculated once
		 * and stored in fields to decrease execution time.
		 */

		return 0;
	}

}
