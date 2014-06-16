package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.MobDefinition;
import org.apollo.util.ByteBufferUtil;

/**
 * A class which parses mob definitions.
 * 
 * @author Chris Fletcher
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobDefinitionParser {

    /**
     * Parses and returns an array of mob definitions from the specified file
     * system.
     * 
     * @param fs The file system in which to read the mob data from.
     * @throws IOException If some I/O exception occurs.
     */
    public static MobDefinition[] parse(FileSystem fs) throws IOException {
	Archive archive = fs.getArchive(FileSystem.CONFIG_ARCHIVE);
	byte[] dataBuffer = archive.get("npc.dat");
	ByteBuffer buffer = ByteBuffer.wrap(archive.get("npc.idx"));
	int count = buffer.getShort() & 0xffff;
	int[] offsets = new int[count];
	int offset = 2;
	for (int index = 0; index < count; index++) {
	    offsets[index] = offset;
	    offset += buffer.getShort() & 0xffff;
	}

	MobDefinition[] defs = new MobDefinition[count];
	ByteBuffer buf = ByteBuffer.wrap(dataBuffer);
	for (int i = 0; i < count; i++) {
	    buf.position(offsets[i]);
	    defs[i] = parseDefinition(i, buf);
	}

	return defs;
    }

    /**
     * Parses and returns a single mob definition.
     * 
     * @param id The id of the mob.
     * @param buffer The buffer in which to read the mob data from.
     */
    private static MobDefinition parseDefinition(int id, ByteBuffer buffer) {
	MobDefinition def = new MobDefinition(id);

	while (true) {
	    int code = buffer.get() & 0xFF;
	    if (code == 0) {
		return def;
	    } else if (code == 1) {
		int length = buffer.get() & 0xFF;
		int[] unknown = new int[length];
		for (int i = 0; i < length; i++) {
		    unknown[i] = buffer.getShort();
		}
	    } else if (code == 2) {
		def.setName(ByteBufferUtil.readString(buffer));
	    } else if (code == 3) {
		def.setDescription(ByteBufferUtil.readString(buffer));
	    } else if (code == 12) {
		def.setSize(buffer.get());
	    } else if (code == 13) {
		def.setStandAnimation(buffer.getShort());
	    } else if (code == 14) {
		def.setWalkAnimation(buffer.getShort());
	    } else if (code == 17) {
		def.setWalkAnimations(buffer.getShort(), buffer.getShort(), buffer.getShort(), buffer.getShort());
	    } else if (code >= 30 && code < 40) {
		String str = ByteBufferUtil.readString(buffer);
		if (str.equals("hidden")) {
		    str = null;
		}
		def.setInteraction(code - 30, str);
	    } else if (code == 40) {
		int length = buffer.get() & 0xFF;
		int[] unknown1 = new int[length];
		int[] unknown2 = new int[length];
		for (int i = 0; i < length; i++) {
		    unknown1[i] = buffer.getShort();
		    unknown2[i] = buffer.getShort();
		}
	    } else if (code == 60) {
		int length = buffer.get() & 0xFF;
		int[] unknown = new int[length];
		for (int i = 0; i < length; i++) {
		    unknown[i] = buffer.getShort();
		}
	    } else if (code == 90) {
		buffer.getShort(); // Dummy
	    } else if (code == 91) {
		buffer.getShort(); // Dummy
	    } else if (code == 92) {
		buffer.getShort(); // Dummy
	    } else if (code == 93) {
		@SuppressWarnings("unused")
		boolean unknown = false; // Initial = true
	    } else if (code == 95) {
		def.setCombatLevel(buffer.getShort());
	    } else if (code == 97) {
		@SuppressWarnings("unused")
		int unknown = buffer.getShort(); // Initial = 128
	    } else if (code == 98) {
		@SuppressWarnings("unused")
		int unknown = buffer.getShort(); // Initial = 128
	    } else if (code == 99) {
		@SuppressWarnings("unused")
		boolean unknown = true; // Initial = false
	    } else if (code == 100) {
		@SuppressWarnings("unused")
		int unknown = buffer.get(); // Initial = 0
	    } else if (code == 101) {
		@SuppressWarnings("unused")
		int unknown = buffer.get() * 5; // Initial = 0
	    } else if (code == 102) {
		@SuppressWarnings("unused")
		int unknown = buffer.getShort(); // Initial = -1
	    } else if (code == 103) {
		@SuppressWarnings("unused")
		int unknown = buffer.getShort(); // Initial = 32
	    } else if (code == 106) {
		int unknown1 = buffer.getShort(); // Initial = -1
		if (unknown1 == 65535) {
		    unknown1 = -1;
		}
		int unknown2 = buffer.getShort(); // Initial = -1
		if (unknown2 == 65535) {
		    unknown2 = -1;
		}

		int count = buffer.get() & 0xFF;
		int[] unknown = new int[count + 1];
		for (int i = 0; i <= count; i++) {
		    int unknown3 = buffer.getShort();
		    if (unknown3 == 65535) {
			unknown3 = -1;
		    }
		    unknown[i] = unknown3;
		}
	    } else if (code == 107) {
		@SuppressWarnings("unused")
		boolean unknown = false; // Initial = true
	    }
	}
    }

}
