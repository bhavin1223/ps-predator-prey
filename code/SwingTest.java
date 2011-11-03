import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

class TestFrame extends JFrame {
	
	private int noAnimals = 2;
	
	private JTextField hareBirthRate, pumaBirthRate, pumaPredationRate,
			pumaDiffusionRate, pumaDeathRate, hareDiffusionRate, timeStep, fileName;

	private double[][] diffCo = new double[noAnimals][noAnimals];
	private double[] diffusionRate = new double[noAnimals];
	private double step; 
	private double[][] initialDiffCo = {{1.5, 1.5}, {2.0, 2.0}};
	private double[] initialDiffusionRate = {1.0, 1.0};
	private double initialTimeStep = 1.0;
	private String initialFileName = "small.dat";
	private Boolean run = false;
	private String file;

	public void read() {
		diffCo[0][0] = Double.parseDouble(hareBirthRate.getText());
		diffCo[0][1] = Double.parseDouble(pumaPredationRate.getText());
		diffCo[1][0] = Double.parseDouble(pumaBirthRate.getText());
		diffCo[1][1] = Double.parseDouble(pumaDeathRate.getText());
		diffusionRate[0] = Double.parseDouble(hareDiffusionRate.getText());
		diffusionRate[1] = Double.parseDouble(pumaDiffusionRate.getText());
		step = Double.parseDouble(timeStep.getText());
		file = fileName.getText();
	}

	TestFrame() {
		Container content = this.getContentPane();
		// content.setBackground(Color.lightGray);
		/*
		 * content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS)); JPanel
		 * pan1 = new JPanel(); pan1.setLayout(new
		 * BoxLayout(pan1,BoxLayout.Y_AXIS));
		 * 
		 * JPanel pan2 = new JPanel(); pan2.setLayout(new
		 * BoxLayout(pan2,BoxLayout.Y_AXIS));
		 * 
		 * JPanel pan3 = new JPanel(); pan3.setLayout(new
		 * BoxLayout(pan3,BoxLayout.Y_AXIS));
		 */
		content.setLayout(new GridLayout(7, 4));
		ButtonGroup choise = new ButtonGroup();
		JRadioButton preSet = new JRadioButton("PreSet");
		JRadioButton range = new JRadioButton("Range");
		JButton start = new JButton("Start");
		choise.add(preSet);
		choise.add(range);

		this.hareBirthRate = new JTextField("1.5");
		// hareBirthRate.setPreferredSize(new Dimension(50, 25));
		this.pumaBirthRate = new JTextField("1.5");
		this.pumaPredationRate = new JTextField("1.5");
		this.pumaDiffusionRate = new JTextField("1.0");
		this.pumaDeathRate = new JTextField("1.5");
		this.hareDiffusionRate = new JTextField("1.0");
		this.timeStep = new JTextField("1");
		this.fileName = new JTextField("small.dat");

		JLabel hareBirthRateL = new JLabel("Hare Birth Rate");
		JLabel pumaBirthRateL = new JLabel("Puma Birth Rate");
		JLabel pumaPredationRateL = new JLabel("Puma Predation Rate");
		JLabel pumaDiffusionRateL = new JLabel("Puma Diffusion Rate");
		JLabel pumaDeathRateL = new JLabel("Puma Death Rate");
		JLabel hareDiffusionRateL = new JLabel("Hare Diffusion Rate");
		JLabel timeStepL = new JLabel("Time Step");
		JLabel fileNameL = new JLabel("Grid File Name");

		content.add(hareBirthRateL);
		content.add(hareBirthRate);
		// content.add(new JLabel(""));
		content.add(start);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				read();
				setRun(true);
				System.out.println(getRun());
			}
		});

		content.add(pumaBirthRateL);
		content.add(pumaBirthRate);
		content.add(new JLabel(""));

		content.add(pumaPredationRateL);
		content.add(pumaPredationRate);
		content.add(new JLabel(""));

		content.add(pumaDeathRateL);
		content.add(pumaDeathRate);
		content.add(new JLabel(""));

		content.add(hareDiffusionRateL);
		content.add(hareDiffusionRate);
		content.add(range);

		content.add(pumaDiffusionRateL);
		content.add(pumaDiffusionRate);
		content.add(new JLabel(""));

		content.add(timeStepL);
		content.add(timeStep);
		content.add(preSet);
		
		content.add(fileNameL);
		content.add(fileName);

		// JLabel jlbHelloWorld = new JLabel("Hello World");
		// add(jlbHelloWorld);
		this.setSize(400, 200);
		setVisible(true);
		
		setDiffCo(initialDiffCo);
		setDiffusion(initialDiffusionRate);
		setStep(initialTimeStep);
		setFileName(initialFileName);
	}
	
	/**
	 * Parameters is an array of double precision values that hold the user inputs.
	 * 
	 * @param DiffCo	User inputs such as Hare Birth Rate, Puma Birth Rate, 
	 * Puma Predation Rate..., necessary for the computations
	 */
	public void setDiffCo(double[][] diffCo) {
		this.diffCo = diffCo;
	}
	
	/**
	 * Parameters is an array of double precision values that hold the user inputs.
	 * 
	 * @param parametersIn	User inputs such as Hare Birth Rate, Puma Birth Rate, 
	 * Puma Predation Rate..., necessary for the computations
	 */
	public double[][] getDiffCo() {
		return diffCo;
	}
	
	
	public void setDiffusion(double[] diffusionRate) {
		this.diffusionRate = diffusionRate;
	}
	
	
	public double[] getDiffusion() {
		return diffusionRate;
	}
	
	
	public void setStep(double step) {
		this.step = step;
	}
	
	
	public double getStep() {
		return step;
	}
	
	
	public void setNoAnimals(int noAnimals) {
		this.noAnimals = noAnimals;
	}
	
	public String getFileName() {
		return file;
	}
	
	public void setFileName(String file) {
		this.file = file;
	}
	
	
	public int getNoAnimals() {
		return noAnimals;
	}
	
	public void setRun(Boolean run){
		this.run = run;
	}
	
	public Boolean getRun()	{
		return this.run;
	}
	
}

public class SwingTest {
	public static void main(String args[]) {
		new TestFrame();
		System.out.println("test");
	}
}
