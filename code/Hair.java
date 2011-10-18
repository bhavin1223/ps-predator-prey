
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
		super(H, k);
		double[][] diffCo = new double[3][2];
		diffCo[0][0] = r;
		diffCo[1][0] = -a;
		setDiffCo(diffCo);
	}
	
}
