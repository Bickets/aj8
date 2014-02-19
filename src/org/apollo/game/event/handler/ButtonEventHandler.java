
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.ButtonEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} which responds to {@link ButtonEvent}s for
 * withdrawing items as notes.
 * @author Graham
 */
public final class ButtonEventHandler extends EventHandler<ButtonEvent>
{

	/**
	 * The withdraw as item button id.
	 */
	private static final int WITHDRAW_AS_ITEM = 5387;

	/**
	 * The withdraw as note button id.
	 */
	private static final int WITHDRAW_AS_NOTE = 5386;

	/**
	 * The logout button interface
	 */
	private static final int LOGOUT = 2458;


	@Override
	public void handle( Player player, ButtonEvent event )
	{
		if( event.getInterfaceId() == WITHDRAW_AS_ITEM ) {
			player.getFields().setWithdrawingNotes( false );
		} else if( event.getInterfaceId() == WITHDRAW_AS_NOTE ) {
			player.getFields().setWithdrawingNotes( true );
		} else if( event.getInterfaceId() == LOGOUT ) {
			player.logout();
		}
	}

}
