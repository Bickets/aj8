package commands

import common.Plugin
import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Position
import org.apollo.game.model.^def.GameObjectDefinition
import org.apollo.game.model.obj.GameObject
import org.apollo.game.model.pf.AStarPathFinder
import org.apollo.game.msg.impl.GameObjectMessage

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

				//				val r_pos = plr.lastEnteredRegionPosition
				//				val curr = plr.position
				//				val offset = Math.abs((curr.x - r_pos.x) + (curr.y - r_pos.y))
				//				println(offset)
				plr.send(new GameObjectMessage(object, 0))
			}
			case "walk": {
				if (args.length != 2) {
					plr.sendMessage("Syntax is: ::walk [x, y]")
					return
				}

				var x = toInt(args.get(0))
				var y = toInt(args.get(1))

				val finder = new AStarPathFinder
				val path = finder.find(plr, x, y)
				if (path != null) {
					plr.walkingQueue.addFirstStep(path.poll)
					while (!path.empty) {
						plr.walkingQueue.addStep(path.poll)
					}
				}
			}
		}
	}

}
