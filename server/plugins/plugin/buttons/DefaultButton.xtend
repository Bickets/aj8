package plugin.buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player.PrivilegeLevel

@SubscribesTo(ButtonActionEvent)
class DefaultButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(ButtonActionEvent event) {
		val plr = event.player
		if (plr.privilegeLevel == PrivilegeLevel.ADMINISTRATOR) {
			plr.sendMessage("Button clicked: " + event.id)
		}
	}

}
