package org.apollo.game.msg.impl;

import org.apollo.game.model.Item;
import org.apollo.game.msg.Message;

/**
 * Represents a message which an item model is displayed on an interface.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class InterfaceItemModelMessage implements Message {

	/**
	 * The if of the interface which displays the item.
	 */
	private final int interfaceId;

	/**
	 * The item to display.
	 */
	private final Item item;

	/**
	 * The items model zoom.
	 */
	private final int zoom;

	/**
	 * Constructs a new {@link InterfaceItemModelMessage}.
	 *
	 * @param interfaceId The interface id.
	 * @param item The item.
	 * @param zoom The zoom.
	 */
	public InterfaceItemModelMessage(int interfaceId, Item item, int zoom) {
		this.interfaceId = interfaceId;
		this.item = item;
		this.zoom = zoom;
	}

	/**
	 * Returns the interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * Returns the item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Returns the items zoom.
	 */
	public int getZoom() {
		return zoom;
	}

}