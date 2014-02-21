
package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * This event is used to open an overlay over the chatbox.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * TODO: BETTER NAMING?
 */
public final class OpenChatboxOverlayEvent extends Event
{

	/**
	 * Represents the id of the interface.
	 */
	private final int interfaceId;


	/**
	 * Constructs a new {@link OpenChatboxOverlayEvent}.
	 * @param interfaceId The id of the interface.
	 */
	public OpenChatboxOverlayEvent( int interfaceId )
	{
		this.interfaceId = interfaceId;
	}


	/**
	 * Returns the interface id.
	 */
	public int getInterfaceId()
	{
		return interfaceId;
	}

}
