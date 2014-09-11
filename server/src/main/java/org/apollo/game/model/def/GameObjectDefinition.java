package org.apollo.game.model.def;

import org.apollo.game.model.Position;

/**
 * Represents the definition of some game object.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Steve
 */
public final class GameObjectDefinition {

	/**
	 * The game object definitions.
	 */
	private static GameObjectDefinition[] definitions;

	/**
	 * Gets the total number of objects.
	 *
	 * @return The total number of objects.
	 */
	public static int count() {
		return definitions.length;
	}

	/**
	 * Gets the object definition for the specified id.
	 *
	 * @param id The id.
	 * @return The definition.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	public static GameObjectDefinition forId(int id) {
		if (id < 0 || id >= definitions.length) {
			throw new IndexOutOfBoundsException();
		}
		return definitions[id];
	}

	/**
	 * Initializes the class with the specified set of definitions.
	 *
	 * @param definitions The definitions.
	 * @throws RuntimeException if there is an id mismatch.
	 */
	public static void init(GameObjectDefinition[] definitions) {
		GameObjectDefinition.definitions = definitions;
		for (int id = 0; id < definitions.length; id++) {
			GameObjectDefinition def = definitions[id];
			if (def.getId() != id) {
				throw new RuntimeException("Object definition id mismatch");
			}
		}
	}

	/**
	 * The object id.
	 */
	private final int id;

	/**
	 * The map scene id.
	 */
	private int mapSceneId = 0;

	/**
	 * The scale x.
	 */
	private int scaleX = 0;

	/**
	 * The scale y.
	 */
	private int scaleY = 0;

	/**
	 * The scale z.
	 */
	private int scaleZ = 0;

	/**
	 * The offset x.
	 */
	private int offsetX = 0;

	/**
	 * The offset y.
	 */
	private int offsetY = 0;

	/**
	 * The offset z.
	 */
	private int offsetZ = 0;

	/**
	 * The horizontal size.
	 */
	private int sizeX;

	/**
	 * The vertical size.
	 */
	private int sizeY;

	/**
	 * The 'solid object' flag, {@code false} by default.
	 */
	private boolean solid = false;

	/**
	 * The 'walkable' flag, {@code false} by default.
	 */
	private boolean walkable = false;

	/**
	 * Denotes some un-interactable solid ground decoration.
	 */
	private boolean uninteractableSolid = false;

	/**
	 * Flag for if the object is intractable.
	 */
	private boolean interactable;

	/**
	 * The name.
	 */
	private String name = "";

	/**
	 * The description.
	 */
	private String description = "";

	/**
	 * The actions.
	 */
	private final String[] actions = new String[10];

	/**
	 * The actions flag.
	 */
	private boolean hasActions = false;

	/**
	 * Creates a new object definition.
	 *
	 * @param id The object id.
	 */
	public GameObjectDefinition(int id) {
		this.id = id;
	}

	/**
	 * Adds a action to the actions array.
	 *
	 * @param code The position to add the action.
	 * @param action The action to add.
	 */
	public void addAction(int code, String action) {
		if (!action.equalsIgnoreCase("hidden")) {
			actions[code] = action;
			if (!action.contains("Examine")) {
				hasActions = true;
			}
		}
	}

	/**
	 * Gets the actions.
	 *
	 * @return The actions.
	 */
	public String[] getActions() {
		return actions;
	}

	/**
	 * Gets the description.
	 *
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the object id.
	 *
	 * @return The object id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the map scene id.
	 *
	 * @return The map scene id.
	 */
	public int getMapSceneId() {
		return mapSceneId;
	}

	/**
	 * Gets the name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the offset.
	 *
	 * @return The offset.
	 */
	public Position getOffset() {
		return new Position(offsetX, offsetY, offsetZ);
	}

	/**
	 * Gets the scale.
	 *
	 * @return The scale.
	 */
	public Position getScale() {
		return new Position(scaleX, scaleY, scaleZ);
	}

	/**
	 * Gets the object size, in tiles.
	 *
	 * @return The size.
	 */
	public int getSize() {
		return sizeX + sizeY;
	}

	/**
	 * Gets the object's horizontal X size.
	 *
	 * @return The X size.
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * Gets the object's horizontal Y size.
	 *
	 * @return The Y size.
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * Gets the actions flag.
	 *
	 * @return The actions flag.
	 */
	public boolean hasActions() {
		return hasActions;
	}

	/**
	 * Gets the object's intractability flag.
	 *
	 * @return {@code true} if the object has actions, {@code false} otherwise.
	 */
	public boolean isInteractable() {
		return interactable || hasActions;
	}

	/**
	 * Gets the object's solid flag.
	 *
	 * @return The solid flag.
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Gets the object's walkable flag.
	 *
	 * @return The walkable flag.
	 */
	public boolean isWalkable() {
		return walkable;
	}

	/**
	 * Returns whether or not this object is an un-interactable solid.
	 */
	public boolean isUninteractableSolid() {
		return uninteractableSolid;
	}

	/**
	 * Sets the description.
	 *
	 * @param description The description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the object's intractability flag.
	 *
	 * @param interactable The intractability flag.
	 */
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	/**
	 * Sets the map scene id.
	 *
	 * @param mapSceneId The map scene id.
	 */
	public void setMapSceneId(int mapSceneId) {
		this.mapSceneId = mapSceneId;
	}

	/**
	 * Sets the name.
	 *
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the offset x.
	 *
	 * @param offsetX The offset x.
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * Sets the offset y.
	 *
	 * @param offsetY The offset y.
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * Sets the offset z.
	 *
	 * @param offsetZ The offset z.
	 */
	public void setOffsetZ(int offsetZ) {
		this.offsetZ = offsetZ;
	}

	/**
	 * Sets the scale x.
	 *
	 * @param scaleX The scale x.
	 */
	public void setScaleX(int scaleX) {
		this.scaleX = scaleX;
	}

	/**
	 * Sets the scale y.
	 *
	 * @param scaleY The scale y.
	 */
	public void setScaleY(int scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * Sets the scale z.
	 *
	 * @param scaleZ The scale z.
	 */
	public void setScaleZ(int scaleZ) {
		this.scaleZ = scaleZ;
	}

	/**
	 * Sets the horizontal X size.
	 *
	 * @param sizeX The horizontal size to be set.
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	/**
	 * Sets the vertical Y size.
	 *
	 * @param sizeY The vertical size to be set.
	 */
	public void setSizeY(int sizeY) {
		sizeX = sizeY;
	}

	/**
	 * Sets the object's solid flag.
	 *
	 * @param solid The solid flag.
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * Sets the object's walkable flag.
	 *
	 * @param walkable The walkable flag.
	 */
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	/**
	 * Sets whether or not this object is an un-interactable solid.
	 */
	public void setUninteractableSolid(boolean uninteractableSolid) {
		this.uninteractableSolid = uninteractableSolid;
	}

}