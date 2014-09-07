package org.apollo.game.msg.impl;

import org.apollo.game.model.obj.GameObject;
import org.apollo.game.msg.Message;

/**
 * A message related to a {@link GameObject} which could be addition or removal.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameObjectMessage implements Message {

	/**
	 * The game object.
	 */
	private final GameObject object;

	/**
	 * The objects offset.
	 */
	private final int positionOffset;

	/**
	 * Constructs a new {@link GameObjectMessage} with the specified game
	 * object.
	 * 
	 * @param positionOffset The objects offset.
	 * @param object The game object.
	 */
	public GameObjectMessage(GameObject object, int positionOffset) {
		this.object = object;
		this.positionOffset = positionOffset;
	}

	/**
	 * Returns this messages game object.
	 */
	public GameObject getObject() {
		return object;
	}

	/**
	 * Returns the offset of this object.
	 */
	public int getPositionOffset() {
		return positionOffset;
	}

}