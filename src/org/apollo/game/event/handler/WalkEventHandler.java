
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.WalkEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;

/**
 * A handler for the {@link WalkEvent}.
 * @author Graham
 */
@HandlesEvent( WalkEvent.class )
public final class WalkEventHandler extends EventHandler<WalkEvent>
{

	@Override
	public void handle( Player player, WalkEvent event )
	{
		WalkingQueue queue = player.getWalkingQueue();

		Position[] steps = event.getSteps();
		for( int index = 0; index < steps.length; index ++ ) {
			Position step = steps[ index ];
			if( index == 0 ) {
				if( ! queue.addFirstStep( step ) ) {
					return; /* ignore packet */
				}
			} else {
				queue.addStep( step );
			}
		}

		queue.setRunningQueue( event.isRunning() );

		if( queue.size() > 0 ) {
			player.stopAction();
			player.getInterfaceSet().close();
		}
	}

}
