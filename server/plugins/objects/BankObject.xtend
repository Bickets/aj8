package objects

import org.apollo.game.interact.ObjectActionListener
import org.apollo.game.model.InterfaceConstants.InterfaceOption
import org.apollo.game.model.Player
import org.apollo.game.model.Position
import org.apollo.game.model.inter.bank.BankAction

class BankObject extends ObjectActionListener {

	new() {
		super(2213)
	}

	override handle(int id, InterfaceOption option, Player player, Position position) {
		switch option {
			case OPTION_ONE:
				player.startAction(new BankAction(player, position))
			default:
				throw new UnsupportedOperationException('Unhandled bank option: ' + option)
		}
	}

}
