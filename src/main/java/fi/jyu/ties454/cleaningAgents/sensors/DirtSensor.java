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
package fi.jyu.ties454.cleaningAgents.sensors;

import java.util.Optional;

import fi.jyu.ties454.cleaningAgents.infra.FloorState;

public interface DirtSensor {
	/**
	 * Is there dirt at the location of the agent?
	 *
	 * @return
	 */
	FloorState inspect();

	/**
	 * Is there dirt in front of the agent. Not all sensors support this.
	 *
	 * @return
	 */
	Optional<Boolean> dirtInFront();
}
