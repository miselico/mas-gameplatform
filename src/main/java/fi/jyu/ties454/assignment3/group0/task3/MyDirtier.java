package fi.jyu.ties454.assignment3.group0.task3;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.SoilingAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

/**
 * The agent extends from {@link SoilingAgent}, which is actually a normal JADE
 * agent. As an extra it has methods to obtain sensors and actuators.
 */
public class MyDirtier extends SoilingAgent {

	private static final long serialVersionUID = 1L;
	private Dirtier d;
	private Dirtier dirtExplosion;
	private ForwardMover jumper;
	private Rotator rotator;
	private Dirtier areaDirtier;
	private boolean expectArea;
	private ForwardMover f;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random r = new Random();
				expectArea = true;
				MyDirtier.this.getDevice(DefaultDevices.AreaDirtier.class);
				expectArea = false;
				if (MyDirtier.this.getDevice(DefaultDevices.JumpForwardMover.class)) {
					MyDirtier.this.jumper.move();
					MyDirtier.this.jumper.move();
					while (true) {
						MyDirtier.this.rotator.rotateCW();
						while (MyDirtier.this.jumper.move() == 5) {
							if (!MyDirtier.this.areaDirtier.isEmpty()) {
								MyDirtier.this.areaDirtier.makeMess();
							} else {
								// just normal mess
								MyDirtier.this.d.makeMess();
							}
							if (r.nextInt(5) == 0) {
								rotator.rotateCW();
							}
							if (r.nextInt(25) == 0) {
								// attempt dirt explosion. This only works if
								// there is money left.
								if (MyDirtier.this.getDevice(DefaultDevices.DirtExplosion.class)) {
									MyDirtier.this.dirtExplosion.makeMess();
								}
							}
							if (r.nextInt(10) == 0){
								MyDirtier.this.f.move();
							}
						}
					}
				}
			}
		});

	}

	@Override
	public void install(ForwardMover f, Rotator r, Dirtier c) {
		this.f = f;
		this.d = c;
		this.rotator = r;
	}

	@Override
	public void update(Dirtier f) {
		if (expectArea) {
			this.areaDirtier = f;
		} else {
			this.dirtExplosion = f;
		}
	}

	@Override
	public void update(ForwardMover f) {
		this.jumper = f;
	}

}
