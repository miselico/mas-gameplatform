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
package fi.jyu.ties454.cleaningAgents.example.soiling;

import java.util.Optional;

import fi.jyu.ties454.cleaningAgents.actuators.Dirtier;
import fi.jyu.ties454.cleaningAgents.actuators.Rotator;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.DirtExplosion;
import fi.jyu.ties454.cleaningAgents.infra.DefaultDevices.JumpForwardMover;
import jade.core.behaviours.OneShotBehaviour;

public class Soiler2 extends GameAgent {

	private static final long serialVersionUID = 1L;

	private Rotator rotator;
	private Dirtier dirtier;

	@Override
	protected void setup() {

		this.rotator = this.getDevice(DefaultDevices.BasicRotator.class).get();
		this.dirtier = this.getDevice(DefaultDevices.BasicDirtier.class).get();

		this.addBehaviour(new OneShotBehaviour() {

			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				Optional<DirtExplosion> dirtExplosion = Soiler2.this.getDevice(DefaultDevices.DirtExplosion.class);
				if (dirtExplosion.isPresent()) {
					dirtExplosion.get().makeMess();
				}
				Optional<JumpForwardMover> jumper = Soiler2.this.getDevice(DefaultDevices.JumpForwardMover.class);
				if (jumper.isPresent()) {
					jumper.get().move();
					jumper.get().move();
					while (true) {
						Soiler2.this.rotator.rotateCW();
						while (jumper.get().move() == 5) {
							Soiler2.this.dirtier.makeMess();
						}
					}
				}
			}
		});

	}

}
