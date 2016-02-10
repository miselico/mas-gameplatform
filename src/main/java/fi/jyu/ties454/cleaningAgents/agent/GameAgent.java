package fi.jyu.ties454.cleaningAgents.agent;

import java.util.UUID;

import fi.jyu.ties454.cleaningAgents.actuators.BackwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.infra.Device;
import fi.jyu.ties454.cleaningAgents.infra.Manager;
import fi.jyu.ties454.cleaningAgents.sensors.DirtSensor;
import fi.jyu.ties454.cleaningAgents.sensors.WallSensor;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GameAgent extends Agent {

	private static final long serialVersionUID = 1L;

	public void update(ForwardMover f) {
	};

	public void update(BackwardMover f) {
	};

	public void update(Dirtier f) {
	};

	public void update(Rotator f) {
	};

	public void update(DirtSensor f) {
	};

	public void update(WallSensor f) {
	};

	public void update(Cleaner f) {
	};

	/**
	 * Sends a request to get the device specified by deviceClass.
	 *
	 * @param deviceClass
	 * @return
	 */
	public boolean getDevice(Class<? extends Device> deviceClass) {
		ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
		m.addReceiver(Manager.AID);
		m.setProtocol(Manager.DEVICE_ACQUISITION_PROTOCOL);
		m.setContent(deviceClass.getName());
		m.setReplyWith(UUID.randomUUID().toString());
		this.send(m);
		ACLMessage response = this.blockingReceive(MessageTemplate.MatchInReplyTo(m.getReplyWith()));
		return response.getPerformative() == ACLMessage.AGREE;
	}
}
