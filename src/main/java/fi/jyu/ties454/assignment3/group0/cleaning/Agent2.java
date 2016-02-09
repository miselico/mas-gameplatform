package fi.jyu.ties454.assignment3.group0.cleaning;

import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.CleaningAgent;

public class Agent2 extends CleaningAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		System.out.println(this.getClass().getName() + "Lazy all day long");
	}

	@Override
	public void install(ForwardMover f, Rotator r, Cleaner c) {
		// TODO Auto-generated method stub

	}
}
