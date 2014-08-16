package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.MapDefinition;

/**
 * Represents the map index manifest archive entry.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MapDefinitionParser {

    /**
     * Parses and returns a map of map definitions from the specified file
     * system.
     *
     * @param fs The file system in which to read the map data from.
     * @throws IOException If some I/O exception occurs.
     * @return A map of parsed map definitions.
     */
    protected static Map<Integer, MapDefinition> parse(FileSystem fs) throws IOException {
	Archive archive = fs.getArchive(FileSystem.MANIFEST_ARCHIVE);
	ByteBuffer buffer = ByteBuffer.wrap(archive.get("map_index"));
	Map<Integer, MapDefinition> defs = new HashMap<>();

	int count = buffer.capacity() / 7;
	for (int i = 0; i < count; i++) {
	    int hash = buffer.getShort();
	    int mapFile = buffer.getShort() & 0xFFFF;
	    int landscapeFile = buffer.getShort() & 0xFFFF;
	    boolean preload = buffer.get() == 1;

	    defs.put(hash, new MapDefinition(hash, mapFile, landscapeFile, preload));
	}

	return defs;
    }

}