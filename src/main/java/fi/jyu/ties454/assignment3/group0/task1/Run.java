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
package fi.jyu.ties454.assignment3.group0.task1;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.common.collect.ImmutableList;

import fi.jyu.ties454.cleaningAgents.agent.GameAgent;
import fi.jyu.ties454.cleaningAgents.infra.Floor;
import fi.jyu.ties454.cleaningAgents.infra.Game;

/**
 * Class to start the simulation
 *
 * @author michael
 *
 */
public class Run {
	public static void main(String[] args) throws Exception {
		/*
		 * Load the map from the text file 'rectangleRoom.txt'. This file has to
		 * be in the resources folder. Note that you must have copied that
		 * folder according to the instructions given.
		 */
		InputStream is = Run.class.getResourceAsStream("rectangleRoom.txt");
		if (is == null) {
			System.err.println("Did you copy the resource folder as instructed?");
			System.exit(1);
		}
		Floor map = Floor.readFromReader(new InputStreamReader(is, StandardCharsets.US_ASCII));

		// The game needs a list of cleaners. For the first task only one
		// cleaner is used.
		List<GameAgent> cleaners = ImmutableList.of(new MyCleaner());

		// Create a game with the map and the cleaners. There are also
		// constructors which take more arguments. They will be used in later
		// exercises.
		Game g = new Game(map, cleaners);
		// Start the game. This will also show the a 'graphical' representation
		// of the state of the rooms.
		// The agent will start on a random location on the map.
		g.start();
	}
}
