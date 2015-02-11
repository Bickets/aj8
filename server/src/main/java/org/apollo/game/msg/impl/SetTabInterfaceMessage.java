package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent to the client to set the interface of a tab.
 *
 * @author Graham
 */
public final class SetTabInterfaceMessage implements Message {

	/**
	 * The tab id.
	 */
	private final int tab;

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * Constructs a new {@link SetTabInterfaceMessage}.
	 *
	 * @param tab The tab id.
	 * @param interfaceId The interface id.
	 */
	public SetTabInterfaceMessage(int tab, int interfaceId) {
		this.tab = tab;
		this.interfaceId = interfaceId;
	}

	/**
	 * Gets the tab id.
	 *
	 * @return The tab id.
	 */
	public int getTabId() {
		return tab;
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