package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import org.apollo.fs.FileSystem;
import org.apollo.fs.Manifest;
import org.apollo.fs.MapEntry;
import org.apollo.fs.MapIndex;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.StaticObjectDefinition;
import org.apollo.game.model.obj.ObjectOrientation;
import org.apollo.game.model.obj.ObjectType;
import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A class which parses static object defintions.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class StaticObjectDefinitionParser {

    /**
     * A multimap of entries.
     */
    private static final Multimap<Integer, StaticObjectDefinition> definitions = ArrayListMultimap.create();

    /**
     * Parses the landscape files from the specified file system.
     * 
     * @param fs The file system.
     * @return A multimap of static object definitions.
     * @throws IOException If some I/O exception occurs.
     */
    public static Multimap<Integer, StaticObjectDefinition> parse(FileSystem fs) throws IOException {
	Manifest manifest = fs.getManifest();
	MapIndex index = manifest.getMapIndex();

	for (Entry<Integer, MapEntry> entry : index.getEntries().entrySet()) {
	    int hashCode = entry.getKey();

	    byte[] compressed = fs.getFile(FileSystem.MAP_IDX, entry.getValue().getScapeFile());
	    ByteBuffer buffer = ByteBuffer.wrap(CompressionUtil.ungzip(compressed));

	    int x = ((hashCode >> 8) & 0xFF) * 64;
	    int y = (hashCode & 0xFF) * 64;

	    int id = -1;
	    while (true) {
		int deltaId = ByteBufferUtil.readSmart(buffer);
		if (deltaId == 0) {
		    break;
		}

		id += deltaId;

		int positionHashCode = 0;
		while (true) {
		    int deltaPos = ByteBufferUtil.readSmart(buffer);
		    if (deltaPos == 0) {
			break;
		    }

		    positionHashCode += deltaPos - 1;

		    int localX = (positionHashCode >> 6) & 0x3F;
		    int localY = positionHashCode & 0x3F;
		    int height = (positionHashCode >> 12) & 0x3;

		    int attributeHashCode = buffer.get() & 0xFF;
		    int type = attributeHashCode >> 2;
		    int orientation = attributeHashCode & 0x3;

		    definitions.put(id, new StaticObjectDefinition(id, new Position(localX + x, localY + y, height), ObjectType.forId(type), ObjectOrientation.forId(orientation)));
		}
	    }
	}

	return definitions;
    }

}