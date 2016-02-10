package fi.jyu.ties454.assignment3.group0.cleaning;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.FloorState;
import fi.jyu.ties454.cleaningAgents.sensors.DirtSensor;
import jade.core.behaviours.OneShotBehaviour;

public class Cleaner2 extends CleaningAgent {

	private static final long serialVersionUID = 1L;

	private ForwardMover f;
	private Rotator r;
	private Cleaner c;

	private DirtSensor ds;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random rand = new Random();
				// no money -> use cheap stuff
				Cleaner2.this.getDevice(DefaultDevices.BasicDirtSensor.class);
				while (true) {
					if (Cleaner2.this.f.move() > 0) {
						// if there is a sensor, use it
						if ((Cleaner2.this.ds == null) || (Cleaner2.this.ds.inspect() == FloorState.DIRTY)) {
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
	public void update(DirtSensor f) {
		this.ds = f;
	}

	@Override
	public void install(ForwardMover f, Rotator r, Cleaner c) {
		this.f = f;
		this.r = r;
		this.c = c;
	}
}
