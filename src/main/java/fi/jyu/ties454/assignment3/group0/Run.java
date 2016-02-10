package fi.jyu.ties454.assignment3.group0;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import fi.jyu.ties454.assignment3.agent.CleaningAgent;
import fi.jyu.ties454.assignment3.agent.SoilingAgent;
import fi.jyu.ties454.assignment3.group0.cleaning.Cleaner1;
import fi.jyu.ties454.assignment3.group0.cleaning.Cleaner2;
import fi.jyu.ties454.assignment3.group0.cleaning.Cleaner3;
import fi.jyu.ties454.assignment3.group0.soiling.Soiler1;
import fi.jyu.ties454.assignment3.group0.soiling.Soiler2;
import fi.jyu.ties454.assignment3.group0.soiling.Soiler3;
import fi.jyu.ties454.assignment3.infra.Floor;
import fi.jyu.ties454.assignment3.infra.Game;

public class Run {
	public static void main(String[] args) throws Exception {
		InputStream is = Game.class.getResourceAsStream("/fi/jyu/ties454/assignment3/group0/map3.txt");
		Floor map = Floor.readFromReader(new InputStreamReader(is, StandardCharsets.US_ASCII));

		List<CleaningAgent> cleaners = ImmutableList.of(new Cleaner1(), new Cleaner2(), new Cleaner3());
		List<SoilingAgent> soilers = ImmutableList.of(new Soiler1(), new Soiler2(), new Soiler3());

		// Random r = new Random(467545L);
		Random r = new Random(78978L);
		Game g = new Game(map, cleaners, soilers, 300, r);
		g.start();
	}
}
