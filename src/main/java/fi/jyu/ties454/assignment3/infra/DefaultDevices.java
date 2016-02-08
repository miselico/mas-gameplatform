package fi.jyu.ties454.assignment3.infra;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import fi.jyu.ties454.assignment3.actuators.BackwardMover;
import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.GameAgent;
import fi.jyu.ties454.assignment3.sensors.DirtSensor;
import fi.jyu.ties454.assignment3.sensors.WallSensor;

/**
 * Class containing the actual implementation of actuators
 * 
 * @author michael
 *
 */
public class DefaultDevices {

	private static final int basicDirtSensorCost = 500;
	private static final int basicWallSensorCost = 500;
	private static final int basicDirtierCost = 0;
	private static final int basicCleanerCost = 0;
	private static final int basicRotatorCost = 0;
	private static final int basicBackwardMovercost = 500;
	private static final int basicForwardMoverCost = 0;
	
	private static long factor = 50;
	private static long basicMoveTime = factor * 5;
	private static long basicCleanTime = factor * 5;
	private static long basicSoilTime = basicCleanTime * 10;
	private static long basicRotateTime = factor * 1;
	private static long basicWallSenseTime = factor * 1;
	private static long basicDirtSenseTime = factor * 1;

	private static int basicWallSensorDepth = 1;



	@Device.AvailableDevice(cost=basicDirtSensorCost)
	public static class BasicDirtSensor extends Device implements DirtSensor {

		BasicDirtSensor(Floor map, AgentState state) {
			super(map, state);
		}

		@Override
		public FloorState inspect() {
			sleep(basicDirtSenseTime);
			return map.state(state.l);
		}

		@Override
		public Optional<Boolean> inspectInFront() {
			return Optional.empty();
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}

	}

	@Device.AvailableDevice(cost=basicWallSensorCost)
	public static class BasicWallSensor extends WallSensorImpl implements WallSensor {

		BasicWallSensor(Floor map, AgentState state) {
			super(map, state, basicWallSensorDepth);
		}

		@Override
		public int canContinueAtLeast() {
			sleep(basicWallSenseTime);
			return super.canContinueAtLeast();
		}
	}

	static class WallSensorImpl extends Device implements WallSensor {

		private final int depth;

		WallSensorImpl(Floor map, AgentState state, int visionDist) {
			super(map, state);
			this.depth = visionDist;
		}

		@Override
		public int canContinueAtLeast() {
			Location newLocation = state.l;
			Orientation orientation = state.o;

			int steps = 0;
			for (; steps < depth; steps++) {
				Location potentialNewLocation = newLocation.oneStep(orientation);
				if (!map.validLocation(potentialNewLocation)) {
					break;
				}
				newLocation = potentialNewLocation;
			}
			return steps;
		}

		@Override
		public int visionDistance() {
			return depth;
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}
	}

	@Device.AvailableDevice(cost=basicDirtierCost)
	public static class BasicDirtier extends Device implements Dirtier {

		BasicDirtier(Floor map, AgentState state) {
			super(map, state);
		}

		@Override
		public void makeMess() {
			sleep(basicSoilTime);
			map.soil(state.l);
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}

	}

	@Device.AvailableDevice(cost=basicCleanerCost)
	public static class BasicCleaner extends Device implements Cleaner {

		BasicCleaner(Floor map, AgentState state) {
			super(map, state);
		}

		@Override
		public void clean() {
			sleep(basicCleanTime);
			map.clean(state.l);
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}

	}

	@Device.AvailableDevice(cost=basicRotatorCost)
	public static class BasicRotator extends Device implements Rotator {

		BasicRotator(Floor map, AgentState state) {
			super(map, state);
		}

		@Override
		public void rotateCW() {
			sleep(basicRotateTime);
			this.state.o = this.state.o.cw();
		}

		@Override
		public void rotateCCW() {
			sleep(basicRotateTime);
			this.state.o = this.state.o.ccw();
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}

	}

	@Device.AvailableDevice(cost=basicBackwardMovercost)
	public static class BasicBackwardMover extends BackwardMoverImpl {
		BasicBackwardMover(Floor floor, AgentState state) {
			super(floor, state, 1);
		}

		@Override
		public int move() {
			sleep(basicMoveTime);
			return super.move();
		}
	}

	static class BackwardMoverImpl extends MoverImpl implements BackwardMover {
		public BackwardMoverImpl(Floor floor, AgentState state, int maxMoveSize) {
			super(floor, state, maxMoveSize, true);
		}

		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}
	}

	@Device.AvailableDevice(cost=basicForwardMoverCost)
	public static class BasicForwardMover extends ForwardMoverImpl {
		BasicForwardMover(Floor floor, AgentState state) {
			super(floor, state, 1);
		}

		@Override
		public int move() {
			sleep(basicMoveTime);
			return super.move();
		}
	}

	static class ForwardMoverImpl extends MoverImpl implements ForwardMover {
		ForwardMoverImpl(Floor floor, AgentState state, int maxMoveSize) {
			super(floor, state, maxMoveSize, false);
		}
		
		@Override
		public void attach(GameAgent agent) {
			agent.update(this);
		}
	}

	static abstract class MoverImpl extends Device {

		private final int maxMove;
		private final boolean reverse;

		MoverImpl(Floor map, AgentState state, int maxMoveSize, boolean reverse) {
			super(map, state);
			this.maxMove = maxMoveSize;
			this.reverse = reverse;
		}

		public int move() {
			Location newLocation = state.l;
			Orientation orientation = this.reverse ? state.o.oposite() : state.o;

			int steps = 0;
			for (; steps < maxMove; steps++) {
				Location potentialNewLocation = newLocation.oneStep(orientation);
				if (!map.validLocation(potentialNewLocation)) {
					break;
				}
				newLocation = potentialNewLocation;
			}
			state.l = newLocation;
			return steps;
		}

	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new Error(e);
		}
	}
}
