package fi.jyu.ties454.assignment3.infra;

import fi.jyu.ties454.assignment3.agent.GameAgent;
import jade.core.Agent;

class AgentState {
	final GameAgent agent;
	Location l;
	Orientation o;

	public AgentState(GameAgent agent, Location l, Orientation o) {
		super();
		this.agent = agent;
		this.l = l;
		this.o = o;
	}

	// private BackwardMover backward;
	// private ForwardMover forward;
	// private Cleaner cleaner;
	// private Rotator rotator;
	//
	// private
}
