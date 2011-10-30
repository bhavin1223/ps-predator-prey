import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

class TestFrame extends JFrame {
	private JTextField hareBirthRate, pumaBirthRate, pumaPredationRate,
			pumaDiffusionRate, pumaDeathRate, hareDiffusionRate, timeStep;

	private double parameters[] = new double[7];
	private double[] initialValues = {3.0, 7.0, 7.0, 7.0, 7.0, 3.0, 3.0};

	public void read() {
		parameters[0] = Double.parseDouble(hareBirthRate.getText());
		parameters[1] = Double.parseDouble(pumaBirthRate.getText());
		parameters[2] = Double.parseDouble(pumaPredationRate.getText());
		parameters[3] = Double.parseDouble(pumaDiffusionRate.getText());
		parameters[4] = Double.parseDouble(pumaDeathRate.getText());
		parameters[5] = Double.parseDouble(hareDiffusionRate.getText());
		parameters[6] = Double.parseDouble(timeStep.getText());
	}

	TestFrame() {
		double inputs[] = new double[7];
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

		this.hareBirthRate = new JTextField("3");
		// hareBirthRate.setPreferredSize(new Dimension(50, 25));
		this.pumaBirthRate = new JTextField("7");
		this.pumaPredationRate = new JTextField("7");
		this.pumaDiffusionRate = new JTextField("7");
		this.pumaDeathRate = new JTextField("7");
		this.hareDiffusionRate = new JTextField("3");
		this.timeStep = new JTextField("3");

		JLabel hareBirthRateL = new JLabel("Hare Birth Rate");
		JLabel pumaBirthRateL = new JLabel("Puma Birth Rate");
		JLabel pumaPredationRateL = new JLabel("Puma Predation Rate");
		JLabel pumaDiffusionRateL = new JLabel("Puma Diffusion Rate");
		JLabel pumaDeathRateL = new JLabel("Puma Death Rate");
		JLabel hareDiffusionRateL = new JLabel("Hare Diffusion Rate");
		JLabel timeStepL = new JLabel("Time Step");

		content.add(hareBirthRateL);
		content.add(hareBirthRate);
		// content.add(new JLabel(""));
		content.add(start);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				read();
			}
		});

		content.add(pumaBirthRateL);
		content.add(pumaBirthRate);
		content.add(new JLabel(""));

		content.add(pumaPredationRateL);
		content.add(pumaPredationRate);
		content.add(new JLabel(""));

		content.add(pumaDiffusionRateL);
		content.add(pumaDiffusionRate);
		content.add(new JLabel(""));

		content.add(pumaDeathRateL);
		content.add(pumaDeathRate);
		content.add(new JLabel(""));

		content.add(hareDiffusionRateL);
		content.add(hareDiffusionRate);
		content.add(range);

		content.add(timeStepL);
		content.add(timeStep);
		content.add(preSet);

		// JLabel jlbHelloWorld = new JLabel("Hello World");
		// add(jlbHelloWorld);
		this.setSize(400, 200);
		setVisible(true);
		
		setParameters(initialValues);
	}
	
	/**
	 * Parameters is an array of double precision values that hold the user inputs.
	 * 
	 * @param parametersIn	User inputs such as Hare Birth Rate, Puma Birth Rate, 
	 * Puma Predation Rate..., necessary for the computations
	 */
	public void setParameters(double[] parametersIn) {
		parameters = parametersIn;
	}
	
	/**
	 * Parameters is an array of double precision values that hold the user inputs.
	 * 
	 * @param parametersIn	User inputs such as Hare Birth Rate, Puma Birth Rate, 
	 * Puma Predation Rate..., necessary for the computations
	 */
	public double[] getParameters() {
		return parameters;
	}
}

public class SwingTest {
	public static void main(String args[]) {
		new TestFrame();
		System.out.println("test");
	}
}
