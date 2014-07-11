package objects

import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.InterfaceConstants.InterfaceOption
import org.apollo.game.model.inter.bank.BankAction

class BankObject implements EventSubscriber<ObjectActionEvent> {

	override subscribe(ObjectActionEvent event) {
		if (event.id != 2213) {
			return
		}

		if (event.option == InterfaceOption.OPTION_ONE) {
			event.player.startAction(new BankAction(event.player, event.position))
		}
	}

}
