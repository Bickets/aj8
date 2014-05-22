package buttons

import org.apollo.game.interact.ButtonClickListener
import org.apollo.game.model.Player

class WithdrawAsItemButton extends ButtonClickListener {

	new() {
		super(5387)
	}
	
	override handle(int id, Player player) {
		player.fields.withdrawingNotes = false
	}

}
