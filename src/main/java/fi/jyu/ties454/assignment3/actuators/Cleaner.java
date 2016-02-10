package fi.jyu.ties454.assignment3.actuators;

public interface Cleaner {
	/**
	 * Cleans according to the actuators specs.
	 *
	 * @return was there dirt in the area?
	 */
	boolean clean();
}
