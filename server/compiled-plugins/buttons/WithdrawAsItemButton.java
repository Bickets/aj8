
package buttons;

import org.apollo.game.interact.ButtonEventHandler;
import org.apollo.game.model.GameCharacterFields;
import org.apollo.game.model.Player;

@SuppressWarnings( "all" )
public class WithdrawAsItemButton extends ButtonEventHandler
{

	public WithdrawAsItemButton()
	{
		super( 5387 );
	}


	public void handle( final Player player )
	{
		GameCharacterFields _fields = player.getFields();
		_fields.setWithdrawingNotes( false );
	}
}
