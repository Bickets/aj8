
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.ObjectActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankAction;

/**
 * Handles an option event for an object.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ObjectEventHandler extends EventHandler<ObjectActionEvent>
{

	/**
	 * The object id of a bank booth.
	 */
	private static final int BANK_BOOTH_ID = 2213;


	@Override
	public void handle( Player player, ObjectActionEvent event )
	{
		if( event.getOption() == 2 && event.getId() == BANK_BOOTH_ID ) {
			player.startAction( new BankAction( player, event.getPosition() ) );
		}
	}

}
