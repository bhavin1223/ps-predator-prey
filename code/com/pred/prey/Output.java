package com.pred.prey;
import java.io.*;
/**
 * Method to write output in ppm files.
 * @author Milena Put your name here if you work on this class
 * @version 6.0, October, 28th 2011
 * @since 1.0
 */

public class Output{

 
    int x, y;
    double maxValue;
   
    int [][][] cell;
    double [][] density;   
        
    public Output()	{
    	
    }


    /**
     * Constructor for the image cells. The third dimension [3] is introduced
     * to account for the contribution of red/green/blue 
     * colours to each image cell.
     * @param x Row index
     * @param y Column index
     * @param red pixel
     * @param green pixel
     * @param blue pixel
     **/

    /**
     * Method to write densities into a PPM file
     * In the final output, water will be seen as blue and land as green cells.
     * The change in density of animals with position will be represented by different shades of grey
     * (the higher the density the darker the colour).
     * @param outputName 
     * @param density 
     * @param colour optional
     **/

    
    public void printPpm(String outputName, double[][] density, int[][] neighbours) throws Exception { 	


	/**
	 * Calculate the maximum density.
	 **/
	maxValue=0;
	for(x=1; x<(density.length-1); x++)
	    {
		for(y=1; y<(density[0].length-1); y++)
		    {
			if(density[y][x]>maxValue)
			    {
				maxValue = density[y][x];
			    }
		    }
	    }
	double scale=255/maxValue;
		
	 /**
	 * Print the values in a ppm file.
	 **/
	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputName)));

	out.printf("P3");
	out.printf("\n");
	out.printf(""+(density.length-2)+" "+(density[0].length-2));
	out.printf("\n");
	out.printf("255");
	out.printf("\n");
	
	
	for(y=1; y<(density[0].length-1); y++)
	{
		for(x=1; x<(density.length-1); x++)
		{				
			/**
			 * Fill water cells with blue.
			 **/
				if(neighbours[y][x]==-1)
				{
				out.printf("%03d %03d %03d ",0,0,255);
				}
				else{
				
				out.printf("%03d %03d %03d ",(int)(density[y][x]*scale),(int)(density[y][x]*scale),(int)(density[y][x]*scale));

				}
					    
				
		}
	}
	out.close();    	
   }
    


	
    /**
     * Method to write average density with corresponding time to a file
     * @param outputName
     * @param density
     * @param time
     **/

    public void printMeanDensity(String outputName, double[][] density, double time) throws Exception
    {
    	
        double sum = 0;
        double average;
	
       	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputName,true)));
	
       	for (x=1; x<(density.length-1); x++)
       	{
       		for (y=1; y<(density[x].length-1); y++)
       		{       			
       			sum += density[y][x];
		    }
	    }
		
	 average = sum/((double)(density.length-1)*(double)(density[0].length-1));
	 
	 out.printf(time+" "+average);
	 out.printf("\n");
	 out.close();
    }
  
}	    
