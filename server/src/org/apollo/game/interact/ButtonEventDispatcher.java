
package org.apollo.game.interact;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;

public final class ButtonEventDispatcher
{

	private final Map<Integer, ButtonEventHandler> handlers = new HashMap<>();


	public ButtonEventDispatcher()
	{

	}


	public void bind( ButtonEventHandler handler )
	{
		handlers.put( handler.getId(), handler );
	}


	public void unbind( ButtonEventHandler handler )
	{
		handlers.remove( handler.getId() );
	}


	public void dispatch( Player player, int id )
	{
		ButtonEventHandler handler = handlers.get( id );
		if( handler != null ) {
			handler.handle( player );
		}
	}

}
