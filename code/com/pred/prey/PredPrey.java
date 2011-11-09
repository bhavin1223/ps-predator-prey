package com.pred.prey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Contains the main method. Controls the various components of the program.
 * 
 * @author Put your name here if you worked on this class
 * @version 1.5, November, 6th 2011
 * @since 1.0
 */
public class PredPrey {
	private  double[][] diffCo;
	private  double[] diffusionRate;
	private  double step;
	private  int noAnimals;
	private  Animal[] animals;
	private  String fileName;
	private  MapReader io;
	private  GridAlg grid;
	private  Output output;
	private  double t = 500;
	private  int T=20;

	/**
	 * Controls the IO and Algorithm classes.
	 * 
	 * @param args
	 *            Program does not take command line arguments
	 */
	public  void run(double[] parameters, String fileNameIn)
	{
		try
		{
			
			noAnimals = 2;

			diffCo = new double[2][2];
			diffusionRate = new double[2];

			diffCo[0][0] = parameters[0];
			diffCo[0][1] = parameters[1];
			diffusionRate[0] = parameters[4];
			diffCo[1][0] = parameters[2];
			diffCo[1][1] = parameters[3];
			diffusionRate[1] = parameters[5];
			step = parameters[6];
			T = (int) parameters[7];
			fileName = fileNameIn;
			setIo(new MapReader(fileName));
			createAnimals();
			createGrid();
			createOutput();
		
			getOutput().cleanDirectory("./outputs/");
			
			getOutput().GetLandArea(getIo().getNeighbours());
		
			System.out.print("Simulating populations...");

			int stepnum = 0;

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
													System.out.print('.');

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
	
	// Second (very similar) version of the run method used 
	// to simulate a range of parameters
	public  void run(double[] parameters, String fileNameIn, double[] parRange, int indicator) 
	{
//		try
//		{
			noAnimals = 2;

			diffCo = new double[2][2];
			diffusionRate = new double[2];

		
			createOutput();
			for (int l=0; l<parRange.length; l++)
			{
				getOutput().cleanDirectory("./outputs"+(l+1)+"/");
			}
			
			System.out.print("Simulating populations...");

			int stepnum = 0;
		
			
			for (int l=0; l<parRange.length; l++)
			{
				diffCo[0][0] = parameters[0];
				diffCo[0][1] = parameters[1];
				diffusionRate[0] = parameters[4];
				diffCo[1][0] = parameters[2];
				diffCo[1][1] = parameters[3];
				diffusionRate[1] = parameters[5];
				step = parameters[6];
				T = (int) parameters[7];
				parameters[indicator] = parRange[l];
				fileName = fileNameIn;
				setIo(new MapReader(fileName));
				createAnimals();
				createGrid();
	
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
															System.out.print('.');
	
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
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//		}
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
	 * Creates the animals as specified by the gui or file
	 */
	public  void createAnimals() {
		animals = new Animal[noAnimals];

		for (int i = 0; i < noAnimals; i++) {
			animals[i] = new Animal(noAnimals);
			animals[i].setDiffCo(diffCo[i]);
			animals[i].setDiffusionRate(diffusionRate[i]);
			animals[i].initiateDensities(getIo().getNeighbours().length,getIo().getNeighbours()[0].length);
		}
	
		 animals[0].setName("Hare"); 
		 animals[1].setName("Puma");
	}

	public  void createGrid() {
		int[][] neighbours = getIo().getNeighbours();
		setGrid(new GridAlg(neighbours, animals));
		getGrid().setStep(getStep());
	}

	public  void createOutput() {
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
	public  MapReader getIo() {
		return io;
	}

	/**
	 * Io deals with the reading and parsing of the landscape files (.dat)
	 * 
	 * @param A class responsible for reading/parsing
	 */
	public  void setIo(MapReader io) {
		this.io = io;
	}

	/**
	 * The GridAlg class runs the algorithm corresponding to the user's input with the user defined
	 * settings. Also performs measurements on the efficiency of the chosen
	 * algorithm.
	 * 
	 * @return The class that runs the algorithm
	 */
	public  GridAlg getGrid() {
		return grid;
	}

	/**
	 * The GridAlg class runs the algorithm corresponding to the user's input with the user defined
	 * settings. Also performs measurements on the efficiency of the chosen
	 * algorithm.
	 * 
	 * @param grid S class that runs the algorithm
	 */
	public  void setGrid(GridAlg grid) {
		this.grid = grid;
	}

	/**
	 * The variable step holds the time step that defines the time interval at which the calculations are made
	 * 
	 * @return A double precision time step 
	 */
	public  double getStep() {
		return step;
	}

	/**
	 * The variable step holds the time step that defines the time interval at which the calculations are made
	 * 
	 * @param step A double precision time step
	 */
	public  void setStep(double step) {
		this.step = step;
	}

	/**
	 * The class Output is responsible to write the outcomes of the computations to image files.
	 * 
	 * @return A class that creates visualisations of the results
	 */
	public  Output getOutput() {
		return output;
	}

	/**
	 * The class Output is responsible to write the outcomes of the computations to image files.
	 * 
	 * @param output	An Ouput class that creates visualisations of the results
	 */
	public  void setOutput(Output output) {
		this.output = output;
	}
	
	/**
	 * Returns the options listed in the settings.txt file in the correct format for the program.
	 * @return Parameters array.
	 */
	private  double[] getSettings() {
		
		double[] params;
		String inLine, fileName = "settings.txt";
		String[] tokens;
		
		int numParams = 8;
		params = new double[numParams];
		
		// Perform the read in a catch block in case of exceptions
		try {
		
			// Create a new buffered reader
			File inFile = new File(fileName);
			FileInputStream fis = new FileInputStream(inFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			BufferedReader br = new BufferedReader(isr);
			
			// Loop over the lines, reading the parameters into the array
			for(int i = 0; i < numParams; i++) {
				inLine = br.readLine();
				tokens = inLine.split("=");
				params[i] = Double.parseDouble(tokens[1].replaceAll(" ", ""));
			}
			
			// Close the reader
			br.close();
		}

		// Catch errors
		catch(Exception e) {
			System.out.println("Error reading from settings file.");
			System.exit(0);
		}	
		return params;
	}

    public static void main(String args[]) {
	
	// Launch GUI or take options from file depending on command line arguments
	if (args.length == 0) new InputFrame();
	else { 
	    PredPrey pp = new PredPrey();
	    double[] params = pp.getSettings();
	    String inputFile = args[0];
	    pp.run(params, inputFile);
	}
    }
}
