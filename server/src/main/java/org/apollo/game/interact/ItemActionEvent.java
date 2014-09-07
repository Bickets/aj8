package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.model.Player;

/**
 * An event which manages single item actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ItemActionEvent implements Event {

	/**
	 * The player to perform the item action for.
	 */
	private final Player player;

	/**
	 * The clicked interface option.
	 */
	private final InterfaceOption option;

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * The items id.
	 */
	private final int id;

	/**
	 * The items slot.
	 */
	private final int slot;

	/**
	 * Constructs a new {@link ItemActionEvent} with the specified player,
	 * interface id, item id and item slot.
	 *
	 * @param player The player performing this event.
	 * @param option The clicked interface option.
	 * @param interfaceId The interface id.
	 * @param id The items id.
	 * @param slot The items slot.
	 */
	public ItemActionEvent(Player player, InterfaceOption option, int interfaceId, int id, int slot) {
		this.player = player;
		this.option = option;
		this.interfaceId = interfaceId;
		this.id = id;
		this.slot = slot;
	}

	/**
	 * Returns the player performing this event.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the clicked interface option.
	 */
	public InterfaceOption getOption() {
		return option;
	}

	/**
	 * Returns the interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * Returns the item id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the items slot.
	 */
	public int getSlot() {
		return slot;
	}

}