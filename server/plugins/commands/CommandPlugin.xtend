package commands

import common.Plugin
import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Position
import org.apollo.game.model.^def.GameObjectDefinition
import org.apollo.game.model.obj.GameObject
import org.apollo.game.msg.impl.GameObjectMessage
import org.apollo.game.msg.impl.PositionMessage

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

				if (x < 1 || y < 1 || z < 0) {
					plr.sendMessage("Position: [x=" + x + ", y=" + y + ", z=" + z + "] is not valid.")
					return
				}

				plr.teleport(new Position(x, y, z))
			}
			case "obj": {
				if (args.length != 1) {
					plr.sendMessage("Syntax is: ::obj [id]")
					return
				}

				var id = toInt(args.get(0))
				if (id < 0 || id > GameObjectDefinition.count) {
					plr.sendMessage("Object: [id=" + id + "] is not valid.")
					return
				}

				val object = new GameObject(id, plr.position)
				object.notifyExists

				plr.send(new PositionMessage(object.position))
				plr.send(new GameObjectMessage(object, 0))
			}
		}
	}

}
