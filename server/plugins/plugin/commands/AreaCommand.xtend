package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player

@SubscribesTo(CommandEvent)
class AreaCommand extends Command {

	override execute(Player plr, String[] args) {
		val path = plr.world.pathFinder.find(plr, 3200, 3200)
		if (path == null || path.empty) {
			plr.sendMessage("Could not find path to 3200 3200!")
			return
		}

		plr.walkingQueue.addFirstStep(path.poll)
		while (!path.empty) {
			plr.walkingQueue.addStep(path.poll)
		}
	}

	override test(CommandEvent event) {
		event.name == "area"
	}

}
