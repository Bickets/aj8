
package org.apollo.game.model.inter.bank;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

/**
 * An action which handles the clicking of a bank booth.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class BankAction extends DistancedAction<Player>
{

	/**
	 * The size of a bank booth, in tiles.
	 */
	public static final int BANK_BOOTH_SIZE = 1;

	/**
	 * The position of the bank booth.
	 */
	private final Position position;


	/**
	 * Constructs a new {@link BankAction}.
	 * @param player The player who is performing this bank action.
	 * @param position The position of the bank booth.
	 */
	public BankAction( Player player, Position position )
	{
		super( 0, true, player, position, BANK_BOOTH_SIZE );
		this.position = position;
	}


	@Override
	public void executeAction()
	{
		getCharacter().turnTo( position );
		BankUtils.openBank( getCharacter() );
		stop();
	}

}
