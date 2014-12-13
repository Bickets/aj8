package org.apollo.game.interact;

import org.apollo.game.event.Event;

/**
 * An event which manages button actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ButtonActionEvent implements Event {

	/**
	 * The id of the button.
	 */
	private final int id;

	/**
	 * Constructs a new {@link ButtonActionEvent} with the specified button id.
	 *
	 * @param id The buttons id.
	 */
	public ButtonActionEvent(int id) {
		this.id = id;
	}

	/**
	 * Returns the buttons id.
	 */
	public int getId() {
		return id;
	}

}