
package buttons;

import org.apollo.game.interact.ButtonEventHandler;
import org.apollo.game.model.GameCharacterFields;
import org.apollo.game.model.Player;

@SuppressWarnings( "all" )
public class WithdrawFromBankButton extends ButtonEventHandler
{

	public WithdrawFromBankButton()
	{
		super( 5386 );
	}


	public void handle( final Player player )
	{
		GameCharacterFields _fields = player.getFields();
		_fields.setWithdrawingNotes( true );
	}
}
