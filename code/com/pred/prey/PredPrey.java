package com.pred.prey;

/**
 * Contains the main method. Controls the various components of the program.
 * 
 * @author Put your name here if you worked on this class
 * @version 1.5, November, 6th 2011
 * @since 1.0
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
			setStep(Double.parseDouble(args[6]));
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

		for (double i = 0; i < t; i += getStep()) {
		
				if ((stepnum % T == 0) || (stepnum == 0)) {
					for (int k = 0; k < animals.length; k++) {

						getOutput().printMeanDensity(
								"./outputs/Mean" + animals[k].getName()
										+ "Densities", animals[k].getDensities(), i);
						getOutput().printPpm("./outputs/" + animals[k].getName()
						+ stepnum + ".ppm", animals[k].getDensities(),getIo().getNeighbours());
					}

				}

			getGrid().syncUpdate();
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
		setStep(gui.getStep());
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
		setIo(new InOut(fileName));
		int[][] neighbours = getIo().getNeighbours();
		setGrid(new GridAlg(neighbours, animals));
		getGrid().setStep(getStep());
	}

	public static void createOutput() {
		setOutput(new Output());
	}

	/**
	 * NoAnimals holds the total number of animal assessed in the application
	 * 
	 * @param noAnimalsIn	Integer number with the total number of animals 
	 */
	public void setNoAnimals(int noAnimalsIn) {
		noAnimals = noAnimalsIn;
	}

	/**
	 * NoAnimals holds the total number of animal assessed in the application
	 * 
	 * @return noAnimals 	An integer number with the total number of animals 
	 */
	public int getNoAnimals() {
		return noAnimals;
	}
	
	/**
	 * Coefficients scaling how amounts of the different animals effect this
	 * animal. Matrix is rectangular N*N+1 where N is the number of types of
	 * animals First row are linear coefficients and the rest are cross
	 * quadratic terms Most of the grid will be 0 e.g. birth and death rates.
	 * 
	 * @param diffCoIn	Multidimensional array of double precision holding the coefficients
	 */
	public void setDiffCo(double[][] diffCoIn) {
		diffCo = diffCoIn;
	}

	/**
	 * Coefficients scaling how amounts of the different animals effect this
	 * animal. Matrix is rectangular N*N+1 where N is the number of types of
	 * animals First row are linear coefficients and the rest are cross
	 * quadratic terms Most of the grid will be 0 e.g. birth and death rates.
	 * 
	 * @return diffCoIn	Multidimensional array of double precision holding the coefficients
	 */
	public double[][] getDiffCo() {
		return diffCo;
	}
	
	/**
	 * Diffusion rate of this animal;
	 * 
	 * @param diffusionRateIn	An array of diffusion 
	 */
	public void setDiffusionRate(double[] diffusionRateIn) {
		diffusionRate = diffusionRateIn;
	}

	/**
	 * Diffusion rate of this animal;
	 * 
	 * @return The array of diffusion 
	 */
	public double[] getDiffusionRate() {
		return diffusionRate;
	}
	
	/**
	 * Filename holds the name of the file to read the bimap mask map from.
	 *  
	 * @param fileNameIn The path to the file to load
	 */
	public void setFilename(String fileNameIn) {
		fileName = fileNameIn;
	}

	/**
	 * Filename holds the name of the file to read the bimap mask map from.
	 *  
	 * @return The path to the file to load
	 */
	public String getFilename() {
		return fileName;
	}

	/**
	 * Io deals with the reading and parsing of the landscape files (.dat)
	 * 
	 * @return io	The class responsible for reading/parsing
	 */
	public static InOut getIo() {
		return io;
	}

	/**
	 * Io deals with the reading and parsing of the landscape files (.dat)
	 * 
	 * @param A class responsible for reading/parsing
	 */
	public static void setIo(InOut io) {
		PredPrey.io = io;
	}

	/**
	 * The GridAlg class runs the algorithm corresponding to the user's input with the user defined
	 * settings. Also performs measurements on the efficiency of the chosen
	 * algorithm.
	 * 
	 * @return The class that runs the algorithm
	 */
	public static GridAlg getGrid() {
		return grid;
	}

	/**
	 * The GridAlg class runs the algorithm corresponding to the user's input with the user defined
	 * settings. Also performs measurements on the efficiency of the chosen
	 * algorithm.
	 * 
	 * @param grid S class that runs the algorithm
	 */
	public static void setGrid(GridAlg grid) {
		PredPrey.grid = grid;
	}

	/**
	 * The variable step holds the time step that defines the time interval at which the calculations are made
	 * 
	 * @return A double precision time step 
	 */
	public static double getStep() {
		return step;
	}

	/**
	 * The variable step holds the time step that defines the time interval at which the calculations are made
	 * 
	 * @param step A double precision time step
	 */
	public static void setStep(double step) {
		PredPrey.step = step;
	}

	/**
	 * The class Output is responsible to write the outcomes of the computations to image files.
	 * 
	 * @return A class that creates visualisations of the results
	 */
	public static Output getOutput() {
		return output;
	}

	/**
	 * The class Output is responsible to write the outcomes of the computations to image files.
	 * 
	 * @param output	An Ouput class that creates visualisations of the results
	 */
	public static void setOutput(Output output) {
		PredPrey.output = output;
	}


}
