package objects

import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.inter.bank.BankAction
import org.apollo.game.common.Interfaces.InterfaceOption

class BankObject implements EventSubscriber<ObjectActionEvent> {

	override subscribe(ObjectActionEvent event) {
		event.player.startAction(new BankAction(event.player, event.position))
	}

	override test(ObjectActionEvent event) {
		event.id == 2213 && event.option == InterfaceOption.OPTION_ONE
	}

}
