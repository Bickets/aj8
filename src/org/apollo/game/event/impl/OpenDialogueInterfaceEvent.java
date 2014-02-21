
package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * This event is used to open an overlay over the chatbox.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class OpenDialogueInterfaceEvent extends Event
{

	/**
	 * Represents the id of the interface.
	 */
	private final int interfaceId;


	/**
	 * Constructs a new {@link OpenDialogueInterfaceEvent}.
	 * @param interfaceId The id of the interface.
	 */
	public OpenDialogueInterfaceEvent( int interfaceId )
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
