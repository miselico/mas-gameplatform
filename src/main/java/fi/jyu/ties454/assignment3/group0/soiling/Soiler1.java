package fi.jyu.ties454.assignment3.group0.soiling;

import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;
import fi.jyu.ties454.assignment3.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

public class Soiler1 extends SoilingAgent {

	private static final long serialVersionUID = 1L;
	private Dirtier d;
	private Dirtier dirtExplosion;
	private ForwardMover jumper;
	private Rotator rotator;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				if (Soiler1.this.getDevice(DefaultDevices.DirtExplosion.class)) {
					Soiler1.this.dirtExplosion.makeMess();
				}
				if (Soiler1.this.getDevice(DefaultDevices.JumpForwardMover.class)) {
					Soiler1.this.jumper.move();
					Soiler1.this.jumper.move();
					while (true) {
						Soiler1.this.rotator.rotateCW();
						while (Soiler1.this.jumper.move() == 5) {
							Soiler1.this.d.makeMess();
						}
					}
				}
			}
		});

	}

	@Override
	public void install(ForwardMover f, Rotator r, Dirtier c) {
		this.d = c;
		this.rotator = r;
	}

	@Override
	public void update(Dirtier f) {
		this.dirtExplosion = f;
	}

	@Override
	public void update(ForwardMover f) {
		this.jumper = f;
	}
}
