package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.util.ByteBufferUtil;

/**
 * A class which parses object definitions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Steve, initial contribution
 */
public final class GameObjectDefinitionParser {

	/**
	 * Reads object information from the file system and parses all of the
	 * definitions.
	 *
	 * @param fs The file system.
	 * @return An array of game object definitions.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static GameObjectDefinition[] parse(FileSystem fs) throws IOException {
		Archive archive = fs.getArchive(FileSystem.CONFIG_ARCHIVE);
		ByteBuffer dataBuffer = archive.getData("loc.dat");
		ByteBuffer buffer = archive.getData("loc.idx");

		int count = buffer.getShort() & 0xFFFF;
		int[] indices = new int[count];

		int index = 2;
		for (int i = 0; i < count; i++) {
			indices[i] = index;
			index += buffer.getShort() & 0xFFFF;
		}

		GameObjectDefinition[] defs = new GameObjectDefinition[count];
		for (int i = 0; i < count; i++) {
			dataBuffer.position(indices[i]);
			defs[i] = parseDefinition(i, dataBuffer);
		}

		return defs;
	}

	/**
	 * Parses a single game object definition by reading object info from a
	 * buffer.
	 *
	 * @param id The id of the object.
	 * @param buffer The buffer.
	 * @return The read game object definition.
	 */
	private static GameObjectDefinition parseDefinition(int id, ByteBuffer buffer) {
		GameObjectDefinition def = new GameObjectDefinition(id);

		while (true) {
			int code = buffer.get() & 0xFF;

			if (code == 0) {
				return def;
			} else if (code == 1) {
				final int amount = buffer.get() & 0xFF;
				for (int i = 0; i < amount; i++) {
					buffer.getShort();
					buffer.get();
				}
			} else if (code == 2) {
				def.setName(ByteBufferUtil.readString(buffer));
			} else if (code == 3) {
				def.setDescription(ByteBufferUtil.readString(buffer));
			} else if (code == 5) {
				final int amount = buffer.get() & 0xFF;
				for (int i = 0; i < amount; i++) {
					buffer.getShort();
				}
			} else if (code == 14) {
				def.setWidth(buffer.get() & 0xFF);
			} else if (code == 15) {
				def.setLength(buffer.get() & 0xFF);
			} else if (code == 17) {
				def.setSolid(true);
			} else if (code == 18) {
				def.setImpenetrable(true);
			} else if (code == 19) {
				int interactableValue = buffer.get() & 0xFF;
				def.setInteractable(interactableValue == 1);
			} else if (code == 24) {
				buffer.getShort();
			} else if (code == 28) {
				buffer.get();
			} else if (code == 29) {
				buffer.get();
			} else if (code >= 30 && code < 39) {
				final String action = ByteBufferUtil.readString(buffer);
				def.addAction(code - 30, action);
			} else if (code == 39) {
				buffer.get();
			} else if (code == 40) {
				final int amount = buffer.get() & 0xFF;
				for (int i = 0; i < amount; i++) {
					buffer.getShort();
					buffer.getShort();
				}
			} else if (code == 60) {
				buffer.getShort();
			} else if (code == 65) {
				final int scaleX = buffer.getShort() & 0xFFFF;
				def.setScaleX(scaleX);
			} else if (code == 66) {
				final int scaleY = buffer.getShort() & 0xFFFF;
				def.setScaleY(scaleY);
			} else if (code == 67) {
				final int scaleZ = buffer.getShort() & 0xFFFF;
				def.setScaleZ(scaleZ);
			} else if (code == 68) {
				final int mapSceneId = buffer.getShort() & 0xFFFF;
				def.setMapSceneId(mapSceneId);
			} else if (code == 69) {
				buffer.get();
			} else if (code == 70) {
				final int offsetX = buffer.getShort() & 0xFFFF;
				def.setOffsetX(offsetX);
			} else if (code == 71) {
				final int offsetY = buffer.getShort() & 0xFFFF;
				def.setOffsetY(offsetY);
			} else if (code == 72) {
				final int offsetZ = buffer.getShort() & 0xFFFF;
				def.setOffsetZ(offsetZ);
			} else if (code == 73) {
				def.setUninteractableSolid(true);
			} else if (code == 75) {
				buffer.get();
			} else {
				continue;
			}
		}
	}

}