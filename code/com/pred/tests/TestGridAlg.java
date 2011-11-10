package com.pred.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pred.prey.Animal;
import com.pred.prey.GridAlg;

/**
 * JUnit test case for GridAlg.java
 * 
 * @author Jorge Moreira
 * @version 1.0, November 5th, 2011
 */
public class TestGridAlg {
	private int[][] neighbours;
	private static int noAnimals = 2;
	private static double[][] diffCo;
	private static double[] diffusionRate;
	private GridAlg grid;
	Animal[] theseAnimals;
	private int expected;
	private double actual, previous;
	private int m, n;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = 5;
		n = 6;
		neighbours = new int[m + 2][n + 2];
		diffCo = new double[2][2];
		diffusionRate = new double[2];
		
		for (int i = 0; i < neighbours.length; i++) { 			
			for (int j = 0; j < neighbours[0].length; j++) {
				if (i == 0 || j == 0 || (i == n + 1) || (j == m + 1)) {
					neighbours[i][j] = -1;
				} else {
					neighbours[i][j] = 4;
				}
			}
		}
		
		theseAnimals = new Animal[noAnimals];
		
		diffCo[0][0] = 0.08;
		diffCo[0][1] = 0.04;
		diffusionRate[0] = 0.02;
		diffCo[1][0] = 0.06;
		diffCo[1][1] = 0.2;

		for (int i = 0; i < noAnimals; i++) {
			theseAnimals[i] = new Animal(noAnimals);
			theseAnimals[i].setDiffCo(diffCo[i]);
			theseAnimals[i].setDiffusionRate(diffusionRate[i]);
		}
		
		grid = new GridAlg(neighbours, theseAnimals);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		grid = null;
		theseAnimals = null;
		diffCo = null;
		diffusionRate = null;
		neighbours = null;
	}

	/**
	 * Test method for {@link com.pred.prey.GridAlg#getNeighbours(int, int)}.
	 */
	@Test
	public final void testGetNeighboursIntInt() {
		expected = 4;
		assertEquals(expected, grid.getNeighbours(2, 2));
	}

	/**
	 * Test method for {@link com.pred.prey.GridAlg#initaliseDensities()}.
	 */
	@Test
	public final void testInitaliseDensities() {
		actual = grid.getAnimals()[0].getDensity(2,2);
		assertNotNull(actual);
		assertTrue(actual <= 10);
		assertTrue(actual >= 0);
	}

	/**
	 * Test method for {@link com.pred.prey.GridAlg#syncUpdate()}.
	 */
	@Test
	public final void testSyncUpdate() {
		assertNotNull(grid.getAnimals()[0].getNextDensities());
		assertNotNull(grid.getAnimals()[1].getNextDensities());
		
		actual = grid.getAnimals()[0].getNextDensities()[2][2];
		assertEquals(actual, grid.getAnimals()[0].getDensity(2, 2), 0.0);
		actual = grid.getAnimals()[1].getNextDensities()[2][2];
		assertEquals(actual, grid.getAnimals()[1].getDensity(2, 2), 0.0);

		previous = grid.getAnimals()[0].getNextDensities()[2][2];
		grid.setStep(3.0);
		grid.syncUpdate();
		actual = grid.getAnimals()[0].getNextDensities()[2][2];
		assertNotSame(previous, actual);
		
		previous = grid.getAnimals()[1].getNextDensities()[2][2];
		grid.setStep(3.0);
		grid.syncUpdate();
		actual = grid.getAnimals()[1].getNextDensities()[2][2];
		assertNotSame(previous, actual);
	}

	/**
	 * Test method for {@link com.pred.prey.GridAlg#gridSyncUpdate()}.
	 */
	@Test
	public final void testGridSyncUpdate() {
		assertNotNull(grid.getAnimals()[0].getNextDensities());
		assertNotNull(grid.getAnimals()[1].getNextDensities());
		
		actual = grid.getAnimals()[0].getNextDensities()[2][2];
		assertEquals(actual, grid.getAnimals()[0].getDensity(2, 2), 0.0);
		actual = grid.getAnimals()[1].getNextDensities()[2][2];
		assertEquals(actual, grid.getAnimals()[1].getDensity(2, 2), 0.0);

		previous = grid.getAnimals()[0].getNextDensities()[2][2];
		grid.setStep(3.0);
		grid.gridSyncUpdate();
		actual = grid.getAnimals()[0].getNextDensities()[2][2];
		assertNotSame(previous, actual);
		
		previous = grid.getAnimals()[1].getNextDensities()[2][2];
		grid.setStep(3.0);
		grid.gridSyncUpdate();
		actual = grid.getAnimals()[1].getNextDensities()[2][2];
		assertNotSame(previous, actual);
	}
}
