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
package fi.jyu.ties454.assignment3.group0.task3;

import java.util.Optional;

import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.JackieChanRotator;
import jade.core.behaviours.OneShotBehaviour;

/**
 * The agent extends from CleaningAgent, which is actually a normal JADE agent.
 * As an extra it has methods to obtain sensors and actuators.
 */
public class MyCleaner extends GameAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		// it is safe to obtain parts in setup(), but using them must be done in
		// behaviors!
		// getting the device is don using the getDevice call.
		// This call returns an Optional<E extends Device> which is present if
		// the device was successfully obtained
		Optional<JackieChanRotator> fastRotator = this.getDevice(DefaultDevices.JackieChanRotator.class);
		if (fastRotator.isPresent()) {
			System.out.println("Got the moves");
		}
		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				// TODO implement using whatever toys you got, like the
				// fastRotator
			}
		});
	}
}
