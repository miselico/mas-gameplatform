package fi.jyu.ties454.assignment3.group0.cleaning;

import fi.jyu.ties454.assignment3.actuators.BackwardMover;
import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import fi.jyu.ties454.assignment3.infra.DefaultDevices;

public class Agent1 extends CleaningAgent {

	private static final long serialVersionUID = 1L;
	private ForwardMover f;
	private Rotator r;
	private Cleaner c;
	private BackwardMover backw;

	@Override
	protected void setup() {
		System.out.println(this.getClass().getName() + "Moving to the wall");
		this.getDevice(DefaultDevices.BasicBackwardMover.class);
	}

	@Override
	public void install(ForwardMover f, Rotator r, Cleaner c) {
		this.f = f;
		this.r = r;
		this.c = c;
	}

	@Override
	public void update(BackwardMover f) {
		System.out.println("backwardMover received");
		this.backw = f;
	}
}
