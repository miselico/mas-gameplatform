package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Random;

import com.google.common.base.Optional;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.CleaningAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.AreaCleaner;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.JumpForwardMover;
import jade.core.behaviours.OneShotBehaviour;

public class RichCleaner1 extends CleaningAgent {

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
				Optional<AreaCleaner> areaCleaner = RichCleaner1.this.getDevice(DefaultDevices.AreaCleaner.class);
				Optional<JumpForwardMover> jumper = RichCleaner1.this.getDevice(DefaultDevices.JumpForwardMover.class);
				if (areaCleaner.isPresent()	&& jumper.isPresent()) {
					while (true) {
						jumper.get().move();
						areaCleaner.get().clean();
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

}
