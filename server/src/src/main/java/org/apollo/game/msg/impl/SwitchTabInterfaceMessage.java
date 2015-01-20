package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent to the client to change the interface of a tab.
 *
 * @author Graham
 */
public final class SwitchTabInterfaceMessage implements Message {

	/**
	 * The tab id.
	 */
	private final int tab;

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * Creates the switch interface message.
	 *
	 * @param tab The tab id.
	 * @param interfaceId The interface id.
	 */
	public SwitchTabInterfaceMessage(int tab, int interfaceId) {
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