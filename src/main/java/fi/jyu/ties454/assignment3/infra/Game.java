package fi.jyu.ties454.assignment3.infra;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class Game {

	private final Floor map;
	private final List<CleaningAgent> cleaners;
	private final List<SoilingAgent> soilers;

	private final Random r;
	private final PartsShop ps;
	private final int gameLength;

	public Game(Floor map, List<CleaningAgent> cleaners) {
		this(map, cleaners, Collections.emptyList());
	}

	public Game(Floor map, List<CleaningAgent> cleaners, List<SoilingAgent> soilers) {
		this(map, cleaners, soilers, 150, new Random());
	}

	public Game(Floor map, List<CleaningAgent> cleaners, List<SoilingAgent> soilers, int gameLength, Random r) {
		this(map, cleaners, soilers, gameLength, r, DefaultDevices.class);
	}

	public Game(Floor map, List<CleaningAgent> cleaners, List<SoilingAgent> soilers, int gameLength, Random r,
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
	public Game(Floor map, List<CleaningAgent> cleaners, List<SoilingAgent> soilers, int gameLength, Random r,
			PartsShop ps) {
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
		pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
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
	}

}
