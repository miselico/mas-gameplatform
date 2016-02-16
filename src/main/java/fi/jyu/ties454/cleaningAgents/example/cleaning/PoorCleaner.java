package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

public class PoorCleaner extends GameAgent {

	private static final long serialVersionUID = 1L;

	private ForwardMover mover;
	private Rotator rotator;
	private Cleaner cleaner;

	@Override
	protected void setup() {

		this.mover = this.getDevice(DefaultDevices.BasicForwardMover.class).get();
		this.rotator = this.getDevice(DefaultDevices.BasicRotator.class).get();
		this.cleaner = this.getDevice(DefaultDevices.BasicCleaner.class).get();

		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random rand = new Random();
				// no money -> use free stuff
				while (true) {
					PoorCleaner.this.mover.move();
					PoorCleaner.this.cleaner.clean();
					if (rand.nextInt(5) == 0) {
						PoorCleaner.this.rotator.rotateCW();
					}
				}
			}

		});
	}
}
