package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.model.Player

abstract class Command implements EventSubscriber<CommandEvent> {

	override subscribe(EventContext context, Player player, CommandEvent event) {
		execute(player, event.arguments)
	}

	def void execute(Player player, String[] args)

}
