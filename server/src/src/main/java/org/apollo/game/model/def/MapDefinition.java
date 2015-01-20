package org.apollo.game.model.def;

/**
 * Represents a single map definition.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MapDefinition {

	/**
	 * The hash of the region coordinates.
	 */
	private final int hash;

	/**
	 * The terrain file id.
	 */
	private final int terrainFile;

	/**
	 * The object file id.
	 */
	private final int objectFile;

	/**
	 * Whether or not this map is preloaded.
	 */
	private final boolean preload;

	/**
	 * Constructs a new {@link MapDefinition} with the specified hash, terrain
	 * file id, object file id and preload state.
	 *
	 * @param hash The hash of the region coordinates.
	 * @param terrainFile The terrain file id.
	 * @param objectFile The object file id.
	 * @param preload Whether or not this map is preloaded.
	 */
	public MapDefinition(int hash, int terrainFile, int objectFile, boolean preload) {
		this.hash = hash;
		this.terrainFile = terrainFile;
		this.objectFile = objectFile;
		this.preload = preload;
	}

	/**
	 * Returns the coordinate hash.
	 */
	public int getHash() {
		return hash;
	}

	/**
	 * Returns the terrain file id.
	 */
	public int getTerrainFile() {
		return terrainFile;
	}

	/**
	 * Returns the object file id.
	 */
	public int getObjectFile() {
		return objectFile;
	}

	/**
	 * Returns whether or not this map is preloaded.
	 */
	public boolean isPreload() {
		return preload;
	}

}