package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;

/**
 * An event which manages single item actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ItemActionEvent implements Event {

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
	 * @param option The clicked interface option.
	 * @param interfaceId The interface id.
	 * @param id The items id.
	 * @param slot The items slot.
	 */
	public ItemActionEvent(InterfaceOption option, int interfaceId, int id, int slot) {
		this.option = option;
		this.interfaceId = interfaceId;
		this.id = id;
		this.slot = slot;
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