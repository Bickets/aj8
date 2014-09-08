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
	 * The player.
	 */
	private Player player;

	/**
	 * The other player.
	 */
	private Player otherPlayer;

	/**
	 * The item.
	 */
	private Item item;

	/**
	 * The interface id.
	 */
	private int interfaceId;

	/**
	 * Constructs a new instance of this event.
	 * 
	 * @param player The player.
	 * @param otherPlayer The other player.
	 * @param item The item.
	 * @param interfaceId The interface id.
	 */
	public ItemOnPlayerActionEvent(Player player, Player otherPlayer, Item item, int interfaceId) {
		this.player = player;
		this.otherPlayer = otherPlayer;
		this.item = item;
		this.interfaceId = interfaceId;
	}

	/**
	 * Gets the player.
	 * 
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the other player.
	 * 
	 * @return The other player.
	 */
	public Player getOtherPlayer() {
		return otherPlayer;
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