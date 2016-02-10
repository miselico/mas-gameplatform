package fi.jyu.ties454.assignment3.group0.task3;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

/**
 * The agent extends from CleaningAgent, which is actually a normal JADE agent.
 * As an extra it has methods to obtain sensors and actuators.
 */
public class MyCleaner extends CleaningAgent {

	private static final long serialVersionUID = 1L;
	
	private ForwardMover mover;
	private Rotator rotator;
	private Cleaner cleaner;

	private Rotator fastRotator;

	@Override
	protected void setup() {
		//it is safe to obtain parts in setup(), but using them must be done in behaviors!
		//getting the device is don using the getDevice call.
		//when this call returns true, the update method of the agent has been called
		if (this.getDevice(DefaultDevices.JackieChanRotator.class)){
			System.out.println("Got the moves");
		}
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				// TODO implement using whatever toys you got, like the fastRotator
			}
		});
	}

	@Override
	public void install(ForwardMover f, Rotator r, Cleaner c) {
		this.mover = f;
		this.rotator = r;
		this.cleaner = c;

	}

	@Override
	public void update(Rotator f) {
		System.out.println("Received a rotator");
		this.fastRotator = f;
	}
	
}
