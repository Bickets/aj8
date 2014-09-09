package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.Position

import static plugin.Plugin.*

@SubscribesTo(CommandEvent)
class TeleCommand extends Command {

	override execute(Player plr, String[] args) {
		if (args.length < 2 || args.length > 3) {
			plr.sendMessage("Syntax is: ::tele [x] [y] [z=0]")
			return
		}

		val x = toInt(args.get(0))
		val y = toInt(args.get(1))
		val z = if(args.length == 3) toInt(args.get(2)) else 0

		if (x < 1 || y < 1 || z < 0) {
			plr.sendMessage("Position: [x=" + x + ", y=" + y + ", z=" + z + "] is not valid.")
			return
		}

		plr.teleport(new Position(x, y, z))
	}

	override test(CommandEvent event) {
		event.name == "tele"
	}

}
