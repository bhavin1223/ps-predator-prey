
/**
 * Method to write output in a ppm file.
 * @author Milena Put your name here if you work on this class
 * @version 1.0, October, 20th 2011
 * @since 1.0
 */

public class Output {

    int xMax, yMax, maxVal;
    int [][][] cell;
    
    // TODO - get values for xMax, yMax, maxVal ?
    
    /**
     *Constructor sets an image cell 
     * @param x Row index
     * @param y Column index
     * @param red
     * @param green
     * @param blue
     */

    public void setImageCell(int x, int y, int red, int green, int blue)
    { 
	cell[x][y][0] = red;
	cell[x][y][1] = green;
	cell[x][y][2] = blue;
    }


    /**
     * Writes lines of output into a ppm.
     * @param outputName Name of the output file
     */

    public void writePpm(outputName)
    {

	PrintWriter out = new PrintWriter(new FileWriter(outputName));

	out.println("P3");
	out.println(xMax + " " + yMax);

	// TODO - set maxVal if greater than 255 ? 

	out.println(maxVal);

	for(int y=0; y<yMax; y++)
	    {
		for(int x=0; x<xMax; x++)
		  {
		      out.println(cell[x][y][0]+" "+cell[x][y][1]+" " +cell[x][y][2]);   		        }
	    }	
		out.close();
    }

}

	

	

    




	    
	


    }

    