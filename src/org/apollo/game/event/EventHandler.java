
package org.apollo.game.event;

import org.apollo.game.model.Player;

/**
 * A class which handles events.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of event this class handles.
 */
public abstract class EventHandler<E extends Event>
{

	/**
	 * Handles an event.
	 * @param player The player.
	 * @param event The event.
	 */
	public abstract void handle( Player player, E event );

}
