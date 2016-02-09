package fi.jyu.ties454.assignment3.infra;

public class Location {
	public final int X;
	public final int Y;

	public Location(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public Location oneStep(Orientation o) {
		switch (o) {
		case N:
			return new Location(this.X, this.Y - 1);
		case E:
			return new Location(this.X + 1, this.Y);
		case S:
			return new Location(this.X, this.Y + 1);
		case W:
			return new Location(this.X - 1, this.Y);
		default:
			throw new Error();
		}
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
