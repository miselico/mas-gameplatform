package fi.jyu.ties454.assignment3.group0.task3;

import java.util.Random;

import com.google.common.base.Optional;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.SoilingAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.AreaDirtier;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.DirtExplosion;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.JumpForwardMover;
import jade.core.behaviours.OneShotBehaviour;

/**
 * The agent extends from {@link SoilingAgent}, which is actually a normal JADE
 * agent. As an extra it has methods to obtain sensors and actuators.
 */
public class MyDirtier extends SoilingAgent {

	private static final long serialVersionUID = 1L;
	private Dirtier d;
	private Rotator rotator;
	private ForwardMover f;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random r = new Random();
				Optional<AreaDirtier> areaDirtier = MyDirtier.this.getDevice(DefaultDevices.AreaDirtier.class);
				Optional<JumpForwardMover> jumper = MyDirtier.this.getDevice(DefaultDevices.JumpForwardMover.class);
				if (jumper.isPresent()) {
					jumper.get().move();
					jumper.get().move();
					while (true) {
						MyDirtier.this.rotator.rotateCW();
						while (jumper.get().move() == 5) {
							if (areaDirtier.isPresent() && !areaDirtier.get().isEmpty()) {
								areaDirtier.get().makeMess();
							} else {
								// just normal mess
								MyDirtier.this.d.makeMess();
							}
							if (r.nextInt(5) == 0) {
								MyDirtier.this.rotator.rotateCW();
							}
							if (r.nextInt(25) == 0) {
								// attempt dirt explosion. This only works if
								// there is money left.
								Optional<DirtExplosion> dirtExplosion = MyDirtier.this
										.getDevice(DefaultDevices.DirtExplosion.class);
								if (dirtExplosion.isPresent()) {
									dirtExplosion.get().makeMess();
								}
							}
							if (r.nextInt(10) == 0) {
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
}