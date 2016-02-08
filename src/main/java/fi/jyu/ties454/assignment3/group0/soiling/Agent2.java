package fi.jyu.ties454.assignment3.group0.soiling;

import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;

public class Agent2 extends SoilingAgent {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		System.out.println(getClass().getName() + "Lazy all day long");
	}

	@Override
	public void install(ForwardMover f, Rotator r, Dirtier c) {
		// TODO Auto-generated method stub
		
	}
}
