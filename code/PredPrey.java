
/**
 * Contains the main method. Controls the various components of the program.
 * @author Jorge Put your name here if you worked on this class
 * @version 1.0, October, 30th 2011
 */
public class PredPrey {
	private Animal[] animals = null;
	double[] parameters;
	int noAnimals = 0;
	
	/**
	 * Constructor
	 */
	public PredPrey() {
		
		// read values from GUI - maybe stored in an array
		TestFrame gui = new TestFrame();
		parameters = gui.getParameters();
		
		//Create animals
		
		//TODO - Create an array of animals 
		animals[0] = new Animal(noAnimals);
		
		// TODO - Method body
		GridAlg ga = new GridAlg(animals);
	}
	


	public Animal[] getAnimals() {
		return animals;
	}

	public void setAnimals(Animal[] animals) {
		this.animals = animals;
	}
	
	/**
	 * Controls the IO and Algorithm classes.
	 * @param args Program does not take command line arguments
	 */
	public static void main(String[] args) {
			PredPrey pp = new PredPrey();	
	}

}
