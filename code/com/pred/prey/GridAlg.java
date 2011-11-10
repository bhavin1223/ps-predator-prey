package com.pred.prey;

import java.util.Random;

/**
 * Runs the algorithm corresponding to the user's input with the user defined
 * settings. Also performs measurements on the efficiency of the chosen
 * algorithm.
 * 
 * @author Put your name here if you work on this class
 * @version 1.2, October, 30th 2011
 * @since 1.0
 */
public class GridAlg {
	
	/**
	 * Array containing the density distribution of all the animals
	 */
	private Animal[] animals;

	/**
	 * Array containing the neighbours each grid point should check for density
	 * updates based on land and water.
	 */
	private int[][] neighbours;

	/**
	 * Update frequency
	 */
	private double dt;

	/**
	 * Random number generator
	 */
	private Random random;

	/**
	 * Class constructor - Version taking an array of animals as argument and
	 * the Neighbours Array from the input grid.
	 * 
	 */
	public GridAlg(int[][] neighbours, Animal[] animals) {
		setNeighbours(neighbours);
		setAnimals(animals);
		random = new Random(2011);
		initaliseDensities();
	}

	/**
	 * Sets the array of animals to an input density array
	 * 
	 * @param animals
	 *           array of animals setting to the grids animals.
	 */
	public void setAnimals(Animal[] animals) {
		this.animals = animals;
	}

	/**
	 * @return Returns the array of animals on the map.
	 */
	public Animal[] getAnimals() {
		return this.animals;
	}

	/**
	 * Returns the time step
	 */
	public double getStep() {
		return this.dt;
	}


	/**
	 * Sets the time step
	 * 
	 */
	public void setStep(double step) {
		this.dt = step;
	}

	/**
	 * Returns the number of land neighbours the grid point i, j has.
	 * 
	 * @param i
	 *            Row index
	 * @param j
	 *            Column index
	 * @return neighbours Number of land neighbours point i,j has.
	 */
	public int getNeighbours(int i, int j) {
		return neighbours[i][j];
	}

	/**
	 * Returns the array of land neighbours.
	 * 
	 * @return neighbours Number of land neighbours point i,j has.
	 */    
	public int[][] getNeighbours() {
		return neighbours;
	}

	/**
	 * Set the current object neighbours array to the array in InOut.java. The
	 * number of neighbours for each land element is directly accessible.
	 */
	public void setNeighbours(int[][] neighbours) {
		this.neighbours = neighbours;
	}

	/**
	 * Initialises the densities of all the animals to a pseudo-normally
	 * distributed number. Only the land squares are filled with animals.
	 */
	public void initaliseDensities() {
		double randomValue;

		//loops over all of the animals
		for (int i = 0; i < getAnimals().length; i++) {
		
			//loops over all of the cells filling with a random density
			for (int x = 0; x < getNeighbours()[0].length; x++) {			
				for (int y = 0; y < getNeighbours().length; y++) {				
					if (getNeighbours(y, x) == -1) {
						randomValue = 0;
					} else {
						randomValue = random.nextDouble()*5;
					}

					//sets the random value to the array
					getAnimals()[i].setDensity(y, x, randomValue);
					getAnimals()[i].setNextDensity(y, x, randomValue);
				}
			}
		}
	}

	/**
	 * Updates the grid and animals in parallel. runs sequentially through the
	 * cells before replacing at end.
	 */
	public void syncUpdate() {
		
		// Loop over all of the animals
		for (int i = 0; i < animals.length; i++) {

			// Loop over all of the cells, calculating the next densities
			for (int x = 1; x < (getNeighbours()[0].length-1); x++) {
				for (int y = 1; y < (getNeighbours().length-1); y++) {
					if (getNeighbours(y, x) != -1) {
					    animals[i].calcNextDensity(y, x, dt, animals,neighbours[y][x]);
					}
				}
			}
		}

		// Apply change at end of all calculations
		for (int i = 0; i < animals.length; i++) {
			animals[i].applyTimeStep();
		}

	}

	/**
	 * Updates the grid and in parallel. Runs sequentially through the cells for
	 * one animal then replaces Goes through all animals in turn.
	 */
	public void gridSyncUpdate() {

		// Loop over all of the animals
		for (int i = 0; i < animals.length; i++) {

			// Loop over all of the cells, calculating the next densities
			for (int x = 1; x < getNeighbours()[0].length - 1; x++) {
				for (int y = 1; y < getNeighbours().length - 1; y++) {
					if (getNeighbours(y, x) != -1) {
						animals[i].calcNextDensity(y, x, dt, animals,
								neighbours[y][x]);
					}
				}
			}

			// Apply densities update while looping through animals
			animals[i].applyTimeStep();
		}
	}
}
