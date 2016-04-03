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

import java.util.Optional;
import java.util.Random;

import fi.jyu.ties454.cleaningAgents.actuators.Cleaner;
import fi.jyu.ties454.cleaningAgents.actuators.ForwardMover;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.BasicDirtSensor;
import fi.jyu.ties454.cleaningAgents.infra.FloorState;
import jade.core.behaviours.OneShotBehaviour;

public class Cleaner2 extends GameAgent {

	private static final long serialVersionUID = 1L;

	private ForwardMover mover;
	private Rotator rotator;
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
				Random rand = new Random();
				// no money -> use cheap stuff
				Optional<BasicDirtSensor> ds = Cleaner2.this.getDevice(DefaultDevices.BasicDirtSensor.class);
				while (true) {
					if (Cleaner2.this.mover.move() > 0) {
						// if there is a sensor, use it
						if ((!ds.isPresent()) || (ds.get().inspect() == FloorState.DIRTY)) {
							Cleaner2.this.cleaner.clean();
						}
					}
					int prob = rand.nextInt(10);
					if (prob == 0) {
						Cleaner2.this.rotator.rotateCW();
					} else if (prob == 1) {
						Cleaner2.this.rotator.rotateCCW();
					}
				}
			}

		});
	}
}
