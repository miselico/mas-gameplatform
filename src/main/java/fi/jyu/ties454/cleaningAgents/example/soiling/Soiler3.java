package fi.jyu.ties454.cleaningAgents.example.soiling;


import java.util.Optional;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.SoilingAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.DirtExplosion;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.JumpForwardMover;
import jade.core.behaviours.OneShotBehaviour;

public class Soiler3 extends SoilingAgent {

	private static final long serialVersionUID = 1L;
	private Dirtier d;
	private Rotator rotator;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Optional<DirtExplosion> dirtExplosion = Soiler3.this.getDevice(DefaultDevices.DirtExplosion.class);
				if (dirtExplosion.isPresent()) {
					dirtExplosion.get().makeMess();
				}
				Optional<JumpForwardMover> jumper = Soiler3.this.getDevice(DefaultDevices.JumpForwardMover.class);
				if (jumper.isPresent()) {
					jumper.get().move();
					jumper.get().move();
					while (true) {
						Soiler3.this.rotator.rotateCW();
						while (jumper.get().move() == 5) {
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

}
