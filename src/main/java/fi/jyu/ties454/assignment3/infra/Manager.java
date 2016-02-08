package fi.jyu.ties454.assignment3.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Iterables;

import fi.jyu.ties454.assignment3.actuators.Cleaner;
import fi.jyu.ties454.assignment3.actuators.Dirtier;
import fi.jyu.ties454.assignment3.actuators.ForwardMover;
import fi.jyu.ties454.assignment3.actuators.Rotator;
import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class Manager extends Agent {

	public static final String DEVICE_ACQUISITION_PROTOCOL = "device-acquisition";
	private static final long serialVersionUID = 1L;
	private static final int initialBudget = 10000;
	public static final AID AID = new AID("Manager", false);
	private AtomicInteger cleanersBudget = new AtomicInteger(initialBudget);
	private AtomicInteger soilersBudget = new AtomicInteger(initialBudget);
	private final Map<String, AgentState> cleaners = new HashMap<>();
	private final Map<String, AgentState> soilers = new HashMap<>();
	private final Floor map;
	private final PartsShop partsShop;

	public Manager(int cleaningGroup, int soilingGroup, Floor map, PartsShop partsShop, Random rand) throws Exception {
		this.map = map;
		this.partsShop = partsShop;
		System.out.println("Starting game with the following map:");
		System.out.println(map.toString());
		System.out.println("Adding the following cleaners:");
		for (int i = 1; i <= 3; i++) {
			String cleaningAgentClass = "fi.jyu.ties454.assignment3.group" + cleaningGroup + ".cleaning.Agent" + i;
			CleaningAgent agent = (CleaningAgent) Class.forName(cleaningAgentClass).newInstance();
			AgentState state = new AgentState(agent, map.getRandomLocation(rand), Orientation.random(rand));
			ForwardMover m = new DefaultDevices.BasicForwardMover(map, state);
			Rotator r = new DefaultDevices.BasicRotator(map, state);
			Cleaner c = new DefaultDevices.BasicCleaner(map, state);
			agent.install(m, r, c);

			cleaners.put(cleaningAgentClass, state);
			System.out.println(cleaningAgentClass);
		}
		System.out.println("And the following soilers:");
		for (int i = 1; i <= 3; i++) {
			String soilingAgentClass = "fi.jyu.ties454.assignment3.group" + cleaningGroup + ".soiling.Agent" + i;
			SoilingAgent agent = (SoilingAgent) Class.forName(soilingAgentClass).newInstance();
			AgentState state = new AgentState(agent, map.getRandomLocation(rand), Orientation.random(rand));
			ForwardMover m = new DefaultDevices.BasicForwardMover(map, state);
			Rotator r = new DefaultDevices.BasicRotator(map, state);
			Dirtier c = new DefaultDevices.BasicDirtier(map, state);
			agent.install(m, r, c);
			soilers.put(soilingAgentClass, state);
			System.out.println(soilingAgentClass);
		}
		System.out.println("Following devices are available in the partsShop");
		System.out.println(this.partsShop);
		System.out.println("Let's get started...");
		System.out.println();
	}

	@Override
	protected void setup() {
		AgentContainer ac = this.getContainerController();
		try {
			for (AgentState state : Iterables.concat(cleaners.values(), soilers.values())) {
				ac.acceptNewAgent(state.agent.getClass().getName(), state.agent).start();
			}
		} catch (StaleProxyException e) {
			throw new Error(e);
		}
		addBehaviour(new CyclicBehaviour() {

			private static final long serialVersionUID = 1L;

			MessageTemplate t = MessageTemplate.and(

					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchProtocol(DEVICE_ACQUISITION_PROTOCOL)

			);

			@Override
			public void action() {
				ACLMessage msg = receive(t);
				if (msg != null) {
					String deviceName = msg.getContent();
					String content;
					int performative;
					if (partsShop.partExists(deviceName)) {
						int price = partsShop.getPrice(deviceName);
						AtomicInteger budget;
						AgentState state;
						if ((state = cleaners.get(msg.getSender().getLocalName())) != null) {
							budget = cleanersBudget;
						} else if ((state = soilers.get(msg.getSender().getLocalName())) != null) {
							budget = soilersBudget;
						} else {
							performative = ACLMessage.FAILURE;
							content = "Agent with name " + msg.getSender() + " not known to manager.";
							ACLMessage reply = msg.createReply();
							reply.setPerformative(performative);
							reply.setContent(content);
							send(reply);
							return;
						}
						if (budget.addAndGet(-price) > 0) {
							// successfull
							partsShop.attachPart(deviceName, map, state);
							performative = ACLMessage.AGREE;
							content = "on it's way";
						} else {
							budget.addAndGet(price);
							performative = ACLMessage.REFUSE;
							content = "Not enough budget";
						}
					} else {
						performative = ACLMessage.FAILURE;
						content = "Part does not exist in the partsShop";
					}
					ACLMessage reply = msg.createReply();
					reply.setPerformative(performative);
					reply.setContent(content);
					send(reply);
				} else {
					block();
				}
			}
		});
	}
}
