
/**
 * Contains the main method. Controls the various components of the program.
 * @author Put your name here if you worked on this class
 * @version 1.2, October, 31th 2011
 */
public class PredPrey {
	
	private static double[][] diffCo;
	private static double[] diffusionRate;
	private static double step;
	private static int noAnimals;
	private static Animal[] animals;
	private static String fileName;
	private static InOut io;
	private static GridAlg grid;
	private static Output output;
	private static double t = 1000;
		
	/**
	 * Controls the IO and Algorithm classes.
	 * @param args Program does not take command line arguments
	 */
	public static void main(String[] args) throws Exception {
		// read values from GUI - maybe stored in an array
		TestFrame gui = new TestFrame();
		gui.setVisible(true);

		while(gui.getRun() == false) {
			System.out.println(gui.getRun());
		}

		System.out.println("woo");		
		
		setParameters(gui);
		createAnimals();
		createGrid();
		createOutput();
		
		
		for (double i= 0; i<t/100; i+=step) {
			for (int j=0; j<100; j++) {
				grid.gridSyncUpdate();		
			}
			output.printMeanDensity("MeanHareDensityAfter" + i, animals[0].getDensities(), i);	
			output.printMeanDensity("MeanPumaDensityAfter" + i, animals[1].getDensities(), i);
			output.printPpm("HareDensitiesAfter" +i, animals[0].getDensities(), "black&white");
			output.printPpm("PumaDensitiesAfter" +i, animals[1].getDensities(), "green&white");
		}
		
		
	}	
	
	/**
	 * Takes the values from the gui and sets them to the main program
	 * @param gui the gui for the program
	 */
	public static void setParameters(TestFrame gui) {
		
		noAnimals = gui.getNoAnimals();
		diffCo = gui.getDiffCo();
		diffusionRate = gui.getDiffusion();
		step = gui.getStep();
		fileName = gui.getFileName();
		
	}
	
	public Animal[] getAnimals() {
		return animals;
	}

	public void setAnimals(Animal[] animals) {
		this.animals = animals;
	}
	
	/**
	 * Creates the animals as specified by the gui
	 */
	public static void createAnimals() {
		
		Animal[] theseAnimals = new Animal[noAnimals];
		
		for(int i=0;i<noAnimals;i++) {
			
			theseAnimals[i] = new Animal(noAnimals);
			theseAnimals[i].setDiffCo(diffCo[i]);
			theseAnimals[i].setDiffusionRate(diffusionRate[i]);
			
		}
		
		animals = theseAnimals;		
	}
	
	
	public static void createGrid() {
		
		io = new InOut(fileName);
		int[][] neighbours = io.getNeighbours();
		grid = new GridAlg(neighbours,animals);
		grid.setStep(step);			
		
	}
	
	public static void createOutput() {
		output = new Output();
	}	
}
