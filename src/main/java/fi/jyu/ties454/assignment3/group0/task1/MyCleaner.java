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
package fi.jyu.ties454.assignment3.group0.task1;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import jade.core.behaviours.OneShotBehaviour;

/**
 * The agent extends from CleaningAgent, which is actually a normal JADE agent.
 * As an extra it has methods to obtain sensors and actuators.
 */
public class MyCleaner extends GameAgent {

	private static final long serialVersionUID = 1L;
	/*
	 * the following three sensors and actuators are received from the install
	 * method below.
	 *
	 * Note that sensors and actuators MUST only be used inside behaviors on the
	 * agent thread. In other words, you are not allowed to use them directly in
	 * the setup method, but have to add a behavior inside which they can be
	 * used. Further, you are also not allowed to use them from other threads
	 * than the agent's own thread.
	 */
	// The mover has a move() method. This moves the robot one cell forward.
	// Returns 0 if moving forward failed: hit a wall.
	private ForwardMover mover;
	// the rotator has a rotateCW() and rotateCCW() method for clockwise and
	// counter-clockwise rotation
	private Rotator rotator;
	// the cleaner has a clean() method, which cleans the cell under the robot
	private Cleaner cleaner;

	@Override
	protected void setup() {

		this.mover = this.getDevice(DefaultDevices.BasicForwardMover.class).get();
		this.rotator = this.getDevice(DefaultDevices.BasicRotator.class).get();
		this.cleaner = this.getDevice(DefaultDevices.BasicCleaner.class).get();

		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				// TODO implement using the mover, rotator, and cleaner.
			}
		});
	}

}
