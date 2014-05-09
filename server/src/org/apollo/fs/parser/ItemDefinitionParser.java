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
		@SuppressWarnings("unused")
		int modelId = buffer.getShort() & 0xFFFF;
	    } else if (code == 2) {
		def.setName(ByteBufferUtil.readString(buffer));
	    } else if (code == 3) {
		def.setDescription(ByteBufferUtil.readString(buffer));
	    } else if (code == 4) {
		@SuppressWarnings("unused")
		int modelScale = buffer.getShort() & 0xFFFF;
	    } else if (code == 5) {
		@SuppressWarnings("unused")
		int modelRotationX = buffer.getShort() & 0xFFFF;
	    } else if (code == 6) {
		@SuppressWarnings("unused")
		int modelRotationY = buffer.getShort() & 0xFFFF;
	    } else if (code == 7) {
		@SuppressWarnings("unused")
		int modelTransformationX = buffer.getShort();
	    } else if (code == 8) {
		@SuppressWarnings("unused")
		int modelTransformationY = buffer.getShort();
	    } else if (code == 10) {
		@SuppressWarnings("unused")
		int dummy = buffer.getShort() & 0xFFFF;
	    } else if (code == 11) {
		def.setStackable(true);
	    } else if (code == 12) {
		def.setValue(buffer.getInt());
	    } else if (code == 16) {
		def.setMembersOnly(true);
	    } else if (code == 23) {
		@SuppressWarnings("unused")
		int maleEquip1 = buffer.getShort() & 0xFFFF;
		@SuppressWarnings("unused")
		int maleYOffset = buffer.get();
	    } else if (code == 24) {
		@SuppressWarnings("unused")
		int maleEquip2 = buffer.getShort() & 0xFFFF;
	    } else if (code == 25) {
		@SuppressWarnings("unused")
		int femaleEquip1 = buffer.getShort() & 0xFFFF;
		@SuppressWarnings("unused")
		int femaleYOffset = buffer.get();
	    } else if (code == 26) {
		@SuppressWarnings("unused")
		int femaleEquip2 = buffer.getShort() & 0xFFFF;
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
		    @SuppressWarnings("unused")
		    int oldColor = buffer.getShort() & 0xFFFF;
		    @SuppressWarnings("unused")
		    int newColor = buffer.getShort() & 0xFFFF;
		}
	    } else if (code == 78) {
		@SuppressWarnings("unused")
		int maleEmblem = buffer.getShort() & 0xFFFF;
	    } else if (code == 79) {
		@SuppressWarnings("unused")
		int femaleEmblem = buffer.getShort() & 0xFFFF;
	    } else if (code == 90) {
		@SuppressWarnings("unused")
		int maleDialogue = buffer.getShort() & 0xFFFF;
	    } else if (code == 91) {
		@SuppressWarnings("unused")
		int femaleDialogue = buffer.getShort() & 0xFFFF;
	    } else if (code == 92) {
		@SuppressWarnings("unused")
		int maleDialogueHat = buffer.getShort() & 0xFFFF;
	    } else if (code == 93) {
		@SuppressWarnings("unused")
		int femaleDialogueHat = buffer.getShort() & 0xFFFF;
	    } else if (code == 95) {
		@SuppressWarnings("unused")
		int diagonalRotation = buffer.getShort() & 0xFFFF;
	    } else if (code == 97) {
		int noteInfoId = buffer.getShort() & 0xFFFF;
		def.setNoteInfoId(noteInfoId);
	    } else if (code == 98) {
		int noteGraphicId = buffer.getShort() & 0xFFFF;
		def.setNoteGraphicId(noteGraphicId);
	    } else if (code >= 100 && code < 110) {
		@SuppressWarnings("unused")
		int stackId = buffer.getShort() & 0xFFFF;
		@SuppressWarnings("unused")
		int stackAmount = buffer.getShort() & 0xFFFF;
	    } else if (code == 110) {
		@SuppressWarnings("unused")
		int modelSizeX = buffer.getShort() & 0xFFFF;
	    } else if (code == 111) {
		@SuppressWarnings("unused")
		int modelSizeY = buffer.getShort() & 0xFFFF;
	    } else if (code == 112) {
		@SuppressWarnings("unused")
		int modelSizeZ = buffer.getShort() & 0xFFFF;
	    } else if (code == 113) {
		@SuppressWarnings("unused")
		int lightModifider = buffer.get();
	    } else if (code == 114) {
		@SuppressWarnings("unused")
		int shadowModifier = buffer.getShort() * 5;
	    } else if (code == 115) {
		@SuppressWarnings("unused")
		int team = buffer.get() & 0xFF;
	    }
	}
    }

}
