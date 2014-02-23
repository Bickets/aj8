
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.ButtonEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} which responds to {@link ButtonEvent}s for
 * withdrawing items as notes.
 * @author Graham
 */
@HandlesEvent( ButtonEvent.class )
public final class ButtonEventHandler extends EventHandler<ButtonEvent>
{

	private final World world;


	public ButtonEventHandler( World world )
	{
		this.world = world;
	}


	@Override
	public void handle( Player player, ButtonEvent event )
	{
		world.getInteractionHandler().dispatch( player, event.getInterfaceId() );
	}

}
