package buttons

import org.apollo.game.model.Player
import org.apollo.game.interact.ButtonClickListener

class WithdrawFromBankButton extends ButtonClickListener {

	new() {
		super(5386)
	}

	override handle(int id, Player player) {
		player.fields.withdrawingNotes = true
	}

}
