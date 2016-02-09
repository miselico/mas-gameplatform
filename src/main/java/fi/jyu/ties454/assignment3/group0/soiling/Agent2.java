package fi.jyu.ties454.assignment3.group0.soiling;

import fi.jyu.ties454.assignment3.actuators.BackwardMover;
import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;
import fi.jyu.ties454.assignment3.infra.DefaultDevices;

public class Agent2 extends SoilingAgent {

	private static final long serialVersionUID = 1L;
	private Dirtier d;
	private ForwardMover f;
	private BackwardMover b;

	@Override
	protected void setup() {
		this.getDevice(DefaultDevices.BasicBackwardMover.class);
		while (this.b.move() > 0) {
			this.d.makeMess();
		}
		while (this.f.move() > 0) {
			this.d.makeMess();
		}

	}

	@Override
	public void install(ForwardMover f, Rotator r, Dirtier c) {
		this.d = c;
		this.f = f;
	}

	@Override
	public void update(BackwardMover f) {
		this.b = f;
	}
}
