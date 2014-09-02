package commands

import common.Plugin
import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player.PrivilegeLevel
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.^def.GameObjectDefinition
import org.apollo.game.model.^def.ItemDefinition
import org.apollo.game.model.inter.bank.BankUtils
import org.apollo.game.model.inter.trade.TradeUtils
import org.apollo.game.model.obj.GameObject
import org.apollo.game.model.pf.AStarPathFinder
import org.apollo.game.msg.impl.GameObjectMessage
import org.apollo.game.msg.impl.OpenInterfaceMessage
import org.apollo.game.msg.impl.WelcomeScreenMessage

@SubscribesTo(CommandEvent)
@Data class CommandPlugin extends Plugin implements EventSubscriber<CommandEvent> {
	val World world;

	override subscribe(CommandEvent event) {
		val args = event.arguments
		val plr = event.player

		switch event.name.toLowerCase {
			case "open": {
				plr.send(new WelcomeScreenMessage(201, 3, false, (127 << 24) + 1, 1))
				plr.send(new OpenInterfaceMessage(15244))
			}
			case "admin":
				plr.privilegeLevel = PrivilegeLevel.ADMINISTRATOR
			case "close":
				plr.interfaceSet.close
			case "trade":
				TradeUtils.openTrade(plr, world.playerRepository.get(2))
			case "pickup": {
				if (args.length < 1) {
					plr.sendMessage("Syntax is ::pickup [id] [amount=1]")
					return
				}

				val id = toInt(args.get(0))
				var amount = 1

				if (args.length == 2) {
					if ("max".equals(args.get(1))) {
						amount = Integer.MAX_VALUE
					} else {
						args.set(1, args.get(1).toLowerCase)
						args.set(1, args.get(1).replace("k", "000"))
						args.set(1, args.get(1).replace("m", "000000"))
						args.set(1, args.get(1).replace("b", "000000000"))
						amount = toInt(args.get(1))
					}
				}

				if (id < 0 || amount < 0 || id > ItemDefinition.count || amount > Integer.MAX_VALUE) {
					plr.sendMessage("Item [id=" + id + ", amount=" + amount + "] ")
					return
				}

				plr.inventory.add(id, amount)
			}
			case "bank":
				BankUtils.openBank(plr)
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

				val id = toInt(args.get(0))
				if (id < 0 || id > GameObjectDefinition.count) {
					plr.sendMessage("Object: [id=" + id + "] is not valid.")
					return
				}

				val object = new GameObject(id, plr.position)

				plr.send(new GameObjectMessage(object, 0))
			}
			case "walk": {
				if (args.length != 2) {
					plr.sendMessage("Syntax is: ::walk [x, y]")
					return
				}

				val x = toInt(args.get(0))
				val y = toInt(args.get(1))

				val finder = new AStarPathFinder
				val path = finder.find(plr, x, y)
				plr.walkingQueue.clear
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
