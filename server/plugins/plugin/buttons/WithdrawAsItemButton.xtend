package plugin.buttons

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.bank.BankConstants

@SubscribesTo(ButtonActionEvent)
class WithdrawAsItemButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(EventContext context, Player player, ButtonActionEvent event) {
		if (!player.interfaceSet.contains(BankConstants.BANK_WINDOW_ID, BankConstants.SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		player.fields.withdrawingNotes = false
	}

	override test(ButtonActionEvent event) {
		event.id == 5387
	}

}
