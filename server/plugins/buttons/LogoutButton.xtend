package buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent

@SubscribesTo(ButtonActionEvent)
class LogoutButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(ButtonActionEvent event) {
		event.player.logout
	}

	override test(ButtonActionEvent event) {
		event.id == 2458
	}

}
