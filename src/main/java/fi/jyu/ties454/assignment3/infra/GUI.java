package fi.jyu.ties454.assignment3.infra;

import java.awt.Font;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import fi.jyu.ties454.assignment3.infra.Manager.Listener;

public class GUI extends JFrame implements Listener {

	private static final long serialVersionUID = 1L;

	private final JTextArea output;

	public GUI() {
		this.output = new JTextArea(30, 50);
		this.output.setFont(new Font("monospaced", Font.PLAIN, 20));

		this.output.setEditable(false);
		this.add(new JScrollPane(this.output));
		this.pack();
	}

	@Override
	public void floorUpdate(int cleanersBudget, int soilersBudget, Map<String, AgentState> cleaners,
			Map<String, AgentState> soilers, Floor map) {
		String text = map.toString();
		// try {
		// SwingUtilities.invokeAndWait(new Runnable() {
		//
		// @Override
		// public void run() {
		// output.setText(text);
		// }
		// });
		// } catch (InvocationTargetException | InterruptedException e) {
		// throw new Error(e);
		// }
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI.this.output.setText(text);
			}
		});
	}

	@Override
	public void processUpdates(int cleanersBudget, int soilersBudget, Map<String, AgentState> cleaners,
			Map<String, AgentState> soilers, Floor map) {
		this.floorUpdate(cleanersBudget, soilersBudget, cleaners, soilers, map);
	}

	@Override
	public void agentStateUpdate(int cleanersBudget, int soilersBudget, Map<String, AgentState> cleaners,
			Map<String, AgentState> soilers, Floor map) {
		System.out.println(soilers);
	}
}
