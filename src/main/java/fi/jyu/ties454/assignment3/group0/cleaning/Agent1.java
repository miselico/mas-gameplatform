package fi.jyu.ties454.assignment3.group0.cleaning;

import java.util.UUID;

import fi.jyu.ties454.assignment3.actuators.BackwardMover;
import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import fi.jyu.ties454.assignment3.infra.DefaultDevices;
import fi.jyu.ties454.assignment3.infra.Manager;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Agent1 extends CleaningAgent {

	private static final long serialVersionUID = 1L;
	private ForwardMover f;
	private Rotator r;
	private Cleaner c;
	private BackwardMover backw;

	@Override
	protected void setup() {
		System.out.println(getClass().getName() + "Moving to the wall");
//		while (true) {
//			int totalMoved = 0;
//			this.r.rotateCW();
//			int moved = 0;
//			while ((moved = this.f.move()) > 0) {
//				totalMoved += moved;
//			}
//			System.out.println("moved " + totalMoved);
//		}
		ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
		m.addReceiver(Manager.AID);
		m.setProtocol(Manager.DEVICE_ACQUISITION_PROTOCOL);
		m.setContent(DefaultDevices.BasicBackwardMover.class.getName());
		m.setReplyWith(UUID.randomUUID().toString());
		send(m);
		ACLMessage response = blockingReceive(MessageTemplate.MatchInReplyTo(m.getReplyWith()));
		System.out.println(response);
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
