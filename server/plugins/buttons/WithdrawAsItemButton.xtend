package buttons

import org.apollo.game.interact.ButtonEventHandler
import org.apollo.game.model.Player

class WithdrawAsItemButton extends ButtonEventHandler {

	new() {
		super(5387)
	}
	
	override handle(Player player) {
		player.fields.withdrawingNotes = false
	}

}
