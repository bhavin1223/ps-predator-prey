package com.pred.tests;

import com.pred.prey.InOut;

import org.junit.After;
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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		IOtest = null;		
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
			assertEquals(50, IOtest.getrows());
			assertEquals(50, IOtest.getcols());
		} else if (fName
				.equals("/home/jorge/Library/PS/Coursework/islands.dat")) {
			assertEquals(1000, IOtest.getcols());
			assertEquals(800, IOtest.getrows());
		}

		assertNotNull(IOtest.getIntBuffer());
		assertEquals(IOtest.getrows() + 2, IOtest.getIntBuffer().length);
		assertEquals(IOtest.getcols() + 2, IOtest.getIntBuffer()[0].length);

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
