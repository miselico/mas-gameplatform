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
package fi.jyu.ties454.cleaningAgents.infra;

import java.util.LinkedList;

import fi.jyu.ties454.cleaningAgents.agent.GameAgent;

class AgentState {

	private final LinkedList<Listener> listeners = new LinkedList<>();

	public interface Listener {
		default void moved() {
			this.changed();
		}

		default void turned() {
			this.changed();
		}

		void changed();
	}

	public void addListener(Listener l) {
		this.listeners.add(l);
	}

	final GameAgent agent;
	private Location l;
	private Orientation o;

	public AgentState(GameAgent agent, Location l, Orientation o) {
		super();
		this.agent = agent;
		this.l = l;
		this.o = o;
	}

	public Location getLocation() {
		return this.l;
	}

	public void setLocation(Location l) {
		if (!this.l.equals(l)) {
			this.l = l;
			this.listeners.forEach(li -> li.moved());
		}

	}

	Orientation getOrientation() {
		return this.o;
	}

	void setOrientation(Orientation o) {
		if (!this.o.equals(o)) {
			this.o = o;
			this.listeners.forEach(li -> li.turned());
		}
	}

	@Override
	public String toString() {
		return "AgentState [agent=" + this.agent.getLocalName() + ", l=" + this.l + ", o=" + this.o + "]";
	}

}
