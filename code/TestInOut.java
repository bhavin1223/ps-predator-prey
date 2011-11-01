import org.junit.Test;
import org.junit.Before;

import junit.framework.TestCase;

/**
 * JUnit test class that make some checks on InOut.java. 
 * 
 * @author Jorge M.
 * 
 */
public class TestInOut extends TestCase {
	// String fName = "/home/jorge/Library/PS/Coursework/islands.dat";
	private String fName = "/home/jorge/Library/PS/Coursework/small.dat";
	private InOut IOtest;
		
	/**
     * Gets executed before each test. Initialises the object under test.
     */
    @Before
    public void setUp() {
    	IOtest = new InOut();
		IOtest.loadLandscape(fName);
		IOtest.countNeighbours();
    }

	/**
	 * Test if loadLanscape method returns expected values
	 */
	@Test
	public void testLoadLandscape() {
		assertEquals("ok", IOtest.loadLandscape(fName));
	}

	/**
	 * Test values read from the file stored in intBuffer are correct
	 */
	@Test
	public void testIntBuffer() {
		if (fName.equals("/home/jorge/Library/PS/Coursework/small.dat")) {
			assertEquals(50, IOtest.getM());
			assertEquals(50, IOtest.getN());
		} else if (fName
				.equals("/home/jorge/Library/PS/Coursework/islands.dat")) {
			assertEquals(1000, IOtest.getN());
			assertEquals(800, IOtest.getM());
		}

		assertNotNull(IOtest.getIntBuffer());
		assertEquals(IOtest.getM() + 2, IOtest.getIntBuffer().length);
		assertEquals(IOtest.getN() + 2, IOtest.getIntBuffer()[0].length);

		if (fName.equals("/home/jorge/Library/PS/Coursework/small.dat")) {
			assertEquals(0, IOtest.getIntBuffer()[10][21]);
			assertEquals(0, IOtest.getIntBuffer()[11][19]);
			assertEquals(1, IOtest.getIntBuffer()[11][20]);
			assertEquals(1, IOtest.getIntBuffer()[11][21]);
		} else if (fName
				.equals("/home/jorge/Library/PS/Coursework/islands.dat")) {
			assertEquals(1, IOtest.getIntBuffer()[86][305]);
		}
	}

	/**
	 * Test values calculated for the neighbours of each land element stored in neighbours are correct
	 */
	@Test
	public void testGetNeighbours() {
		assertNotNull(IOtest.getNeighbours());
		assertEquals(4,IOtest.getNeighbours()[20][20]); 
		assertEquals(3,IOtest.getNeighbours()[15][17]); 
		assertEquals(2,IOtest.getNeighbours()[14][17]); 
		assertEquals(2,IOtest.getNeighbours()[36][19]);
	}
}
