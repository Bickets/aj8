package org.apollo.fs.parser;

import static org.apollo.game.model.obj.GameObjectGroup.WALL;
import static org.apollo.game.model.obj.GameObjectType.DIAGONAL_WALL;
import static org.apollo.game.model.obj.GameObjectType.GENERAL_PROP;
import static org.apollo.game.model.obj.GameObjectType.GROUND_PROP;
import static org.apollo.game.model.region.Tile.FLAG_BLOCKED;
import static org.apollo.game.model.region.Tile.FLAG_BRIDGE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.def.MapDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.obj.GameObjectGroup;
import org.apollo.game.model.obj.GameObjectOrientation;
import org.apollo.game.model.obj.GameObjectType;
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
	 * Parses the map definition files from the {@link FileSystem}.
	 *
	 * @param fs The file system.
	 * @return A {@link List} of parsed {@link GameObject}s
	 * @throws IOException If some I/O exception occurs.
	 */
	public List<GameObject> parse(FileSystem fs) throws IOException {
		Map<Integer, MapDefinition> defs = MapDefinitionParser.parse(fs);

		for (Entry<Integer, MapDefinition> entry : defs.entrySet()) {
			MapDefinition def = entry.getValue();

			int hash = def.getHash();
			int x = (hash >> 8 & 0xFF) * 64;
			int y = (hash & 0xFF) * 64;

			ByteBuffer gameObjectData = fs.getFile(FileSystem.MAP_INDEX, def.getObjectFile());
			ByteBuffer gameObjectBuffer = ByteBuffer.wrap(CompressionUtil.ungzip(gameObjectData.array()));
			parseGameObject(gameObjectBuffer, x, y);

			ByteBuffer terrainData = fs.getFile(FileSystem.MAP_INDEX, def.getTerrainFile());
			ByteBuffer terrainBuffer = ByteBuffer.wrap(CompressionUtil.ungzip(terrainData.array()));
			parseTerrain(terrainBuffer, x, y);
		}

		return gameObjects;
	}

	/**
	 * Parses a {@link GameObject} on the specified coordinates.
	 *
	 * @param gameObjectBuffer The uncompressed game object data buffer.
	 * @param x The x coordinate this object is on.
	 * @param y The y coordinate this object is on.
	 */
	private void parseGameObject(ByteBuffer gameObjectBuffer, int x, int y) {
		for (int deltaId, id = -1; (deltaId = ByteBufferUtil.readSmart(gameObjectBuffer)) != 0;) {
			id += deltaId;

			for (int deltaPos, hash = 0; (deltaPos = ByteBufferUtil.readSmart(gameObjectBuffer)) != 0;) {
				hash += deltaPos - 1;

				int localX = hash >> 6 & 0x3F;
				int localY = hash & 0x3F;
				int height = hash >> 12 & 0x3;

				int attributeHashCode = gameObjectBuffer.get() & 0xFF;
				Optional<GameObjectType> type = GameObjectType.valueOf(attributeHashCode >> 2);
				Optional<GameObjectOrientation> orientation = GameObjectOrientation.valueOf(attributeHashCode & 0x3);
				Position position = new Position(x + localX, y + localY, height);

				if (type.isPresent() && orientation.isPresent()) {
					gameObjectDecoded(id, orientation.get(), type.get(), position);
				}
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
	private void parseTerrain(ByteBuffer mapBuffer, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int localX = 0; localX < 64; localX++) {
				for (int localY = 0; localY < 64; localY++) {
					Position position = new Position(x + localX, y + localY, height);

					int flags = 0;
					for (;;) {
						int attributeId = mapBuffer.get() & 0xFF;
						if (attributeId == 0) {
							terrainDecoded(flags, position);
							break;
						} else if (attributeId == 1) {
							mapBuffer.get();
							terrainDecoded(flags, position);
							break;
						} else if (attributeId <= 49) {
							mapBuffer.get();
						} else if (attributeId <= 81) {
							flags = attributeId - 49;
						}
					}
				}
			}
		}
	}

	/**
	 * Decodes the terrains {@link Position}.
	 *
	 * @param flags The flags for the specified position.
	 * @param position The decoded position.
	 */
	private void terrainDecoded(int flags, Position position) {
		if ((flags & FLAG_BLOCKED) != 0) {
			world.getTraversalMap().markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		if ((flags & FLAG_BRIDGE) != 0) {
			world.getTraversalMap().markBridge(position.getHeight(), position.getX(), position.getY());
		}
	}

	/**
	 * Decodes a {@link GameObject} with the specified attributes on the
	 * specified {@link Position}.
	 *
	 * @param id The id of the game object.
	 * @param orientation The orientation of the game object.
	 * @param type The type of the game object.
	 * @param position The position the game object lies on.
	 */
	private void gameObjectDecoded(int id, GameObjectOrientation orientation, GameObjectType type, Position position) {
		TraversalMap traversalMap = world.getTraversalMap();
		GameObjectDefinition def = GameObjectDefinition.forId(id);
		Optional<GameObjectGroup> optionalGroup = type.getGroup();

		if (type == GROUND_PROP) {
			if (def.hasActions()) {
				traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
			}
		}

		if (type == GENERAL_PROP) {
			traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		if (type.getId() >= 12 && type != GROUND_PROP) {
			traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		if (optionalGroup.isPresent()) {
			GameObjectGroup group = optionalGroup.get();
			if (group == WALL) {
				traversalMap.markWall(orientation, position.getHeight(), position.getX(), position.getY(), type, def.isImpenetrable());
			}
		}

		if (type == DIAGONAL_WALL) {
			traversalMap.markBlocked(position.getHeight(), position.getX(), position.getY());
		}

		gameObjects.add(new GameObject(id, position, world, type, orientation));
	}

}