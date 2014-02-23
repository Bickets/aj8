
package buttons;

import org.apollo.game.interact.ButtonEventHandler;
import org.apollo.game.model.Player;

@SuppressWarnings( "all" )
public class LogoutButton extends ButtonEventHandler
{

	public LogoutButton()
	{
		super( 2458 );
	}


	public void handle( final Player player )
	{
		player.logout();
	}
}
