package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * This class defines the {@link Event} that is executed when a
 * {@code ItemOnPlayerMessage} occurs.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public final class ItemOnPlayerActionEvent implements Event {

	/**
	 * The other player.
	 */
	private final Player other;

	/**
	 * The item.
	 */
	private final Item item;

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * Constructs a new instance of this event.
	 *
	 * @param other The other player.
	 * @param item The item.
	 * @param interfaceId The interface id.
	 */
	public ItemOnPlayerActionEvent(Player other, Item item, int interfaceId) {
		this.other = other;
		this.item = item;
		this.interfaceId = interfaceId;
	}

	/**
	 * Gets the other player.
	 *
	 * @return The other player.
	 */
	public Player getOther() {
		return other;
	}

	/**
	 * Gets the item.
	 *
	 * @return The item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Gets the interface id.
	 *
	 * @return The interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}