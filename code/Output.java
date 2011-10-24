import java.io.*;
/**
 * Method to write output in ppm files.
 * @author Milena Put your name here if you work on this class
 * @version 4.0, October, 24th 2011
 * @since 1.0
 */

public class Output{

 
    int x, y;
    int red, blue, green;  
    int maxValue = 0;
   
    int [][][] cell;
    int [][] density;   

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
    public int[][] getDensity()
    {
	return density;
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
    public int[][][] getImageCell()
    {
	return cell;
    }


    /**
     * Method to write densities into a PPM file
     * In the final output, variations in density with position will be seen as 
     * different shades of red/green/blue 
     * (grid cells with zero density will appear white)
     * Water cells set to black (need the density in water cells to be negative)
     * @param outputName 
     * @param density 
     * 
     **/

    
    public void printPpm(String outputName, int[][] density, String colour) throws Exception { 	

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

		// TODO - check on the limitting values for maxValue;
	
        cell = new int[density.length][density[0].length][3];
	

	for(x=0; x<density.length; x++)
	    {
		for(y=0; y<density[0].length; y++)
		    {

			
			if(density[x][y]<=-1)
			    {
				cell[x][y][0] = maxValue;
				cell[x][y][1] = maxValue;
				cell[x][y][2] = maxValue;
			    }
		    
			else
			    {
				if(colour=="red")
				    {
					cell[x][y][0] = density[x][y];
					cell[x][y][1] = 0;
					cell[x][y][2] = 0;
				    }
				if(colour=="green")
				    {
					cell[x][y][0] = 0;
					cell[x][y][1] = density[x][y];
					cell[x][y][2] = 0;
				    }
				if(colour=="blue")
				    {
					cell[x][y][0] = 0;
					cell[x][y][1] = 0;
					cell[x][y][2] = density[x][y];
				    }
			    }

		    }
	    }		

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
			out.printf("%s", cell[x][y][0]+" "+cell[x][y][1]+" "+cell[x][y][2]+"  ");	       		    }
		out.printf("\n");
	    }
	out.close();    	
    }
    
	
	// TO DO - sort out the spaces





    public void printMeanDensity(String outputName, int[][] density, double time) throws Exception
    {
	
	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputName)));
	
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
    // TO DO - change so it doesn't overwrite itself! 
	

}	    
	


    

    