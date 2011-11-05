/**
 * 
 */
package com.pred.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pred.prey.PredPrey;
import com.pred.prey.SwingTest;

/**
 * @author jorge
 * 
 */
public class TestPredPrey {
	private PredPrey pd;
	private double[][] diffCo;
	private int noAnimals;
	private double[] diffusionRate;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		noAnimals = 2;
		pd = new PredPrey();
		pd.setNoAnimals(noAnimals);

		diffCo = new double[noAnimals][noAnimals];
		diffCo[0][0] = 0.8;
		diffCo[0][1] = 0.6;
		diffCo[1][0] = 0.6;
		diffCo[1][1] = 0.9;

		diffusionRate = new double[noAnimals];
		diffusionRate[0] = 0.7;

		pd.setDiffCo(diffCo);
		pd.setDiffusionRate(diffusionRate);
		pd.createAnimals();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pd = null;
		noAnimals = 0;
		diffCo = null;
		diffusionRate = null;
	}

	/**
	 * Test method for {@link com.pred.prey.PredPrey#main(java.lang.String[])}.
	 */
	@Test
	public final void testMain() {
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.pred.prey.PredPrey#createAnimals()}.
	 */
	@Test
	public final void testCreateAnimals() {
		assertNotNull(pd.getAnimals());
		assertEquals("Hare", pd.getAnimals()[0].getName());
		assertEquals("Puma", pd.getAnimals()[1].getName());
		assertEquals(diffCo[0], pd.getAnimals()[0].getDiffCo());
		assertEquals(diffCo[1], pd.getAnimals()[1].getDiffCo());
/*		for (int x = 0; x < (pd.getAnimals()[0].getDiffCo().length); x++) {
			//for (int y = 0; y < (pd.getAnimals()[0].getDiffCo().length); y++) {
				System.out.println(pd.getAnimals()[0].getDiffCo()[x]);
				System.out.println(pd.getAnimals()[1].getDiffCo()[x]);
			}
		}*/
	}

	/**
	 * Test method for {@link com.pred.prey.PredPrey#createGrid()}.
	 */
	@Test
	public final void testCreateGrid() {
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.pred.prey.PredPrey#createOutput()}.
	 */
	@Test
	public final void testCreateOutput() {
		// fail("Not yet implemented"); // TODO
	}

}
