package commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Position

@SubscribesTo(CommandEvent)
class CommandPlugin implements EventSubscriber<CommandEvent> {

	override subscribe(CommandEvent event) {
		val args = event.arguments
		val plr = event.player

		switch event.name.toLowerCase {
			case "tele": {
				if (args.length < 2 || args.length > 3) {
					plr.sendMessage("Syntax is: ::tele [x] [y] [z=0]")
					return
				}

				val x = args.get(0).asInt
				val y = args.get(1).asInt
				val z = if(args.length == 3) args.get(2).asInt else 0
				plr.teleport(new Position(x, y, z))
			}
		}
	}

	def isNumeric(String str) {
		str.matches("-?\\d+")
	}

	def asInt(String str) {
		if (str.isNumeric)
			Integer.valueOf(str)
		else
			throw new NumberFormatException(str + " is not a valid numeric string!")
	}

}
