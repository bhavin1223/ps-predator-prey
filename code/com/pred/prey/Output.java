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
    double maxValue = 0;
   
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

    
    public void printPpm(String outputName, double[][] density, String colour) throws Exception { 	


	/**
	 * Calculate the maximum density.
	 **/
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
	
	cell = new int [density[0].length][density.length][3];

	

	/**
	 * Fill the image cells with appropriate colours.
	 **/
	for(x=1; x<(density.length-1); x++)
	{
		for(y=1; y<(density[0].length-1); y++)
		{
			/**
			 * Fill water cells with blue.
			 **/
			if(density[y][x]==0)
			{
				cell[y][x][0] = (int) 000;
				cell[y][x][1] = (int) 000;
				cell[y][x][2] = (int) (maxValue*scale);
			}
		    
			else
			{
				/**
				 * Choosing this option will result in green land cells with density variations
				 * being represented as different shades of grey.
				 **/	
				// This would look not bad, I think, but there are other options below.
				if (colour == "black&green")
				{
					cell[y][x][0] = (int) (0);
					cell[y][x][1] = (int) (density[y][x]*scale);
					cell[y][x][2] = (int) (0);
				}

				/**
				 * Choosing this one will basically result in white land cells.
				 **/
				
				if (colour == "black&white")
				{
					cell[y][x][0] = (int) (density[y][x]*scale);
					cell[y][x][1] = (int) (density[y][x]*scale);
					cell[y][x][2] = (int) (density[y][x]*scale);
				}
				

				/**
				 * This option will show land in white and density variations as different shades of green.
				 **/
				if (colour == "green&white")
				{
					cell[y][x][0] = (int) (density[y][x]*scale);
					cell[y][x][1] = (int) (maxValue*scale);
					cell[y][x][2] = (int) (density[y][x]*scale);
				}
				// Anyway, we can always change the colours.
			}
		}
	}		

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
			out.printf("%03d %03d %03d",cell[y][x][0],cell[y][x][1],cell[y][x][2]);
			if(x<=(density.length-2)) {
			out.printf(" ");
			}	       		    
		}
		out.printf("\n");
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
