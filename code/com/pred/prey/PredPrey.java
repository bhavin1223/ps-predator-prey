package com.pred.prey;

/**
 * Contains the main method. Controls the various components of the program.
 * 
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
	private static double t = 500;
	private static int T=20;

	/**
	 * Controls the IO and Algorithm classes.
	 * 
	 * @param args
	 *            Program does not take command line arguments
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			TestFrame gui = new TestFrame();
			gui.setVisible(true);
			while (gui.getRun() == false) {
				System.out.println(gui.getRun());
			}

			setParameters(gui);
			createAnimals();
			createGrid();
			createOutput();

		} else if (args.length == 9) {
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
			T = Integer.parseInt(args[7]);
			fileName = args[8];

			createAnimals();
			createGrid();
			createOutput();
		} else {
			throw new IllegalArgumentException(
					"Number of arguments should be 0 or 8");
		}

		System.out.println("Simulating populations...");

		int stepnum = 0;

		for (double i = 0; i < t; i += step) {
		
				if ((stepnum % T == 0) || (stepnum == 0)) {
					for (int k = 0; k < animals.length; k++) {

						output.printMeanDensity(
								"./outputs/Mean" + animals[k].getName()
										+ "Densities", animals[k].getDensities(), i);
						output.printPpm("./outputs/" + animals[k].getName()
						+ stepnum + ".ppm", animals[k].getDensities(),io.getNeighbours());
					}

				}

			grid.syncUpdate();
			stepnum += 1;

		}

		System.out.println("done");

	}

	/**
	 * Takes the values from the gui and sets them to the main program
	 * 
	 * @param gui
	 *            the gui for the program
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
		animals = new Animal[noAnimals];

		for (int i = 0; i < noAnimals; i++) {
			animals[i] = new Animal(noAnimals);
			animals[i].setDiffCo(diffCo[i]);
			animals[i].setDiffusionRate(diffusionRate[i]);
		}
	
		 animals[0].setName("Hare"); 
		 animals[1].setName("Puma");
	}

	public static void createGrid() {
		io = new InOut(fileName);
		int[][] neighbours = io.getNeighbours();
		grid = new GridAlg(neighbours, animals);
		grid.setStep(step);
	}

	public static void createOutput() {
		output = new Output();
	}

	public void setNoAnimals(int noAnimalsIn) {
		noAnimals = noAnimalsIn;
	}

	public int getNoAnimals() {
		return noAnimals;
	}
	
	public void setDiffCo(double[][] diffCoIn) {
		diffCo = diffCoIn;
	}

	public double[][] getDiffCo() {
		return diffCo;
	}
	
	public void setDiffusionRate(double[] diffusionRateIn) {
		diffusionRate = diffusionRateIn;
	}

	public double[] getDiffusionRate() {
		return diffusionRate;
	}
}
