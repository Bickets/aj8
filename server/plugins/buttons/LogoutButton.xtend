package buttons

import org.apollo.game.model.Player
import org.apollo.game.interact.ButtonClickListener

class LogoutButton extends ButtonClickListener {

	new() {
		super(2458)
	}

	override handle(int id, Player player) {
		player.logout
	}

}
