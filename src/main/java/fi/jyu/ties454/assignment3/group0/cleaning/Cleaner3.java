package fi.jyu.ties454.assignment3.group0.cleaning;

import java.util.Random;

import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import jade.core.behaviours.OneShotBehaviour;

public class Cleaner3 extends CleaningAgent {

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
					Cleaner3.this.f.move();
					Cleaner3.this.c.clean();
					if (rand.nextInt(5) == 0) {
						Cleaner3.this.r.rotateCW();
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
