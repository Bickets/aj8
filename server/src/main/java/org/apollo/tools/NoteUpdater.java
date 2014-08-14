package org.apollo.tools;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apollo.fs.FileSystem;
import org.apollo.fs.parser.ItemDefinitionParser;
import org.apollo.game.model.def.ItemDefinition;

/**
 * A tool for updating the note data.
 *
 * @author Graham
 */
public final class NoteUpdater {

    /**
     * The entry point of the application.
     *
     * @param args The command line arguments.
     * @throws IOException If some I/O exception occurs.
     */
    public static void main(String[] args) throws IOException {

	try (DataOutputStream os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data/note.dat")))) {
	    FileSystem fs = FileSystem.create("data/fs/");
	    ItemDefinition[] defs = ItemDefinitionParser.parse(fs);
	    ItemDefinition.init(defs);

	    os.writeShort(defs.length);

	    Map<Integer, Integer> itemToNote = new HashMap<Integer, Integer>();

	    for (int id = 0; id < defs.length; id++) {
		ItemDefinition def = ItemDefinition.forId(id);
		if (def.isNote()) {
		    itemToNote.put(def.getNoteInfoId(), def.getId());
		}
	    }

	    for (int id = 0; id < defs.length; id++) {
		if (itemToNote.containsKey(id)) {
		    os.writeBoolean(true); // notable
		    os.writeShort(itemToNote.get(id));
		} else {
		    os.writeBoolean(false); // not notable
		}
	    }
	}
    }

}