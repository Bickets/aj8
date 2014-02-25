package objects

import org.apollo.game.interact.ObjectActionEventHandler
import org.apollo.game.model.Player
import org.apollo.game.model.Position
import org.apollo.game.model.inter.bank.BankAction

class BankObject extends ObjectActionEventHandler {

	new() {
		super(2213)
	}

	override handle(int id, int option, Player player, Position position) {
		switch option {
			case 2: player.startAction(new BankAction(player, position))
		}
	}

}
