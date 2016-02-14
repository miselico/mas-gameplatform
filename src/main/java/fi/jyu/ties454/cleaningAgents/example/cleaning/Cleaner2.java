package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Optional;
import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.BasicDirtSensor;
import fi.jyu.ties454.cleaningAgents.infra.FloorState;
import jade.core.behaviours.OneShotBehaviour;

public class Cleaner2 extends CleaningAgent {

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
				// no money -> use cheap stuff
				Optional<BasicDirtSensor> ds = Cleaner2.this.getDevice(DefaultDevices.BasicDirtSensor.class);
				while (true) {
					if (Cleaner2.this.f.move() > 0) {
						// if there is a sensor, use it
						if ((!ds.isPresent()) || ds.get().inspect() == FloorState.DIRTY) {
							Cleaner2.this.c.clean();
						}
					}
					int prob = rand.nextInt(10);
					if (prob == 0) {
						Cleaner2.this.r.rotateCW();
					} else if (prob == 1) {
						Cleaner2.this.r.rotateCCW();
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
