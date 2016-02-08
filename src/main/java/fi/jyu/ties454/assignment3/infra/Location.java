package fi.jyu.ties454.assignment3.infra;

public class Location {
	public final int X;
	public final int Y;

	public Location(int x, int y) {
		X = x;
		Y = y;
	}

	public Location oneStep(Orientation o) {
		switch (o) {
		case N:
			return new Location(X, Y - 1);
		case E:
			return new Location(X + 1, Y);
		case S:
			return new Location(X, Y + 1);
		case W:
			return new Location(X - 1, Y);
		default:
			throw new Error();
		}
	}
}
