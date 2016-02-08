package fi.jyu.ties454.assignment3.infra;

import java.util.Random;

public enum Orientation {
	N, E, S, W;

	public static Orientation random(Random r) {
		return values()[r.nextInt(values().length)];
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
