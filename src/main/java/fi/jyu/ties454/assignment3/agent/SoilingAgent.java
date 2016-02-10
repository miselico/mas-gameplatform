package fi.jyu.ties454.assignment3.agent;

import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;

public abstract class SoilingAgent extends GameAgent {
	private static final long serialVersionUID = 1L;

	public abstract void install(ForwardMover forward, Rotator rotator, Dirtier dirtier);

}
