
package org.apollo.game.interact;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public final class InteractionHandler
{

	private final ButtonEventDispatcher buttonDispatcher = new ButtonEventDispatcher();
	private final ObjectActionEventDispatcher objectDispatcher = new ObjectActionEventDispatcher();


	public void dispatch( Player player, int id )
	{
		buttonDispatcher.dispatch( player, id );
	}


	public void dispatch( int id, int action, Player player, Position position )
	{
		objectDispatcher.dispatch( id, action, player, position );
	}


	public void bind( ButtonEventHandler handler )
	{
		buttonDispatcher.bind( handler );
	}


	public void bind( ObjectActionEventHandler handler )
	{
		objectDispatcher.bind( handler );
	}

}
