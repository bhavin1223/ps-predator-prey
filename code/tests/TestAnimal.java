import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestAnimal extends TestCase {
	private double[][] densities;
	private double[][] nextDensities;
	private double diffusionRate;
	private double[] diffCoefficients;// = {0.3, 0.4};
	private Animal testAnimal;
	private int numbAnimal;

	/**
	 * Gets executed before each test. Initialises the object under test.
	 */
	@Before
	public void setUp() {
		numbAnimal = 2;

		testAnimal = new Animal(numbAnimal);
		testAnimal.setName("Puma");
	}
	
	@Test
	public void testAnimal() {
		assertNotNull(testAnimal);
	}
	
	@Test
	public void testDiffCoeff() {
		assertNotNull(testAnimal.getDiffCo());
	}
	
	@Test
	public void testName() {
		assertEquals("Puma", testAnimal.getName());
		testAnimal.setName("Hare");
		assertEquals("Hare", testAnimal.getName());
	}
}

