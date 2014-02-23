
package org.apollo.game.interact;

import org.apollo.game.model.Player;

public final class InteractionHandler
{

	private final ButtonEventDispatcher buttonDispatcher = new ButtonEventDispatcher();


	public void dispatch( Player player, int id )
	{
		buttonDispatcher.dispatch( player, id );
	}


	public void bind( ButtonEventHandler handler )
	{
		buttonDispatcher.bind( handler );
	}

}
