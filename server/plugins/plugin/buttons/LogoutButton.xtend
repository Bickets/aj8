package plugin.buttons

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player

@SubscribesTo(ButtonActionEvent)
class LogoutButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(EventContext context, Player player, ButtonActionEvent event) {
		player.logout
	}

	override test(ButtonActionEvent event) {
		event.id == 2458
	}

}
