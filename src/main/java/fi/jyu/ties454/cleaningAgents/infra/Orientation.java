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

import java.util.Random;

public enum Orientation {
	N, E, S, W;

	public static Orientation random(Random r) {
		return Orientation.values()[r.nextInt(Orientation.values().length)];
	}

	public Orientation oposite() {
		switch (this) {
		case N:
			return S;
		case S:
			return N;
		case E:
			return W;
		case W:
			return E;
		default:
			throw new Error();
		}
	}

	public Orientation cw() {
		switch (this) {
		case N:
			return E;
		case S:
			return W;
		case E:
			return S;
		case W:
			return N;
		default:
			throw new Error();
		}
	}

	public Orientation ccw() {
		switch (this) {
		case N:
			return W;
		case S:
			return E;
		case E:
			return N;
		case W:
			return S;
		default:
			throw new Error();
		}
	}
}
