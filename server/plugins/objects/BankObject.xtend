package objects

import org.apollo.game.model.Player
import org.apollo.game.model.Position
import org.apollo.game.model.inter.bank.BankAction
import org.apollo.game.interact.ObjectActionListener

class BankObject extends ObjectActionListener {

	new() {
		super(2213)
	}

	override handle(int id, int option, Player player, Position position) {
		switch option {
			case 1: player.startAction(new BankAction(player, position))
		}
	}

}
