package fi.jyu.ties454.assignment3.actuators;

public interface ForwardMover {
	/**
	 * Moves the agent forward according to the actuators capabilities. Returns
	 * the number of cells moved.
	 *
	 * @return The number of cells moved forward
	 */
	int move();
}
