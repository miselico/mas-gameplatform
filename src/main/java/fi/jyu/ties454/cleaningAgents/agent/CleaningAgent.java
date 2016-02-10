package fi.jyu.ties454.cleaningAgents.agent;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;

public abstract class CleaningAgent extends GameAgent {
	private static final long serialVersionUID = 1L;

	public abstract void install(ForwardMover f, Rotator r, Cleaner c);

}
