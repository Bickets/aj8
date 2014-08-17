package org.apollo.fs.parser;

import static org.apollo.game.model.obj.ObjectGroup.WALL;
import static org.apollo.game.model.obj.ObjectType.GROUND_PROP;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.def.MapDefinition;
import org.apollo.game.model.def.StaticObjectDefinition;
import org.apollo.game.model.obj.ObjectOrientation;
import org.apollo.game.model.obj.ObjectType;
import org.apollo.game.model.pf.TraversalMap;
import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A class which parses static object definitions, which include map tiles and
 * landscapes.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class StaticObjectDefinitionParser {

    /**
     * The map config which represents a clipped tile.
     */
    public static final int FLAG_CLIP = 0x1;

    /**
     * The map config which represents a bridged tile.
     */
    public static final int FLAG_BRIDGE = 0x2;

    /**
     * The traversal map.
     */
    private static final TraversalMap TRAVERSAL_MAP = TraversalMap.getInstance();

    /**
     * A multimap of static object definitions.
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
	Map<Integer, MapDefinition> defs = MapDefinitionParser.parse(fs);

	for (Entry<Integer, MapDefinition> entry : defs.entrySet()) {
	    MapDefinition def = entry.getValue();

	    int hash = def.getHash();
	    int x = (hash >> 8 & 0xFF) * 64;
	    int y = (hash & 0xFF) * 64;

	    byte[] landscapeData = fs.getFile(FileSystem.MAP_IDX, def.getLandscapeFile());
	    ByteBuffer landscapeBuffer = ByteBuffer.wrap(CompressionUtil.ungzip(landscapeData));
	    loadLandscapes(landscapeBuffer, x, y);

	    byte[] mapData = fs.getFile(FileSystem.MAP_IDX, def.getMapFile());
	    ByteBuffer mapBuffer = ByteBuffer.wrap(CompressionUtil.ungzip(mapData));
	    loadMaps(mapBuffer, x, y);
	}

	return definitions;
    }

    /**
     * Loads all of the landscapes and decodes the objects within them.
     *
     * @param landscapeBuffer The uncompressed landscape data buffer.
     * @param x The x coordinate of this landscape.
     * @param y The y coordinate of this landscape.
     */
    private static void loadLandscapes(ByteBuffer landscapeBuffer, int x, int y) {
	for (int deltaId, id = -1; (deltaId = ByteBufferUtil.readSmart(landscapeBuffer)) != 0;) {
	    id += deltaId;

	    for (int deltaPos, hash = 0; (deltaPos = ByteBufferUtil.readSmart(landscapeBuffer)) != 0;) {
		hash += deltaPos - 1;

		int localX = hash >> 6 & 0x3F;
		int localY = hash & 0x3F;
		int height = hash >> 12 & 0x3;

		int attributeHashCode = landscapeBuffer.get() & 0xFF;
		ObjectType type = ObjectType.forId(attributeHashCode >> 2);
		ObjectOrientation orientation = ObjectOrientation.forId(attributeHashCode & 0x3);
		Position position = new Position(x + localX, y + localY, height);

		objectDecoded(id, orientation, type, position);
	    }
	}
    }

    /**
     * Loads all of the map indexes entries and decodes each.
     *
     * @param mapBuffer The uncompressed map entry data buffer.
     * @param x The x coordinate of this map entry.
     * @param y The y coordinate of this map entry.
     */
    private static void loadMaps(ByteBuffer mapBuffer, int x, int y) {
	for (int height = 0; height < 4; height++) {
	    for (int localX = 0; localX < 64; localX++) {
		for (int localY = 0; localY < 64; localY++) {
		    Position position = new Position(x + localX, y + localY, height);

		    int flags = 0;
		    for (;;) {
			int config = mapBuffer.get() & 0xFF;
			if (config == 0) {
			    break;
			} else if (config == 1) {
			    mapBuffer.get();
			    break;
			} else if (config <= 49) {
			    mapBuffer.get();
			} else if (config <= 81) {
			    flags = config - 49;
			    mapDecoded(flags, position);
			}
		    }
		}
	    }
	}
    }

    /**
     * Marks traversal flags based on the map config received for some map file.
     *
     * @param flags The map configs.
     * @param position The position of the tile.
     */
    private static void mapDecoded(int flags, Position position) {
	int height = position.getHeight();

	if ((flags & FLAG_CLIP) == 1) {
	    if ((flags & FLAG_BRIDGE) == 2) {
		height--;
	    }
	    if (height >= 0 && height <= 3) {
		TRAVERSAL_MAP.markBlocked(height, position.getX(), position.getY());
	    }
	}
    }

    /**
     * Marks traversable objects based on their definitions.
     *
     * @param id The id of the object.
     * @param orientation The orientation of the object.
     * @param type The type of the object.
     * @param position The position of the object.
     */
    private static void objectDecoded(int id, ObjectOrientation orientation, ObjectType type, Position position) {
	GameObjectDefinition def = GameObjectDefinition.forId(id);

	if (def.isSolid()) {
	    if (!TRAVERSAL_MAP.regionInitialized(position.getX(), position.getY())) {
		TRAVERSAL_MAP.initializeRegion(position.getX(), position.getY());
	    }

	    if (type.getGroup() == WALL) {
		TRAVERSAL_MAP.markWall(orientation, position.getHeight(), position.getX(), position.getY(), type, def.isWalkable());
	    }

	    if (type == GROUND_PROP || type.getId() >= 9 && type.getId() <= 12) {
		TRAVERSAL_MAP.markBlocked(position.getHeight(), position.getX(), position.getY());
	    }
	}

	def.addPosition(position);
	definitions.put(id, new StaticObjectDefinition(id, position, type, orientation));
    }

}