package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.World
import org.eclipse.xtend.lib.annotations.Data

import static plugin.Plugin.*

@SubscribesTo(CommandEvent)
@Data class UpdateCommand extends Command {

	val World world

	override execute(Player player, String[] args) {
		if (args.length == 0) {
			world.scheduleUpdate
		} else
			world.scheduleUpdate(toInt(args.get(0)))
	}

	override test(CommandEvent event) {
		event.name == "update"
	}

}
