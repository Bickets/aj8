package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.area.Areas

@SubscribesTo(CommandEvent)
class ItemCommand extends Command {

	override execute(Player plr, String[] args) {
		plr.sendMessage(plr.withinArea(Areas.WILDERNESS_AREA))
	}

	override test(CommandEvent event) {
		event.name == "area"
	}

}
