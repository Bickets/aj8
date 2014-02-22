
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.ChatEvent;
import org.apollo.game.model.Player;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * An event handler which broadcasts public chat messages.
 * @author Graham
 */
@HandlesEvent( ChatEvent.class )
public final class ChatEventHandler extends EventHandler<ChatEvent>
{

	@Override
	public void handle( Player player, ChatEvent event )
	{
		int color = event.getTextColor();
		int effects = event.getTextEffects();
		if( color < 0 || color > 11 || effects < 0 || effects > 5 ) {
			return;
		}

		player.getBlockSet().add( SynchronizationBlock.createChatBlock( player, event ) );
	}

}
