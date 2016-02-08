package fi.jyu.ties454.assignment3.actuators;

public interface BackwardMover {
	/**
	 * Moves the agent backward according to the actuators capabilities. Returns
	 * the number of cells moved.
	 * 
	 * @return The number of cells moved backward
	 */
	int move();
}
