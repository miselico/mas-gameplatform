package fi.jyu.ties454.assignment3.infra;

public enum FloorState {
	CLEAN, DIRTY;

	public FloorState invert() {
		return this == CLEAN ? DIRTY : CLEAN;
	}
}
