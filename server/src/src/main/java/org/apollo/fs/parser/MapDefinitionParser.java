package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.MapDefinition;

/**
 * A class which parses {@link MapDefinition}s
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MapDefinitionParser {

	/**
	 * Parses {@link MapDefinition}s from the specified {@link FileSystem}.
	 *
	 * @param fs The file system.
	 * @return A {@link Map} of parsed map definitions.
	 * @throws IOException If some I/O error occurs.
	 */
	protected static Map<Integer, MapDefinition> parse(FileSystem fs) throws IOException {
		Archive archive = fs.getArchive(FileSystem.MANIFEST_ARCHIVE);
		ByteBuffer buffer = archive.getData("map_index");
		Map<Integer, MapDefinition> defs = new HashMap<>();

		int count = buffer.capacity() / 7;
		for (int i = 0; i < count; i++) {
			int hash = buffer.getShort() & 0xFFFF;
			int terrainFile = buffer.getShort() & 0xFFFF;
			int objectFile = buffer.getShort() & 0xFFFF;
			boolean preload = buffer.get() == 1;

			defs.put(hash, new MapDefinition(hash, terrainFile, objectFile, preload));
		}

		return defs;
	}

}