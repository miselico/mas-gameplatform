package fi.jyu.ties454.cleaningAgents.agent;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;

public abstract class SoilingAgent extends GameAgent {
	private static final long serialVersionUID = 1L;

	public abstract void install(ForwardMover forward, Rotator rotator, Dirtier dirtier);

}
