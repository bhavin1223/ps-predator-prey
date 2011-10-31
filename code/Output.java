import java.io.*;
/**
 * Method to write output in ppm files.
 * @author Milena Put your name here if you work on this class
 * @version 6.0, October, 28th 2011
 * @since 1.0
 */

public class Output{

 
    int x, y;
    int red, blue, green;  
    double maxValue = 0;
   
    double [][][] cell;
    double [][] density;   

    int sum = 0;
    int average;
    
    /**
     *Constructor for density
     * @param d density
    
     */
   
    public void setDensity(int d)
    {
	density[x][y] = d;
    }
    public double[][] getDensity()
    {
	return density;
    }

    
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

    public void setImageCell(int x, int y, int red, int green, int blue)
    { 	
	cell[x][y][0] = red;
	cell[x][y][1] = green;
	cell[x][y][2] = blue;
    }  
    public double[][][] getImageCell()
    {
	return cell;
    }


    /**
     * Method to write densities into a PPM file
     * In the final output, water will be seen as blue and land as green cells.
     * (negative input values for water cells required). 
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
	for(x=0; x<density.length; x++)
	    {
		for(y=0; y<density[0].length; y++)
		    {
			if(density[x][y]>maxValue)
			    {
				maxValue = density[x][y];
			    }
		    }
	    }
	
	cell = new double[density.length][density[0].length][3];

	

	/**
	 * Fill the image cells with appropriate colours.
	 **/
	for(x=0; x<density.length; x++)
	{
		for(y=0; y<density[0].length; y++)
		{
			/**
			 * Fill water cells with blue.
			 **/
			if(density[x][y]<=-1)
			{
				cell[x][y][0] = 0;
				cell[x][y][1] = 0;
				cell[x][y][2] = maxValue;
			}
		    
			else
			{
				/**
				 * Choosing this option will result in green land cells with density variations
				 * being represented as different shades of grey.
				 **/	
				// This would look not bad, I think, but there are other options below.
				if (colour == "black&white")
				{
					cell[x][y][0] = density[x][y];
					cell[x][y][1] = density[x][y];
					cell[x][y][2] = density[x][y];
				}

				/**
				 * Choosing this one will basically result in white land cells.
				 **/
				if (colour == "black&green")
				{
					cell[x][y][0] = 0;
					cell[x][y][1] = density[x][y];
					cell[x][y][2] = 0;
				}

				/**
				 * This option will show land in white and density variations as different shades of green.
				 **/
				if (colour == "green&white")
				{
					cell[x][y][0] = density[x][y];
					cell[x][y][1] = maxValue;
					cell[x][y][2] = density[x][y];
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
	out.printf(""+density.length+" "+density[0].length);
	out.printf("\n");
	out.printf(""+maxValue);
	out.printf("\n");

	for(x=0; x<density.length; x++)
	{
		for(y=0; y<density[0].length; y++)
		{				
			out.printf("%s", cell[x][y][0]+" "+cell[x][y][1]+" "+cell[x][y][2]+"  ");	       		    
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
	
       	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputName,true)));
	
	for (x=0; x<density.length; x++)
	    {
		for (y=0; y<density[0].length; y++)
		    {
			 sum += density[x][y];
		    }
	    }
	
	 average = sum/(density.length*density[0].length);
	 
	 out.printf("%s", "Mean density after "+time+" seconds is "+average);
       	 out.printf("\n");
       	 out.close();
    }
  
}	    
	


    

    