
/**
 * Puma animal. Contains the tools for calculating the density of pumas in a grid square
 * after a given time interval.
 * @author Matt, Put your name here if you work on this class
 */
public class Puma extends Animal {

	/**
	 * The hair densities must be known when calculating new puma densities.
	 */
	private double[][] H;
	
	/**
	 * Reproduction rate of predators per one prey eaten.
	 */
	private double b;
	
	/**
	 * Predator mortality rate.
	 */
	private double m;
	
	/**
	 * Constructor stores the values needed to calculate densities.
	 * @param P Puma densities
	 * @param l Puma diffustion rate
	 * @param H Hair densities
	 * @param b Reproduction rate of predators per one prey eaten
	 * @param m Predator mortality rate
	 */
	public Puma(double[][] P, double l, double[][] H, double b, double m) {
		super(P, l);
		this.H = H;
		this.b = b;
		this.m = m;
	}
	
	/**
	 * Provides implementation of the inherited getNextDensity method according to the PijNew equation
	 * provided in the notes. Returns the new density value at grid position i, j after a time interval dt.
	 * @param i Row index
	 * @param j Column index
	 * @param dt Time interval
	 */
	@Override
	public double getNextDensity(int i, int j, double dt) {
		
		// TODO Method body
		
		/*
		 * Note that there will be likely values in the equation that can be calculated once
		 * and stored in fields to decrease execution time.
		 */

		return 0;
	}

}
