package fi.jyu.ties454.cleaningAgents.agent;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;

public abstract class CleaningAgent extends GameAgent {
	private static final long serialVersionUID = 1L;

	/**
	 * Method called before the {@link CleaningAgent} is started. The agent has
	 * to accept (and store) the parts to use them later. By default the system
	 * will provide only very basic components.
	 *
	 * @param f
	 *            A ForwardMover
	 * @param r
	 *            A Rotator
	 * @param c
	 *            A Cleaner
	 */
	public abstract void install(ForwardMover f, Rotator r, Cleaner c);

}
