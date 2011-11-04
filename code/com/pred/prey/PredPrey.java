package com.pred.prey;

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
		
		if(args.length == 0){
		
			TestFrame gui = new TestFrame();
			gui.setVisible(true);
			while(gui.getRun() == false) {
				System.out.println(gui.getRun());
			}
				
			setParameters(gui);
			createAnimals();
			createGrid();
			createOutput();
		
		} else if (args.length == 8){
			
			noAnimals = 2;
			
			diffCo = new double[2][2];
			diffusionRate = new double[2];
			
			diffCo[0][0] = Double.parseDouble(args[0]);
			diffCo[0][1] = Double.parseDouble(args[1]);
			diffusionRate[0] = Double.parseDouble(args[2]);
			diffCo[1][0] = Double.parseDouble(args[3]);
			diffCo[1][1] = Double.parseDouble(args[4]);
			diffusionRate[1] = Double.parseDouble(args[5]);
			step = Double.parseDouble(args[6]);
			fileName=args[7];
			
			createAnimals();
			createGrid();
			createOutput();
			
		} else {
			throw new IllegalArgumentException("Number of arguments should be 0 or 8");
		}
		
		System.out.println("Simulating populations...");		

		
		for (double i= 0; i<t/100; i+=step) {
			for (int j=0; j<100; j++) {
				grid.syncUpdate();		
			}
			
			String colour;
			
			for (int k=0;k<animals.length;k++) {
				
				if(k==0) {
					colour = "black&white";
				}
				else {
					colour = "black&white";
				}
				
				output.printMeanDensity("./outputs/Mean" + animals[k].getName() + "Densities", animals[k].getDensities(), i);
				output.printPpm("./outputs/" + animals[k].getName() + i +".ppm", animals[k].getDensities(), colour);
			}
		}
		
		System.out.println("done");
		
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
		
		theseAnimals[0].setName("Hare");
		theseAnimals[1].setName("Puma");				
		
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
