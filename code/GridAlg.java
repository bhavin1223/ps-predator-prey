import java.util.Random;

/**
 * Runs the algorithm corresponding to the user's input with the user defined settings.
 * Also performs measurements on the efficiency of the chosen algorithm.
 * 
 * @author Put your name here if you work on this class
 * @version 1.2, October, 30th 2011
 * @since 1.0
 */
public class GridAlg {        
        /**
         * Array containing the density distribution of all the animals
         */
        private Animal[] animals;
        
        /**
         * Array containing the neighbours each grid point should check for density updates based on land and water.
         */
        private int[][] neighbours;
        
        /**
         * Update frequency
         */
        private double dt;
        
        /**
         * Random number generator
         */
        private Random random;
        
    	/**
    	 * Class constructor - Version taking an array of animals as argument
    	 * and the Neighbours Array from the input grid.
    	 *  
    	 * @param Array containing the animal objects.
    	 */
    	public GridAlg(int[][] neighbours, Animal[] animals) {
    		setNeighbours(neighbours);
            setAnimals(animals);
            random = new Random(2011);
            initaliseDensities();
    	}
                
        /**
         * Sets the array of animals to an input density array
         * @param
         */
        public void setAnimals(Animal[] animals) {
                this.animals = animals;
        }
        
        /**
         * Returns the array of animals
         * @return
         */
        public Animal[] getAnimals(){
                return this.animals;            
        }
        
        public double getStep(){
            return this.dt;            
        }
        
        public void setStep(double step){
            this.dt = step;            
        }	        
                              
        /**
         * Returns the number of land neighbours the grid point i, j has.
         * @param i Row index
         * @param j Column index
         * @return neighbours Number of land neighbours point i,j has.
         */
        public int getNeighbours(int i,int j) { 
                return neighbours[i][j];                
        }
        
        public int[][] getNeighbours() { 
        	return neighbours;                
        }

    	/**
    	 * Set the current object neighbours array to the array in InOut.java. 
    	 * The number of neighbours for each land element is directly accessible.
    	 */
    	public void setNeighbours(int[][] neighbours) {
    		this.neighbours = neighbours;
    	}
        
                
        /**
         * Initialises the densities of all the animals to a pseudo-normally distributed number.
         * Only the land squares are filled with animals.
         */
        public void initaliseDensities() {
                
                double randomValue;
                
                for(int i=0;i<getAnimals().length;i++) {
                	
                	getAnimals()[i].initiateDensities(getNeighbours().length,getNeighbours()[0].length);
                	
                        for(int j=0;j<getNeighbours().length;j++) {
                                for(int k=0;k<getNeighbours().length;k++) {
                        
                                        if(getNeighbours(j,k) == -1) {
                                                randomValue = 0;
                                        }
                                        else {
                                                randomValue = distributedRandom(0,1);
                                        }                                        
                                        getAnimals()[i].setDensity(j, k, randomValue);
                                }
                        }                       
                }                       
        }
        
        /**
         * Method for creating random densities which will fill the density array. 
         * @param dMin Bottom of the range of possible numbers
         * @param dMax Top of the range of numbers
         * @return
         */
        public double distributedRandom(double dMin, double dMax) {
        	
        	double ranNum = 0;
                
        	for(int i=0;i<1;i++){
        		ranNum += random.nextDouble()*(dMax-dMin);
        	}
                
        	ranNum = ranNum/1;
        	ranNum += dMin;
                
        	return ranNum;
        }
        
        
        /**
         * Updates the grid and animals in parallel.
         * runs sequentially through the cells before replacing at end.
         */
        public void syncUpdate() {
                
                // Loop over all of the animals
                for(int i=0;i<animals.length;i++) {
                        
                        // Loop over all of the cells, calculating the next densities
                        for(int j=0; j<getNeighbours().length;j++) {
                                for(int k=0; k<getNeighbours()[0].length;k++) {
                                        if(getNeighbours(j,k)!=-1) {
                                        	animals[i].calcNextDensity(j, k, dt, animals, neighbours[j][k]);
                                        	if(animals[i].getNextDensities()[j][k]!=0){
                                        		System.out.println(animals[i].getNextDensities()[j][k]);
                                        	}
                                        }
                                }
                        }                       
                }
                
                // Apply change at end of all calculations
                for(int i=0;i<animals.length;i++) {
                	animals[i].applyTimeStep();             
                }
                
        }
        
        /*
         * Not sure why we need two different methods here. The one below is best? 
         * Two different methods to see how the algorithm behaves under each update order.
         * The one above is possibly more correct as animals are eaten, reproduce and die in the same step.
         */
        
        
        /**
         * Updates the grid and in parallel.
         * Runs sequentially through the cells for one animal then replaces
         * Goes through all animals in turn.
         */
        public void gridSyncUpdate() {
                
                // Loop over all of the animals
                for(int i=0;i<animals.length;i++) {
                        
                        // Loop over all of the cells, calculating the next densities
                        for(int j=0; j<getNeighbours().length;j++) {
                                for(int k=0; k<getNeighbours()[j].length;k++) {            
                                        if(getNeighbours(j,k)!=-1) {             
                                                animals[i].calcNextDensity(j, k, dt, animals,neighbours[j][k]); 
                                        }
                                }
                        }
                        
                        // Apply densities update while looping through animals
                        animals[i].applyTimeStep();      
                        
                }                                 
        }        
}
