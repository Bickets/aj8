package objects

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.inter.bank.BankAction

@SubscribesTo(ObjectActionEvent)
class BankObject implements EventSubscriber<ObjectActionEvent> {

	override subscribe(ObjectActionEvent event) {
		event.player.startAction(new BankAction(event.player, event.position))
	}

	override test(ObjectActionEvent event) {
		event.id == 2213 && event.option == InterfaceOption.OPTION_ONE
	}

}
