package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.util.ByteBufferUtil;

/**
 * A class which parses item definitions.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ItemDefinitionParser {

    /**
     * Parses and returns the all found item definitions within the cache.
     *
     * @param fs The file system to parse from.
     * @throws IOException If some I/O exception occurs.
     */
    public static ItemDefinition[] parse(FileSystem fs) throws IOException {
	Archive archive = fs.getArchive(FileSystem.CONFIG_ARCHIVE);
	byte[] dataBuffer = archive.get("obj.dat");
	ByteBuffer buffer = ByteBuffer.wrap(archive.get("obj.idx"));
	int count = buffer.getShort() & 0xffff;
	int[] offsets = new int[count];
	int offset = 2;
	for (int index = 0; index < count; index++) {
	    offsets[index] = offset;
	    offset += buffer.getShort() & 0xffff;
	}

	ItemDefinition[] defs = new ItemDefinition[count];
	ByteBuffer buf = ByteBuffer.wrap(dataBuffer);
	for (int i = 0; i < count; i++) {
	    buf.position(offsets[i]);
	    defs[i] = parseDefinition(i, buf);
	}

	return defs;
    }

    /**
     * Parses and returns a single item definition/
     *
     * @param id The items id.
     * @param buffer The buffer in which to read the item data from.
     */
    private static ItemDefinition parseDefinition(int id, ByteBuffer buffer) {
	ItemDefinition def = new ItemDefinition(id);
	while (true) {
	    int code = buffer.get() & 0xFF;

	    if (code == 0) {
		return def;
	    } else if (code == 1) {
		buffer.getShort();
	    } else if (code == 2) {
		def.setName(ByteBufferUtil.readString(buffer));
	    } else if (code == 3) {
		def.setDescription(ByteBufferUtil.readString(buffer));
	    } else if (code == 4) {
		buffer.getShort();
	    } else if (code == 5) {
		buffer.getShort();
	    } else if (code == 6) {
		buffer.getShort();
	    } else if (code == 7) {
		buffer.getShort();
	    } else if (code == 8) {
		buffer.getShort();
	    } else if (code == 10) {
		buffer.getShort();
	    } else if (code == 11) {
		def.setStackable(true);
	    } else if (code == 12) {
		def.setValue(buffer.getInt());
	    } else if (code == 16) {
		def.setMembersOnly(true);
	    } else if (code == 23) {
		buffer.getShort();
		buffer.get();
	    } else if (code == 24) {
		buffer.getShort();
	    } else if (code == 25) {
		buffer.getShort();
		buffer.get();
	    } else if (code == 26) {
		buffer.getShort();
	    } else if (code >= 30 && code < 35) {
		String str = ByteBufferUtil.readString(buffer);
		if (str.equalsIgnoreCase("hidden")) {
		    str = null;
		}
		def.setGroundAction(code - 30, str);
	    } else if (code >= 35 && code < 40) {
		String str = ByteBufferUtil.readString(buffer);
		def.setInventoryAction(code - 35, str);
	    } else if (code == 40) {
		int colorCount = buffer.get() & 0xFF;
		for (int i = 0; i < colorCount; i++) {
		    buffer.getShort();
		    buffer.getShort();
		}
	    } else if (code == 78) {
		buffer.getShort();
	    } else if (code == 79) {
		buffer.getShort();
	    } else if (code == 90) {
		buffer.getShort();
	    } else if (code == 91) {
		buffer.getShort();
	    } else if (code == 92) {
		buffer.getShort();
	    } else if (code == 93) {
		buffer.getShort();
	    } else if (code == 95) {
		buffer.getShort();
	    } else if (code == 97) {
		int noteInfoId = buffer.getShort() & 0xFFFF;
		def.setNoteInfoId(noteInfoId);
	    } else if (code == 98) {
		int noteGraphicId = buffer.getShort() & 0xFFFF;
		def.setNoteGraphicId(noteGraphicId);
	    } else if (code >= 100 && code < 110) {
		buffer.getShort();
		buffer.getShort();
	    } else if (code == 110) {
		buffer.getShort();
	    } else if (code == 111) {
		buffer.getShort();
	    } else if (code == 112) {
		buffer.getShort();
	    } else if (code == 113) {
		buffer.get();
	    } else if (code == 114) {
		buffer.getShort();
	    } else if (code == 115) {
		buffer.get();
	    }
	}
    }

}
