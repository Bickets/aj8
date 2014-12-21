package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player

@SubscribesTo(CommandEvent)
class PositionCommand extends Command {

	override execute(Player player, String[] args) {
		player.sendMessage(player.position)
	}

	override test(CommandEvent event) {
		event.name == "pos"
	}

}
