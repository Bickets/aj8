package org.apollo.fs.parser;

import static org.apollo.game.model.obj.ObjectGroup.WALL;
import static org.apollo.game.model.obj.ObjectType.DIAGONAL_WALL;
import static org.apollo.game.model.obj.ObjectType.GENERAL_PROP;
import static org.apollo.game.model.obj.ObjectType.GROUND_PROP;
import static org.apollo.game.model.obj.ObjectType.WALKABLE_PROP;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.def.MapDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.obj.ObjectOrientation;
import org.apollo.game.model.obj.ObjectType;
import org.apollo.game.model.pf.TraversalMap;
import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;

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
	 * A set of game objects
	 */
	private final List<GameObject> gameObjects = new ArrayList<>();

	/**
	 * The world.
	 */
	private final World world;

	/**
	 * Constructs a new {@link StaticObjectDefinition} with the specified world.
	 *
	 * @param world The world.
	 */
	public StaticObjectDefinitionParser(World world) {
		this.world = world;
	}

	/**
	 * Parses the landscape files from the specified file system.
	 *
	 * @param fs The file system.
	 * @return A multimap of static object definitions.
	 * @throws IOException If some I/O exception occurs.
	 */
	public List<GameObject> parse(FileSystem fs) throws IOException {
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

		return gameObjects;
	}

	/**
	 * Loads all of the landscapes and decodes the objects within them.
	 *
	 * @param landscapeBuffer The uncompressed landscape data buffer.
	 * @param x The x coordinate of this landscape.
	 * @param y The y coordinate of this landscape.
	 */
	private void loadLandscapes(ByteBuffer landscapeBuffer, int x, int y) {
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
	private void loadMaps(ByteBuffer mapBuffer, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int localX = 0; localX < 64; localX++) {
				for (int localY = 0; localY < 64; localY++) {
					Position position = new Position(x + localX, y + localY, height);

					int flags = 0;
					for (;;) {
						int config = mapBuffer.get() & 0xFF;
						if (config == 0) {
							mapDecoded(flags, position);
							break;
						} else if (config == 1) {
							mapBuffer.get();
							mapDecoded(flags, position);
							break;
						} else if (config <= 49) {
							mapBuffer.get();
						} else if (config <= 81) {
							flags = config - 49;
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
	private void mapDecoded(int flags, Position position) {
		if ((flags & FLAG_CLIP) != 0) {
			world.getTraversalMap().markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		if ((flags & FLAG_BRIDGE) != 0) {
			world.getTraversalMap().markBridge(position.getHeight(), position.getX(), position.getY());
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
	private void objectDecoded(int id, ObjectOrientation orientation, ObjectType type, Position position) {
		TraversalMap traversalMap = world.getTraversalMap();
		GameObjectDefinition def = GameObjectDefinition.forId(id);

		if (type == GROUND_PROP) {
			if (def.hasActions()) {
				traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
			}
		} else if (type == GENERAL_PROP || type == WALKABLE_PROP) {
			if (def.getSize() > 0 || !def.isSolid()) {
				traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
			}
		} else if (type.getId() >= 12) {
			traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
		} else if (type.getGroup() == WALL) {
			traversalMap.markWall(orientation, position.getHeight(), position.getX(), position.getY(), type, def.isWalkable());
		} else if (type == DIAGONAL_WALL) {
			traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		gameObjects.add(new GameObject(id, position, world, type, orientation));
	}

}