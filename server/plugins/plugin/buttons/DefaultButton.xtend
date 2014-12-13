package plugin.buttons

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.Player.PrivilegeLevel

@SubscribesTo(ButtonActionEvent)
class DefaultButton implements EventSubscriber<ButtonActionEvent> {

	override subscribe(EventContext context, Player player, ButtonActionEvent event) {
		if (player.privilegeLevel == PrivilegeLevel.ADMINISTRATOR) {
			player.sendMessage("Button clicked: " + event.id)
		}
	}

}
