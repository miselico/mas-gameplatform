package fi.jyu.ties454.assignment3.infra;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class Run {

	public static void main(String[] args) throws Exception {

		int cleaningGroup = 0;
		int soilingGroup = 0;
		 InputStream is = Run.class.getResourceAsStream("map1.txt");
		 Floor map = Floor.readFromReader(new InputStreamReader(is,
		 StandardCharsets.UTF_8));
		//Floor map = Floor.createSimple();
		Random r = new Random(467545L);
		String partsShopClass = DefaultDevices.class.getName();
		if (args.length > 0) {
			map = Floor.readFromFile(new File(args[0]));
		}
		if (args.length > 1) {
			cleaningGroup = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			soilingGroup = Integer.parseInt(args[2]);
		}
		if (args.length > 3) {
			r = new Random(Long.parseLong(args[3]));
		}
		if (args.length > 4) {
			partsShopClass = args[4];
		}
		PartsShop ps = new PartsShop(Class.forName(partsShopClass));

		
		GUI gui = new GUI();
		map.addListener(gui);
		
		
		Properties pp = new Properties();
		pp.setProperty(Profile.GUI, Boolean.TRUE.toString());
		Profile p = new ProfileImpl(pp);
		AgentContainer ac = jade.core.Runtime.instance().createMainContainer(p);

		try {
			ac.acceptNewAgent(Manager.AID.getLocalName(), new Manager(cleaningGroup, soilingGroup, map, ps, r)).start();
		} catch (StaleProxyException e) {
			throw new Error(e);
		}
		gui.setVisible(true);
	}
}
