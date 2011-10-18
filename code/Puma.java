
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
		super(P, l);
		double[][] diffCo = new double[3][2];
		diffCo[1][1] = b;
		diffCo[0][1] = -m;
		setDiffCo(diffCo);
	}

}
