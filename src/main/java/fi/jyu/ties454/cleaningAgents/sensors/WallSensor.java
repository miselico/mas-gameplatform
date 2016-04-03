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

public interface WallSensor {
	/**
	 * Is there a wall right in front of the agent?
	 *
	 * @return
	 */
	public default boolean wallInfront() {
		return this.canContinueAtLeast() == 0;
	};

	/**
	 * An underestimation of the number of steps the agent can still move
	 * forward. If smaller than {@link WallSensor#visionDistance()} then the
	 * estimate is exact.
	 *
	 * @return
	 */
	public int canContinueAtLeast();

	/**
	 * How far can this sensor 'see'.
	 *
	 * @return
	 */
	public int visionDistance();
}
