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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.PlatformController.Listener;
import jade.wrapper.PlatformEvent;
import jade.wrapper.StaleProxyException;

public class Game {

	private final Floor map;
	private final List<GameAgent> cleaners;
	private final List<GameAgent> soilers;

	private final Random r;
	private final PartsShop ps;
	private final int gameLength;

	public Game(Floor map, List<GameAgent> cleaners) {
		this(map, cleaners, Collections.emptyList());
	}

	public Game(Floor map, List<GameAgent> cleaners, List<GameAgent> soilers) {
		this(map, cleaners, soilers, 150, new Random());
	}

	public Game(Floor map, List<GameAgent> cleaners, List<GameAgent> soilers, int gameLength, Random r) {
		this(map, cleaners, soilers, gameLength, r, DefaultDevices.class);
	}

	public Game(Floor map, List<GameAgent> cleaners, List<GameAgent> soilers, int gameLength, Random r,
			Class<?> deviceClass) {
		this(map, cleaners, soilers, gameLength, r, new PartsShop(deviceClass));
	}

	/**
	 * Create a game
	 *
	 * @param map
	 *            The map to be used in the game. Can already contain dirt.
	 * @param cleaners
	 *            The agents which will act as cleaners.
	 * @param soilers
	 *            The agents which will act as soilers.
	 * @param gameLength
	 *            The length of the game in seconds.
	 *
	 * @param r
	 *            A random number generator which decides the initial placement
	 *            and orientation of the agents.
	 * @param ps
	 *            The {@link PartsShop} which defines all possible additional
	 *            parts the agents can aquire.
	 */
	public Game(Floor map, List<GameAgent> cleaners, List<GameAgent> soilers, int gameLength, Random r, PartsShop ps) {
		super();
		this.map = map;
		this.cleaners = cleaners;
		this.soilers = soilers;
		this.gameLength = gameLength;
		this.r = r;
		this.ps = ps;
	}

	/**
	 * Start the game
	 *
	 * @throws Exception
	 */
	public void start() throws Exception {
		Properties pp = new Properties();
		//pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
		pp.setProperty(Profile.NO_MTP, Boolean.TRUE.toString());
		pp.setProperty(Profile.SERVICES, "jade.core.event.NotificationService");

		Profile p = new ProfileImpl(pp);
		AgentContainer ac = jade.core.Runtime.instance().createMainContainer(p);
		Manager manager = new Manager(this.cleaners, this.soilers, this.map, this.ps, this.r, this.gameLength);
		GUI gui = new GUI();
		manager.addListener(gui);
		try {
			ac.acceptNewAgent(Manager.AID.getLocalName(), manager).start();
		} catch (StaleProxyException e) {
			throw new Error(e);
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				gui.setVisible(true);
			}
		});
		gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		ac.addPlatformListener(new Listener() {

			@Override
			public void suspendedPlatform(PlatformEvent anEvent) {
			}

			@Override
			public void startedPlatform(PlatformEvent anEvent) {
			}

			@Override
			public void resumedPlatform(PlatformEvent anEvent) {
			}

			@Override
			public void killedPlatform(PlatformEvent anEvent) {
				gameEnd.countDown();
			}

			@Override
			public void deadAgent(PlatformEvent anEvent) {
			}

			@Override
			public void bornAgent(PlatformEvent anEvent) {
			}
		});
		manager.addListener(new Manager.Listener() {

			@Override
			public void processUpdates(int cleanersBudget, int soilersBudget, Map<String, AgentState> cleaners,
					Map<String, AgentState> soilers, Floor map) {
				// do nothing
			}

			@Override
			public void gameEnded(double score) {
				finalScore = score;
				gameEnd.countDown();
			}
		});
	}

	// this latch is used to sync the game with other threads waiting for the
	// game to end.
	// When 0, the game has ended and the final score of the game recorded in
	// finalScore.
	// One countdown is performed when the manager agent ends
	// One countdown when the platform ends
	private final CountDownLatch gameEnd = new CountDownLatch(2);
	private double finalScore = Double.NaN;

	public double awaitEnd() throws InterruptedException {
		gameEnd.await();
		return finalScore;
	}

}
