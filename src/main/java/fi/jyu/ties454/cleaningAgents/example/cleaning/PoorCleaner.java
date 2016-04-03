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
package fi.jyu.ties454.cleaningAgents.example.cleaning;

import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.agent.Tracker;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.BasicCleaner;
import jade.core.behaviours.OneShotBehaviour;

public class PoorCleaner extends GameAgent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		Tracker t = new Tracker();
		// register the devices to the tracker
		ForwardMover mover = t.registerForwardMover(this.getDevice(DefaultDevices.BasicForwardMover.class).get());
		Rotator rotator = t.registerRotator(this.getDevice(DefaultDevices.BasicRotator.class).get());
		// A cleaner does not move the robot, so no need to register it
		BasicCleaner cleaner = this.getDevice(DefaultDevices.BasicCleaner.class).get();

		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Random rand = new Random();
				// no money -> use free stuff
				while (true) {
					int distance = mover.move();
					if (distance > 0) {
						cleaner.clean();
						if (rand.nextInt(5) == 0) {
							rotator.rotateCW();
						}
					} else {
						rotator.rotateCW();
					}
					System.out.println("Currently at " + t.getLocation() + " heading " + t.getOrientation());
				}
			}
		});
	}
}
