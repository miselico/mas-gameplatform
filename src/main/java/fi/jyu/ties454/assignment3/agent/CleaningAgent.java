package fi.jyu.ties454.assignment3.agent;

import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;

public abstract class CleaningAgent extends GameAgent {
	private static final long serialVersionUID = 1L;

	public abstract void install(ForwardMover f, Rotator r, Cleaner c);

}
