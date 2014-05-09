package buttons

import org.apollo.game.model.Player
import org.apollo.game.interact.ButtonClickListener

class WithdrawAsItemButton extends ButtonClickListener {

	new() {
		super(5387)
	}
	
	override handle(int id, Player player) {
		player.fields.withdrawingNotes = false
	}

}
