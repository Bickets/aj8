package plugin.objects

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction
import plugin.bank.Bank

@SubscribesTo(ObjectActionEvent)
class BankObject implements EventSubscriber<ObjectActionEvent> {

	override subscribe(EventContext context, Player player, ObjectActionEvent event) {
		Bank.openBank(player)
	}

	override test(ObjectActionEvent event) {
		event.id == 2213 && event.action == InteractContextMenuAction.ACTION_ONE
	}

}
