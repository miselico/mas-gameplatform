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

import java.io.Serializable;

public class Location implements Serializable{

	private static final long serialVersionUID = 1L;

	public final int X;
	public final int Y;

	public Location(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public Location nStep(Orientation o, int n){
		switch (o) {
		case N:
			return new Location(this.X, this.Y - n);
		case E:
			return new Location(this.X + n, this.Y);
		case S:
			return new Location(this.X, this.Y + n);
		case W:
			return new Location(this.X - n, this.Y);
		default:
			throw new Error();
		}		
	}
	
	public Location oneStep(Orientation o) {
		return nStep(o, 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.X;
		result = (prime * result) + this.Y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Location other = (Location) obj;
		if (this.X != other.X) {
			return false;
		}
		if (this.Y != other.Y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Location [X=" + this.X + ", Y=" + this.Y + "]";
	}

}
