package fi.jyu.ties454.assignment3.group0.task4;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import fi.jyu.ties454.assignment3.group0.task3.MyCleaner;
import fi.jyu.ties454.assignment3.group0.task3.MyDirtier;
import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.Floor;
import fi.jyu.ties454.cleaningAgents.infra.Game;

public abstract class Fight {

	private static final int REPEATS = 2;

	public double start() throws Exception {
		double score = 0;
		for (Floor map : maps) {
			for (int i = 0; i < REPEATS; i++) {
				List<GameAgent> cleaners = getFreshCleaners();
				List<GameAgent> dirters = getFreshDirtiers();
				Game g = new Game(map.copyMap(), cleaners, dirters, 15, new Random());
				// Start the game. This will also show the a 'graphical'
				// representation
				// of the state of the rooms.
				// The agent will start on a random location on the map.
				g.start();
				score += g.awaitEnd();
			}
		}
		return score;
	}

	protected abstract List<GameAgent> getFreshCleaners();

	protected abstract List<GameAgent> getFreshDirtiers();

	private static final ImmutableList<String> mapResources = ImmutableList.of("cycles.txt", "map.txt",
			"rectangleRoomLargeClean.txt", "rectangleRoomSmallClean.txt", "spiral.txt");

	private static final ArrayList<Floor> maps;

	static {
		maps = new ArrayList<>(mapResources.size());
		for (String mapResourceName : mapResources) {
			InputStream is = Fight.class.getResourceAsStream(mapResourceName);
			if (is == null) {
				throw new Error("default map could not be loaded : " + mapResourceName);
			}
			try (InputStreamReader r = new InputStreamReader(is, StandardCharsets.US_ASCII)) {
				Floor map = Floor.readFromReader(r);
				maps.add(map);
			} catch (IOException e) {
				throw new Error("default map could not be loaded : " + mapResourceName, e);
			}
		}
	}

	private static final double maxScore = maps.size() * REPEATS;

	public static void main(String[] args) throws Exception {

		Fight AcleansBdirts = new Fight() {

			@Override
			protected List<GameAgent> getFreshCleaners() {
				// cleaners of A here
				return ImmutableList.of(new MyCleaner(), new MyCleaner(), new MyCleaner());
			}

			@Override
			protected List<GameAgent> getFreshDirtiers() {
				// dirters of B here
				return ImmutableList.of(new MyDirtier(), new MyDirtier());
			}

		};
		final double cleanersScoreA = AcleansBdirts.start();
		final double dirtiersScoreB = maxScore - cleanersScoreA;

		Fight BcleansAdirts = new Fight() {

			@Override
			protected List<GameAgent> getFreshCleaners() {
				return ImmutableList.of(new MyCleaner(), new MyCleaner(), new MyCleaner());
			}

			@Override
			protected List<GameAgent> getFreshDirtiers() {
				return ImmutableList.of(new MyDirtier(), new MyDirtier());
			}

		};

		final double cleanersScoreB = BcleansAdirts.start();
		final double dirtiersScoreA = maxScore - cleanersScoreB;

		System.out.println("FIGHT ENDED");
		System.out.printf("accumulated score for team A. Cleaners: %f, Dirtiers: %f\n", cleanersScoreA, dirtiersScoreA);
		System.out.printf("accumulated score for team B. Cleaners: %f, Dirtiers: %f\n", cleanersScoreB, dirtiersScoreB);
	}
}
