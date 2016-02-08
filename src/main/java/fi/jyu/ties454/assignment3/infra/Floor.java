package fi.jyu.ties454.assignment3.infra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TreeBasedTable;

public class Floor {

	interface FloorUpdateListener {
		void update(String newState);
	}

	private List<FloorUpdateListener> listeners = new LinkedList<>();

	public void addListener(FloorUpdateListener l) {
		this.listeners.add(l);
		this.fireUpdate();
	}

	private void fireUpdate() {
		String newState = this.toString();
		this.listeners.forEach(l -> l.update(newState));
	}

	/**
	 * Internally the structure is stored as a table. The axis of this table are
	 * somewhat unexpected. The rows are X and the columns are Y!, This seems
	 * opposite from what is used otherwise.
	 */
	private final Table<Integer, Integer, FloorState> map = TreeBasedTable.create();

	private Floor() {
	}

	/**
	 * Get a random location.
	 * 
	 * @param r
	 * @return
	 */
	Location getRandomLocation(Random r) {
		Cell<Integer, Integer, FloorState> cell = Iterables.get(map.cellSet(), r.nextInt((map.cellSet().size())));
		return new Location(cell.getRowKey(), cell.getColumnKey());
	}

	public boolean validLocation(Location potentialNewLocation) {
		return map.contains(potentialNewLocation.Y, potentialNewLocation.X);
	}

	synchronized public void clean(Location l) {
		Preconditions.checkArgument(validLocation(l));
		if (state(l) != FloorState.CLEAN) {
			map.put(l.Y, l.X, FloorState.CLEAN);
			this.fireUpdate();
		}
	}

	synchronized public void soil(Location l) {
		Preconditions.checkArgument(validLocation(l));
		if (state(l) != FloorState.DIRTY) {
			map.put(l.Y, l.X, FloorState.DIRTY);
			this.fireUpdate();
		}
	}

	synchronized public FloorState state(Location l) {
		Preconditions.checkArgument(validLocation(l));
		return map.get(l.Y, l.X);
	}

	public static Floor createSimple() {
		Floor e = new Floor();
		for (int y = 0; y < 30; y++) {
			for (int x = 0; x < 20; x++) {
				e.map.put(y, x, FloorState.CLEAN);
			}
		}
		return e;
	}

	private static final char CLEANCHAR = 'C';
	private static final char DIRTYCHAR = 'D';
	private static final char VOIDCHAR = ' ';

	void writeToWriter(Writer r) throws IOException {
		int y = 0;
		// traversal is in order (treebased)
		for (Entry<Integer, Map<Integer, FloorState>> row : this.map.rowMap().entrySet()) {
			while (y != row.getKey()) {
				r.write('\n');
				y++;
			}
			int x = 0;
			for (Entry<Integer, FloorState> column : row.getValue().entrySet()) {
				while (column.getKey() != x) {
					r.write(VOIDCHAR);
					x++;
				}
				if (column.getValue() == FloorState.CLEAN) {
					r.write(CLEANCHAR);
				} else if (column.getValue() == FloorState.DIRTY) {
					r.write(DIRTYCHAR);
				} else {
					throw new Error();
				}
				x++;
			}
		}
		r.write('\n');
	}

	public void writeToFile(File descriptor) throws FileNotFoundException, IOException {
		try (BufferedWriter r = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(descriptor), StandardCharsets.UTF_8))) {
			writeToWriter(r);
		}
	}

	static Floor readFromReader(Reader r0) throws IOException {
		Floor e = new Floor();
		String line;
		int y = 0;
		try (BufferedReader r = new BufferedReader(r0)) {
			while ((line = r.readLine()) != null) {
				int x = 0;
				// ASCII encoding, so bytes can be used
				for (byte b : line.getBytes()) {
					switch (b) {
					case VOIDCHAR:
						// void
						break;
					case CLEANCHAR:
						// clean
						e.map.put(y, x, FloorState.CLEAN);
						break;
					case DIRTYCHAR:
						// dirty
						e.map.put(y, x, FloorState.DIRTY);
						break;
					default:
						throw new Error("Unknown character in map " + (char) b);
					}
					x++;
				}
				y++;
			}
		}
		return e;
	}

	public static Floor readFromFile(File descriptor) throws FileNotFoundException, IOException {
		try (BufferedReader r = new BufferedReader(
				new InputStreamReader(new FileInputStream(descriptor), StandardCharsets.UTF_8))) {
			return readFromReader(r);
		}
	}

	@Override
	public String toString() {
		StringWriter w = new StringWriter();
		try {
			this.writeToWriter(w);
		} catch (IOException e) {
			throw new Error("Writing to stringwriter should never throw IOExceptions", e);
		}
		return w.toString();
	}

}
