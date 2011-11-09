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
	private static MapReader io;
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
	public static void run(double[] parameters, String fileNameIn) throws Exception 
	{
		try
		{
			
			System.out.println("...");
			noAnimals = 2;

			diffCo = new double[2][2];
			diffusionRate = new double[2];

			diffCo[0][0] = parameters[0];
			diffCo[0][1] = parameters[1];
			diffusionRate[0] = parameters[2];
			diffCo[1][0] = parameters[3];
			diffCo[1][1] = parameters[4];
			diffusionRate[1] = parameters[5];
			step = parameters[6];
			T = (int) parameters[7];
			fileName = fileNameIn;
			createAnimals();
			createGrid();
			createOutput();

			System.out.println("Cleaning output directory...");
		
			getOutput().cleanDirectory("./outputs/");
		
			System.out.println("Simulating populations...");

			int stepnum = 0;
		
			System.out.println(animals[0].getDensities().length);
			System.out.println(getIo().getNeighbours().length);

			for (double i = 0; i < t; i += getStep()) {
			
					if ((stepnum % T == 0) || (stepnum == 0)) 
					{
						for (int k = 0; k < animals.length; k++) 
						{

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

			if(animals[0].bigChange)
			{
				System.out.println("\n*** Warning:\nVery large changes in local density occurred over single iterations, suggest you use a smaller timestep\n***\n");
			}
			System.out.println("...done");
		}
		catch(Exception e)
		{
			System.out.println("Something's gone wrong");
			System.out.println(e.getMessage());
		}

	}
	public static void run(double[] parameters, String fileNameIn, double[] parRange, int indicator) throws Exception 
	{
		try
		{
			noAnimals = 2;

			diffCo = new double[2][2];
			diffusionRate = new double[2];

			

			System.out.println("Cleaning output directories...");
		
			for (int l=0; l<parRange.length; l++)
			{
				getOutput().cleanDirectory("./outputs"+(l+1)+"/");
			}
			
			System.out.println("Simulating populations...");

			int stepnum = 0;
		
			
			for (int l=0; l<parRange.length; l++)
			{
				diffCo[0][0] = parameters[0];
				diffCo[0][1] = parameters[1];
				diffusionRate[0] = parameters[2];
				diffCo[1][0] = parameters[3];
				diffCo[1][1] = parameters[4];
				diffusionRate[1] = parameters[5];
				step = parameters[6];
				T = (int) parameters[7];
				parameters[indicator] = parRange[l];
				fileName = fileNameIn;
				createAnimals();
				createGrid();
				createOutput();
				
				System.out.println(animals[0].getDensities().length);
				System.out.println(getIo().getNeighbours().length);
	
				for (double i = 0; i < t; i += getStep()) {
				
						if ((stepnum % T == 0) || (stepnum == 0)) 
						{
							for (int k = 0; k < animals.length; k++) 
							{
	
								getOutput().printMeanDensity(
										"./outputs"+(l+1)+"/Mean" + animals[k].getName()
												+ "Densities", animals[k].getDensities(), i);
								getOutput().printPpm("./outputs"+(l+1)+"/" + animals[k].getName()
								+ stepnum + ".ppm", animals[k].getDensities(),getIo().getNeighbours());
							}
	
						}
	
					getGrid().syncUpdate();
					stepnum += 1;
	
				}
	
				if(animals[0].bigChange)
				{
					System.out.println("\n*** Warning:\nVery large changes in local density occurred over single iterations, suggest you use a smaller timestep\n***\n");
				}
			}
			System.out.println("...done");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Takes the values from the gui and sets them to the main program
	 * 
	 * @param gui
	 *            the gui for the program
	 */

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
		setIo(new MapReader(fileName));
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
	public static MapReader getIo() {
		return io;
	}

	/**
	 * Io deals with the reading and parsing of the landscape files (.dat)
	 * 
	 * @param A class responsible for reading/parsing
	 */
	public static void setIo(MapReader io) {
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



public static void main(String args[])
	{
		if (args.length == 0)
		{
			new InputFrame();
		}
		else
		{ 
			System.out.println("File Selected");
		}
	}
}
