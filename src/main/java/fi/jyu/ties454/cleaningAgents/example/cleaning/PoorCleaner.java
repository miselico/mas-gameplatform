package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import jade.core.behaviours.OneShotBehaviour;

public class PoorCleaner extends CleaningAgent {

	private static final long serialVersionUID = 1L;

	private ForwardMover f;
	private Rotator r;
	private Cleaner c;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random rand = new Random();
				// no money -> use free stuff
				while (true) {
					PoorCleaner.this.f.move();
					PoorCleaner.this.c.clean();
					if (rand.nextInt(5) == 0) {
						PoorCleaner.this.r.rotateCW();
					}
				}
			}

		});
	}

	@Override
	public void install(ForwardMover f, Rotator r, Cleaner c) {
		this.f = f;
		this.r = r;
		this.c = c;
	}
}
