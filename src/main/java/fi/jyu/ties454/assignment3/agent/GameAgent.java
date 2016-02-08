package fi.jyu.ties454.assignment3.agent;

import fi.jyu.ties454.assignment3.actuators.BackwardMover;
import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.sensors.DirtSensor;
import fi.jyu.ties454.assignment3.sensors.WallSensor;
import jade.core.Agent;

public class GameAgent extends Agent {
	
	private static final long serialVersionUID = 1L;

	public void update(ForwardMover f) {
	};

	public void update(BackwardMover f) {
	};

	public void update(Dirtier f) {
	};

	public void update(Rotator f) {
	};

	public void update(DirtSensor f) {
	};

	public void update(WallSensor f) {
	};
	
	public void update(Cleaner f) {
	};
	
}
