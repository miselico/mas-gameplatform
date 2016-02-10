package fi.jyu.ties454.cleaningAgents.example.soiling;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.SoilingAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

public class Soiler3 extends SoilingAgent {

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
				if (Soiler3.this.getDevice(DefaultDevices.DirtExplosion.class)) {
					Soiler3.this.dirtExplosion.makeMess();
				}
				if (Soiler3.this.getDevice(DefaultDevices.JumpForwardMover.class)) {
					Soiler3.this.jumper.move();
					Soiler3.this.jumper.move();
					while (true) {
						Soiler3.this.rotator.rotateCW();
						while (Soiler3.this.jumper.move() == 5) {
							Soiler3.this.d.makeMess();
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
