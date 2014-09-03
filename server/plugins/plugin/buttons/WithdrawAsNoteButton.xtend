package plugin.buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent

@SubscribesTo(ButtonActionEvent)
class WithdrawFromBankButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(ButtonActionEvent event) {
		event.player.fields.withdrawingNotes = true
	}

	override test(ButtonActionEvent event) {
		event.id == 5386
	}

}
