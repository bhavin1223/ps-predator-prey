import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * JUnit test case for the class Animal
 *
 * @version 1.1, November 3rd, 2011
 * @since 1.0
 */
public class TestAnimal extends TestCase {
	private double[][] densities;// = {{0.0, 0.0}, {0.0, 0.0}};
	private double[][] nextDensities;
	private double diffusionRate;
	private double[] diffCoIn = { 0.3, 0.4 };
	private double[] diffCoIn_2 = { 0.4, 0.5 };
	private Animal testAnimal, testAnimal2;
	private int numbAnimals;
	private Animal[] animals;
	private final int M = 102, N = 103;
	private double dt, r;

	/**
	 * Gets executed before each test. Initialises the object under test.
	 */
	@Before
	public void setUp() {
		numbAnimals = 2;
		diffusionRate = 0.678;
		densities = new double[M][N];
		nextDensities = new double[M][N];
		dt = 4.0;
		r = 0.625;
		diffusionRate = 0.2;
		animals = new Animal[numbAnimals];
		

		testAnimal = new Animal(numbAnimals);
		testAnimal.setName("Puma");
		testAnimal.setDiffCo(diffCoIn_2);

		testAnimal2 = new Animal(densities, diffusionRate, diffCoIn);
		testAnimal2.setName("Hare");
		
		animals[0] = testAnimal;
		animals[1] = testAnimal2;
		
		for (int i = 0; i < densities.length; i++) {
			for (int j = 0; j < densities.length; j++) {
				densities[i][j] = 2;
			}
		}
		
		testAnimal.setDensities(densities);
		testAnimal.calcNextDensity(50, 50, dt, animals, 4);
		testAnimal.applyTimeStep();
	}

	@Test
	public void testAnimal() {
		assertNotNull(testAnimal);
		assertNotNull(testAnimal2);
	}

	@Test
	public void testDiffCoeff() {
		assertNotNull(testAnimal.getDiffCo());
		assertNotNull(testAnimal2.getDiffCo());

		assertEquals(diffCoIn, testAnimal2.getDiffCo());
		assertEquals(diffCoIn_2, testAnimal.getDiffCo());
	}

	@Test
	public void testName() {
		assertEquals("Puma", testAnimal.getName());
		testAnimal.setName("Hare");
		assertEquals("Hare", testAnimal.getName());
		assertEquals("Hare", testAnimal2.getName());
	}

	@Test
	public void testCalcNextDensity() {
		assertNotSame(2.0, testAnimal.getDensity(50, 50));
		
		assertNotNull(testAnimal.getNextDensities());
		assertEquals(13.2, testAnimal.getNextDensities()[50][50]);
/*		assertEquals(nextDensities[50][50]);
		assertEquals();*/

	}
	
	@Test
	public void testApplyTimeStep() {
			assertEquals(testAnimal.getDensities()[50][50], testAnimal.getNextDensities()[50][50]);
			assertEquals(testAnimal.getDensities()[49][49], testAnimal.getNextDensities()[49][49]);
	}
}
