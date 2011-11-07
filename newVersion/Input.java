import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Provides a basic user interface, allowing desired options to be entered and the simulation
 * to be run. The options can be reset to their defaults. Messages can be printed to the frame 
 * from elsewhere in the program to avoid the need for the terminal.
 * @author
 */
public class Input extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	// Number of options and their default values and array to hold final values
	private final int numOptions = 11;
	private String[] defaults = new String[numOptions];
	private String[] options = new String[numOptions];
	
	// Components of the interface that need to be accessed after being initialized
	private JButton defaultsButton, runButton, exitButton;
	private JTextField[] textFields = new JTextField[numOptions];
	private JTextArea textArea;
	
	/**
	 * Constructor sets up the 3 panels of the interface, adds them to the frame and
	 * displays the input window. The program then waits for the user to click one of
	 * the buttons on the interface.
	 */
	public Input() {
		
		JPanel optionsPanel, buttonsPanel;
		JScrollPane textPanel;
		
		/*
		 * Initialize variables
		 */
		
		// Defaults
		defaults[0] = "0.08";			// r
		defaults[1] = "0.04";			// a
		defaults[2] = "0.02";			// b
		defaults[3] = "0.06";			// m
		defaults[4] = "0.2";			// k
		defaults[5] = "0.2";			// l
		defaults[6] = "0.4";			// dt
		defaults[7] = "5";				// number of times to output to file
		defaults[8] = "10";				// Number of steps between outputs
		defaults[9] = "small.dat";		// Input file name
		defaults[10] = "./results/out";	// Output file name
		
		// Labels
		JLabel[] labels = new JLabel[numOptions];
		labels[0] = new JLabel("Rate of Puma Population Increase (r)");
		labels[1] = new JLabel("Predation Rate Coefficient (a)");
		labels[2] = new JLabel("Puma Birth Rate (b)");
		labels[3] = new JLabel("Puma Mortality Rate (m)");
		labels[4] = new JLabel("Hare Diffusion Rate (k)");
		labels[5] = new JLabel("Puma Diffustion Rate (l)");
		labels[6] = new JLabel("Time Step (dt)");
		labels[7] = new JLabel("Number of Outputs");
		labels[8] = new JLabel("Steps Per Output");
		labels[9] = new JLabel("Input File Name");
		labels[10] = new JLabel("Output File Name (Excluding File Type)");
		
		/*
		 * Set up the options panel
		 */
		
		optionsPanel = new JPanel();
		optionsPanel.setPreferredSize(new Dimension(250, 600));
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		for(int o = 0; o < numOptions; o++) {
			textFields[o] = new JTextField(defaults[o], 15);
			optionsPanel.add(labels[o]);
			optionsPanel.add(textFields[o]);
		}
		
		/*
		 * Set up the text panel
		 */
		
		// Format the text area
        textArea = new JTextArea("Welcome!\n\n");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textPanel = new JScrollPane(textArea);
        textPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPanel.setPreferredSize(new Dimension(600, 600));
        textPanel.setBorder(BorderFactory.createCompoundBorder(
            		BorderFactory.createCompoundBorder(
            				BorderFactory.createTitledBorder("Output"),
                            BorderFactory.createEmptyBorder(7,7,7,7)),
                    textPanel.getBorder()));
        
        /*
         * Set up the buttons panel
         */
        
        //Initialize buttons and add this as an action listener
		defaultsButton = new JButton("Defaults");
		defaultsButton.setActionCommand("defaults");
		defaultsButton.addActionListener(this);
		runButton = new JButton("Run Simulation");
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		
		// Format the button panel and add the buttons
		buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		buttonsPanel.setPreferredSize(new Dimension(700, 60));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Run"));
		buttonsPanel.add(defaultsButton);
		buttonsPanel.add(runButton);
		buttonsPanel.add(exitButton);
        
		/*
		 * Set up the frame
		 */
		
		// Format the frame and add the panels
		setTitle("Predator Prey Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(optionsPanel, BorderLayout.WEST);
		getContentPane().add(textPanel, BorderLayout.EAST);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		pack();
		
		// Position the fram in the middle of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    setLocation(screenWidth/2 - getWidth()/2, screenHeight/2 - getHeight()/2);
		setVisible(true);
	}
	
	/**
	 * Handles button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("defaults")) resetTextFields();
		else if(e.getActionCommand().equals("run")) runSimulation();
		else if(e.getActionCommand().equals("exit")) System.exit(0);
		
	}
	
	/**
	 * Sets the text fields back to their default values.
	 */
	private void resetTextFields() {

		// Loop over the text fields, changing the text to the default values
		for(int o = 0; o < numOptions; o++) {
			textFields[o].setText(defaults[o]);
		}
	}

	/**
	 * Reads the users options, and passes them to the main program that runs the simulation.
	 */
	private void runSimulation() {
		long t1, t2;
		
		// Lock the frame while the simulation is running
		lockFrame();
		
		printMessage("SIMULATION BEGINNING");
		
		t1 = System.currentTimeMillis();
		
		// Read the values in from the text fields
		for(int o = 0; o < numOptions; o++) {
			options[o] = textFields[o].getText();
		}
		
		// Create and set up an algorithm using the options and make sure they are valid
		Algorithm alg = new Algorithm(this);
		if(!alg.setOptions(options)) {
			printMessage("Invalid options.");
			printMessage("SIMULATION END\n");
			unlockFrame();
			return;
		}
		else printMessage("Options accepted.");
		
		// The simulation can begin now all options and have been validated
		printMessage("r = " + options[0] +
				"\na = " + options[1] +
				"\nb = " + options[2] +
				"\nm = " + options[3] +
				"\nk = " + options[4] +
				"\nl = " + options[5] +
				"\ndt = " + options[6] +
				"\nNumber of outputs = " + options[7] +
				"\nSteps per output = " + options[8] +
				"\nTotal steps = " + Integer.toString(alg.getTotalSteps()) +
				"\nInput file = " + options[9]);
		
		// Create the land/water map and run the algorithm
		if(!alg.generateMap()) {
			printMessage("Unable to read from input file specified.");
			printMessage("SIMULATION END\n");
			unlockFrame();
			return;
		}
		printMessage("Land/water mask file read.");
		alg.runAlg();
		
		// Print the total time taken
		printMessage("All steps completed.");
		t2 = System.currentTimeMillis();
		printMessage("Computation time = " + (t2-t1) + " ms.");
		printMessage("SIMULATION END\n");
		unlockFrame();
	}
	
	/**
	 * Prints a text message to the output area.
	 * @param msg Message to print
	 */
	public void printMessage(String msg) {
		textArea.append(msg + "\n");
	}

	/**
	 * Locks the buttons. Used when simulation is running.
	 */
	private void lockFrame() {
		defaultsButton.setEnabled(false);
		runButton.setEnabled(false);
		exitButton.setEnabled(false);
	}
	
	private void unlockFrame() {
		defaultsButton.setEnabled(true);
		runButton.setEnabled(true);
		exitButton.setEnabled(true);
	}
	
	/**
	 * Main method creates an instance of the input window. Once constructed, it waits for
	 * the users input before continuing the program.
	 * @param args
	 */
	public static void main(String[] args) {
		Input a = new Input();
	}

}
