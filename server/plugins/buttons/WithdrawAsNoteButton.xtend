package buttons

import org.apollo.game.interact.ButtonEventHandler
import org.apollo.game.model.Player

class WithdrawFromBankButton extends ButtonEventHandler {

	new() {
		super(5386)
	}

	override handle(Player player) {
		player.fields.withdrawingNotes = true
	}

}
