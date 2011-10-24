import java.io.*;
/**
 * Method to write output in ppm files.
 * @author Milena Put your name here if you work on this class
 * @version 2.0, October, 24th 2011
 * @since 1.0
 */

public class Output{

 
    int x, y;
    int maxValue = 0;
    int red, blue, green;  
    int xMax, yMax, height, width;

    int [][][] cell;
    int [][] density;   

   
    
    /**
     *Constructors for objects
     * @param d density
     * @param x Row index
     * @param y Column index
     * @param red pixel
     * @param green pixel
     * @param blue pixel
     */

   
      
    public void setDensity(int d)
    {
	density[x][y] = d;
    }
    public int[][] getDensity()
    {
	return density;
    }

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
     * Method to write densities into a 'red' PPM file. 
     * In the final output, variations in density with position will be seen as 
     * different shades of red (grid cells with zero density will appear white).
     * Water cells set to black (need the density in water cells to be negative).
     * @param outputName 
     * @param density 
     * 
     **/

    
    public void printPpmRed(String outputName, int[][] density) throws Exception { 	

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
				cell[x][y][0] = density[x][y];
				cell[x][y][1] = 0;
				cell[x][y][2] = 0;
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
    
	
	// TODO - sort out the spaces




 /**
     * Method to write densities into a 'green' PPM file. 
     * Essentially same as for 'red' but with different pixel colours being picked up.
     * In the final output, variations in density with position will be seen as 
     * different shades of green (again grid cells with zero density will appear white).
     * Water cells set to black (need the density in water cells to be negative).
     * @param outputName 
     * @param density 
     * 
     **/


 public void printPpmGreen(String outputName, int[][] density) throws Exception { 	

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
				cell[x][y][0] = 0;
				cell[x][y][1] = density[x][y];
				cell[x][y][2] = 0;
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
    

}	    
	


    

    