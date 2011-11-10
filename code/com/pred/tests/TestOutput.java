package com.pred.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pred.prey.Output;

/**
 * JUnit test class for TestOutput.java.
 * 
 * @author Jorge M. 
 */
public class TestOutput {
	private Output output;
	private double[][] density = { { 0.3, 0.4 }, { 0.2, 0.5 } };
	private int[][] neighbours = { { 3, 4 }, { 2, 4 } };
	private int time;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		time = 2;
		output = new Output();
		output.printPpm("testOutputFile.ppm", density, neighbours);
		output.printMeanDensity("testOutputFile2.ppm", density, time);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		output = null;
		density = null;
		neighbours = null;
	}

	/**
	 * Test method for
	 * {@link com.pred.prey.Output#printPpm(java.lang.String, double[][], int[][])}
	 * .
	 */
	@Test
	public final void testPrintPpm() {
		FileInputStream fstream;
		String strLine;
		
		try {
			fstream = new FileInputStream("./test/testOutputFile.ppm");
			assertNotNull(fstream);
			DataInputStream in = new DataInputStream(fstream);
			assertNotNull(in);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			assertNotNull(br);

			try {
				while ((strLine = br.readLine()) != null) {
					assertNotNull(strLine);
				}
			} catch (IOException ioe) {
				ioe.getMessage();
			}

			try {
				fstream.close();
				in.close();
				br.close();
			} catch (IOException ioe) {
				ioe.getMessage();
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.getMessage();
		}
	}

	/**
	 * Test method for
	 * {@link com.pred.prey.Output#printMeanDensity(java.lang.String, double[][], double)}
	 * .
	 */
	@Test
	public final void testPrintMeanDensity() {
		FileInputStream fstream;
		String strLine;
		
		try {
			fstream = new FileInputStream("./test/testOutputFile2.ppm");
			assertNotNull(fstream);
			DataInputStream in = new DataInputStream(fstream);
			assertNotNull(in);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			assertNotNull(br);
	
			try {
				while ((strLine = br.readLine()) != null) {
					assertNotNull(strLine);
				}
			} catch (IOException ioe) {
				ioe.getMessage();
			}

			try {
				fstream.close();
				in.close();
				br.close();
			} catch (IOException ioe) {
				ioe.getMessage();
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.getMessage();
		}
	}
}
