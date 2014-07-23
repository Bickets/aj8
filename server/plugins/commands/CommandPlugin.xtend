package commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Position
import org.apollo.game.model.obj.GameObject
import org.apollo.game.msg.impl.GameObjectMessage
import org.apollo.game.msg.impl.PositionMessage
import common.Plugin

@SubscribesTo(CommandEvent)
class CommandPlugin extends Plugin implements EventSubscriber<CommandEvent> {

	override subscribe(CommandEvent event) {
		val args = event.arguments
		val plr = event.player

		switch event.name.toLowerCase {
			case "tele": {
				if (args.length < 2 || args.length > 3) {
					plr.sendMessage("Syntax is: ::tele [x] [y] [z=0]")
					return
				}

				val x = toInt(args.get(0))
				val y = toInt(args.get(1))
				val z = if(args.length == 3) toInt(args.get(2)) else 0
				plr.teleport(new Position(x, y, z))
			}
			case "obj": {
				plr.send(new PositionMessage(plr.position))
				plr.send(new GameObjectMessage(new GameObject(2213, plr.position), 0))
			}
		}
	}

}
