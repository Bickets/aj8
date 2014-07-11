package buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ButtonActionEvent

class LogoutButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(ButtonActionEvent event) {
		println(event.getId)
		if (event.getId == 2458) {
			event.getPlayer.logout
		}
	}

}