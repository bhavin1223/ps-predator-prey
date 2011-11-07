package com.pred.prey;
import com.pred.prey.PredPrey;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.*;
/** InputFrame is a JFrame Swing GUI to get the input data from 
 * 	the user
 */
class InputFrame extends JFrame
{
	
	int noAnimals = 2;
 	private double[][] diffCo = new double[noAnimals][noAnimals];
	private double[] diffusionRate = new double[noAnimals];
	private double step;
	private String file;
	/** nParameters is number of parameters set by user, for this task 
	 * 	permanently set to 7
	 */
	int nParameters = 7;
	/**choise2Indicator indicates which parameter is chosen to have a 
	 * range of values
	 */
	int choise2Indicator;
	/** number of values for a range for a parameter
	 */
	int n;
	/**	inputTextField is a JTextField to get user input, it is done
	 * as an array to keep the code consise. it is assumed that the 
	 * values for all the arrays go in the following order:
	 * r - hare birth rate
	 * a - puma predation rate
	 * b - puma birth rate
	 * m - puma death rate
	 * k - hare diffusion rate
	 * l - puma diffusion rate
	 * dt - time step
	 */	
	JTextField inputTextField[] = new JTextField[nParameters];
	/**	inputTextField2 is JTextField to get user input in case
	 * 	user wants to run the simulation for a range of values
	 */
	JTextField inputTextField2[] = new JTextField[nParameters];
	/**	inputLabel is JLabel to label the text fields
	 */
	JLabel inputLabel[] = new JLabel[7];
	/** inputPlane is a JPlane that holds all the information about 
	 * 	the input, buttonPlane has start button and radiobuttons
	 */
	JPanel inputPlane, input2Plane, buttonPlane;
	/** parameters holds input in double precision floating points
	 */
	double parameters[] = new double[nParameters+1];
	/** special case if a user wants to iterate the code over a range of
	 * values, parameterRange holds all the values of that parameter
	 */
	double parameterRange[];
	/** Buttongroups where choise represents buttons to chose between 
	 * preset values and range of values and choise2 represents choise 
	 * of parameter to have a range of values for
	 */
	ButtonGroup choise, choise2;
	/** Members of choise button group
	 */
	JRadioButton preSet, range;
	/** Start button which initiates computation
	 */
	JButton start;
	/** Members of cchoise2 button group
	 */
	JRadioButton choiseButton[] = new JRadioButton[nParameters];
	/** Labeling textfield that is responsible for prompting for 
	 * step size to iterate a parameter over
	 */
	JLabel stepLabel = new JLabel("range step");
	/** textfield that is responsible for prompting for 
	 * step size to iterate a parameter over
	 */
	JTextField stepField = new JTextField();
	JTextField TField = new JTextField("50");
	JLabel TLabel = new JLabel("<html> steps before <br> output <html>");
	
	 JTextField fileNameField = new JTextField("./small.dat");
	 JLabel fileNameLabel = new JLabel("Input map from: ");
	/** readValues reads values for the parameters and converts them
	 * into doubles to be put into parameters array
	 */
	
	

	public void readValues()
	{
		// This loop reads all the inputs 
		for (int i=0; i<(nParameters); i++)
		{
			parameters[i] = Double.parseDouble(inputTextField[i].getText());
			
		}
		parameters[nParameters] = Double.parseDouble(TField.getText());
		// if range is selected, additionally parameterRange is read
		// using the bounds and step set by user
		if (range.isSelected())
		{
			//lowerBound is specified in the left text field
			double lowerBound = Double.parseDouble(inputTextField[choise2Indicator].getText());
			//upperBound is specified in the right text field
			double upperBound = Double.parseDouble(inputTextField2[choise2Indicator].getText());
			double stepDouble = Double.parseDouble(stepField.getText());
			// n can lose up to 1 point but can be neglected for large n
			n = (int) ((upperBound-lowerBound)/stepDouble);
			parameterRange = new double[n];
			//initializing parameterRange
			for (int i=0; i<n; i++)
			{
				parameterRange[i] = lowerBound + i*stepDouble;
			}
		}
		
		file = fileNameField.getText();
	}
	/** creates labels for all the text field inputs accordingly
	 */
	public void label()
	{
		// As every label is unique, it has to be put manually.
		inputLabel[0] = new JLabel("Hare Birth Rate:");
		inputLabel[1] = new JLabel("Puma Predation Rate");
		inputLabel[2] = new JLabel("Puma Birth Rate");
		inputLabel[3] = new JLabel("Puma Death Rate");
		inputLabel[4] = new JLabel("Hare Diffusion Rate");
		inputLabel[5] = new JLabel("Puma Diffusion Rate");
		inputLabel[6] = new JLabel("Time Step");
	}
	/** method that hides second line of input for preSet values
	 */
	public void hideInput2()
	{
		for (int j=0; j<nParameters; j++)
		{
			inputTextField2[j].setVisible(false);
		}
	}
	/** Method to construct the first plane of input
	 */
	public void createInputPlane()
	{
		// Putting first text field and label on a separate panel
		inputPlane = new JPanel();
		label();
		setTextField();
		// Adding in order so that label is always on the left, text field
		// on the right
		for (int i=0; i<nParameters; i++)
		{
			inputPlane.add(inputLabel[i]);
			inputPlane.add(inputTextField[i]);
			
		}
	}
	/**Method to construct second plane of input
	 */
	public void createInput2Plane()
	{
		// second text field along with the choise buttons are on a 
		// separate panel so they can always be hidden easily
		input2Plane = new JPanel();
		setTextField2();
		createChoise2();
		// Adding in order so that text field is always on the left, button
		// on the right
		for (int i=0; i<nParameters; i++)
		{
			input2Plane.add(inputTextField2[i]);
			input2Plane.add(choiseButton[i]);
		}
	}
	/** Method to construct a plane with all the buttons
	 */
	public void createButtonPlane()
	{
		// buttons are on a separate panel so that they can have layout
		// different to that of input panels.
		buttonPlane = new JPanel();
		//radiobuttons preSet and Range are part of the same buttonGroup
		// choise so they cannot be pressed at the same time
		choise = new ButtonGroup();
		preSet = new JRadioButton("PreSet");
		range = new JRadioButton("Range");
		start = new JButton("Start");
		//adding radio buttons to the group
		choise.add(preSet);
		choise.add(range);
		//adding all the buttons to the plane
		buttonPlane.add(start);
		buttonPlane.add(preSet);
		buttonPlane.add(range);
		buttonPlane.add(fileNameLabel);
		buttonPlane.add(fileNameField);
		buttonPlane.add(TLabel);
		buttonPlane.add(TField);
		//step text field seemed to fit best on the button panel although
		// has more to do with input2.
		buttonPlane.add(stepLabel);
		buttonPlane.add(stepField);
		//Initially needs not to be seen only to be seen when range is pressed
		stepLabel.setVisible(false);
		stepField.setVisible(false);
		//for some reason text field takes half a screen if
		// dimensions are not specified
		fileNameField.setMaximumSize(new Dimension(230, 20));
		stepField.setMaximumSize(new Dimension(230, 20));
		TField.setMaximumSize(new Dimension(230, 20));
		// selecting presed as default
		preSet.setSelected(true);

	}
	/** Method to put default values for input
	 */
	public void setTextField()
	{
		// As the default values are put in and they are unique, 
		// putting them in manually
		inputTextField[0] = new JTextField("0.08");
		inputTextField[1] = new JTextField("-0.04");
		inputTextField[2] = new JTextField("0.02");
		inputTextField[3] = new JTextField("-0.06");
		inputTextField[4] = new JTextField("0.2");
		inputTextField[5] = new JTextField("0.2");
		inputTextField[6] = new JTextField("0.4");
		
	}
	/** Method creates text field for ranged input
	 */
	public void setTextField2()
	{
		// No default values needed so can use a loop
		for (int i=0; i<nParameters; i++)
		{ inputTextField2[i] = new JTextField(); }
	}
	/** Method creates radio buttons to choose ranged input
	 */
	public void createChoise2()
	{
		// all the choise2 buttons are part of one group so 
		// only one of them can be selected
		choise2 = new ButtonGroup();
		for (int i=0; i<nParameters; i++)
		{
			choiseButton[i] = new JRadioButton("");
			choise2.add(choiseButton[i]);
		}
		// the first button is selected, also only first input displayed
		choiseButton[0].setSelected(true);
		hideInput2();
		inputTextField2[0].setVisible(true);
	}
	/** Method is responsible for all the actions performed by all the buttons 
	 * including start button
	 */
	public void buttonActions()
	{
		
		// action for range: show second panel of input, show step input.
		range.addActionListener(new ActionListener()
			{
 
				public void actionPerformed(ActionEvent e)
				{
					input2Plane.setVisible(true);
					stepLabel.setVisible(true);
					stepField.setVisible(true);
				}
			});
		// action for preSet: hide second panel of input along with step input
		preSet.addActionListener(new ActionListener()
			{
 
				public void actionPerformed(ActionEvent e)
				{
					input2Plane.setVisible(false);
					stepLabel.setVisible(false);
					stepField.setVisible(false);
				}
			});
		// Start button: read all the inputs and start the simulation
		start.addActionListener(new ActionListener()
			{
				
				public void actionPerformed(ActionEvent e) 
				{
					try
					{
						
					// checking if all the inputs are doubles
					if (isInputAcceptable())
					{
						
						readValues();
						System.out.println("...");
						// no need to see the GUI after simulation have started
						setVisible(false);
						if (range.isSelected())
						{PredPrey.run(parameters, file, parameterRange, choise2Indicator);}
						else
						{PredPrey.run(parameters, file);}
						setVisible(true);
					}
					// if some inputs are not doubles, an error message is shown
					// allowing the user to try again
					else
					{
					final JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel, 
					"There seems to be a problem with your input, please try again",
					"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception a)
				{
					System.out.println("Something's gone wrong");
					//System.out.println(a.getMessage());
				}
				}
			});
		// The following code did not work when put in the loop so 
		// it was written explicitely for every choise2 button below
		// not the best solution in terms of lines of code but could
		// not figure out how to do it otherwise yet	
		/*
		for (z=0; z<(nParameters-1); z++)
		{
			choiseButton[z].addActionListener(new ActionListener()
			{
 
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[z].setVisible(true);					


				}
			});
		}*/
			
			
		// For all the following choise2 buttons: 
		//choise2Indicator is set so that it would be easy to track
		// which input parameter needs to be ranged.	
		choiseButton[0].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[0].setVisible(true);
					choise2Indicator = 0;
				}
			});
		choiseButton[1].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[1].setVisible(true);
					choise2Indicator = 1;
				}
			});
		choiseButton[2].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[2].setVisible(true);
					choise2Indicator = 2;
				}
			});
		choiseButton[3].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[3].setVisible(true);
					choise2Indicator = 3;
				}
			});
		choiseButton[4].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[4].setVisible(true);
					choise2Indicator = 4;
				}
			});
		choiseButton[5].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[5].setVisible(true);
					choise2Indicator = 5;
				}
			});
		choiseButton[6].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					hideInput2();
					inputTextField2[6].setVisible(true);
					choise2Indicator = 6;
				}
			});
	}
	/** Method to check if a string could be parsed into double,
	 * very useful to check if inputs are of the correct format
	 */
	public static boolean isDouble( String input )  
	{  
		
		try  
		{  
			Double.parseDouble( input );  
			return true;  
		}  
		// If throws an error when parsed into double then not a double
		catch( Exception e)  
		{  
			return false;  
		}  
}  
	/** Method returns true if all the inputs can be used in the 
	 * simulation(i.e. all doubles) and false otherwise 
	 */
	public boolean isInputAcceptable()
	{
		// set the temporary values to true, check all the possible 
		// problems, if one of them has a problem, temp is set to be false
		// So far only checks that inputs are doubles, open for more 
		// suggestions
		boolean temp = true;
		// first input panel always needs to be checked
		for (int i=0; i<nParameters; i++)
		{
			if (isDouble(inputTextField[i].getText())==false)
			{
				temp = false;
			}
		}
		// second input panel needs to be checked only if range is pressed
		// 
		if ((range.isSelected()) && 
		((isDouble(inputTextField2[choise2Indicator].getText())==false) ||
		(isDouble(stepField.getText()) == false)))
		{
			temp = false;
		}
		return temp;
	}
	/** Constructor
	 */
	InputFrame()
	{
		Container content = this.getContentPane();
		// The frame contains panels aligned horizontally
		content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));
		// creating all the panels
		createButtonPlane();
		createInputPlane();
		createInput2Plane();
		// setting button actions
		buttonActions();
		// as PreSet is the default choise, second panel is hidden
		input2Plane.setVisible(false);
		// first and second layout are descided to have grid layouts
		// so that all the elements are easily resizable
		inputPlane.setLayout(new GridLayout(nParameters, 2));
		input2Plane.setLayout(new GridLayout(nParameters, 2));
		// for buttons, grid layout is not ideal as buttons then take too much space
		// so box layout is preferred
		buttonPlane.setLayout(new BoxLayout(buttonPlane, BoxLayout.Y_AXIS));
		
	
		// adding all the planes to the frame
		content.add(inputPlane);
		content.add(input2Plane);
		content.add(buttonPlane);
		// seems to be the best default size
		this.setSize(600, 230);
		// making sure the GUI is in the center of a scrreen.
		this.setLocationRelativeTo(null);
		// before the frame is shown, displaying a short message to the
		// user, can get rid of it.
		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, 
		"This program will simulate predator prey model \n" +
		 "Please, specify the parameters for the simulation",
		"Welcome", JOptionPane.PLAIN_MESSAGE);
		setVisible(true);
		
	}
	
	
}
public class PrTest 
{
	public static void main(String args[])
	{
		new InputFrame();

	}
}
