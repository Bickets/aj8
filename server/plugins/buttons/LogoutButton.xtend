package buttons

import org.apollo.game.interact.ButtonEventHandler
import org.apollo.game.model.Player

class LogoutButton extends ButtonEventHandler {

	new() {
		super(2458)
	}

	override handle(Player player) {
		player.logout
	}

}
