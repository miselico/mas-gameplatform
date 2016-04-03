/*******************************************************************************
 * Copyright 2016 Michael Cochez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package fi.jyu.ties454.cleaningAgents.agent;

import java.util.Optional;
import java.util.UUID;

import fi.jyu.ties454.cleaningAgents.infra.Device;
import fi.jyu.ties454.cleaningAgents.infra.Manager;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GameAgent extends Agent {

	private static final long serialVersionUID = 1L;

	public static class NoSuchDeviceException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public <E extends Device> NoSuchDeviceException(Class<E> deviceClass) {
			super("There is no device of class " + deviceClass.getName() + " available");
		}
	}

	/**
	 * Sends a request to get the device specified by deviceClass.
	 *
	 * @param deviceClass
	 * @return
	 */
	public <E extends Device> Optional<E> getDevice(Class<E> deviceClass) {
		try {
			ACLMessage m = new ACLMessage(ACLMessage.REQUEST);
			m.addReceiver(Manager.AID);
			m.setProtocol(Manager.DEVICE_ACQUISITION_PROTOCOL);
			m.setContent(deviceClass.getName());
			m.setReplyWith(UUID.randomUUID().toString());
			this.send(m);
			ACLMessage response = this.blockingReceive(MessageTemplate.MatchInReplyTo(m.getReplyWith()));
			if (response.getPerformative() == ACLMessage.AGREE) {
				if (this.currentDevice == null) {
					throw new Error("Manager informaed that device was delivered, but nothing received.");
				}
				E theCurrentDevice = deviceClass.cast(this.currentDevice);
				return Optional.of(theCurrentDevice);
			} else if (response.getPerformative() == ACLMessage.REFUSE) {
				return Optional.empty();
			} else if (response.getPerformative() == ACLMessage.FAILURE) {
				throw new NoSuchDeviceException(deviceClass);
			} else {
				throw new Error(Manager.AID.getLocalName() + " send message with unknown performative");
			}
		} finally {
			this.currentDevice = null;
		}
	}

	private Device currentDevice = null;

	public final void update(Device d) {
		this.currentDevice = d;
	}

}
