package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

public class RichCleaner1 extends CleaningAgent {

	private static final long serialVersionUID = 1L;
	private ForwardMover f;
	private Rotator r;
	private Cleaner c;
	private Cleaner areaCleaner;
	private ForwardMover jumper;

	@Override
	protected void setup() {
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random rand = new Random();
				if (RichCleaner1.this.getDevice(DefaultDevices.AreaCleaner.class)
						&& RichCleaner1.this.getDevice(DefaultDevices.JumpForwardMover.class)) {
					while (true) {
						RichCleaner1.this.jumper.move();
						RichCleaner1.this.areaCleaner.clean();
						if (rand.nextInt(5) == 0) {
							RichCleaner1.this.r.rotateCW();
						}
					}
				} else {
					// no money -> use free stuff
					while (true) {
						RichCleaner1.this.f.move();
						RichCleaner1.this.c.clean();
						if (rand.nextInt(5) == 0) {
							RichCleaner1.this.r.rotateCW();
						}
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

	@Override
	public void update(Cleaner f) {
		this.areaCleaner = f;
	}

	@Override
	public void update(ForwardMover f) {
		this.jumper = f;
	}

}
