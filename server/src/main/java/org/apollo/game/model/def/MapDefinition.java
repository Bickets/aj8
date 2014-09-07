package org.apollo.game.model.def;

/**
 * Represents a single map definition.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MapDefinition {

	/**
	 * The hash of the region coordinates within this map entry.
	 */
	private final int hash;

	/**
	 * The map file id in this map entry.
	 */
	private final int mapFile;

	/**
	 * The landscape file id in this map entry.
	 */
	private final int landscapeFile;

	/**
	 * Represents whether or not this map is preloaded.
	 */
	private final boolean preload;

	/**
	 * Constructs a new {@link MapDefinition} with the specified hash, map file
	 * id, landscape file id, and preload state.
	 *
	 * @param hash The hash of the region coordinates within this map entry.
	 * @param mapFile The map file id in this map entry.
	 * @param scapeFile The landscape file id in this map entry.
	 * @param preload Represents whether or not this map is preloaded.
	 */
	public MapDefinition(int hash, int mapFile, int landscapeFile, boolean preload) {
		this.hash = hash;
		this.mapFile = mapFile;
		this.landscapeFile = landscapeFile;
		this.preload = preload;
	}

	/**
	 * Returns this map entries hash.
	 */
	public int getHash() {
		return hash;
	}

	/**
	 * Returns this map entries map id.
	 */
	public int getMapFile() {
		return mapFile;
	}

	/**
	 * Returns this map entries landscape id.
	 */
	public int getLandscapeFile() {
		return landscapeFile;
	}

	/**
	 * Returns whether or not this map entry is preloaded.
	 */
	public boolean isPreload() {
		return preload;
	}

}