package fi.jyu.ties454.assignment3.infra;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import fi.jyu.ties454.assignment3.infra.Floor.FloorUpdateListener;

public class GUI extends JFrame implements FloorUpdateListener {

	private final JTextArea output;

	public GUI() {
		output = new JTextArea(50, 50);
		output.setEditable(false);
		this.add(output);
		this.pack();
	}

	@Override
	public void update(String newState) {
		this.output.setText(newState);
	}

}
