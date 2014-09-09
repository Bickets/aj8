package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.^def.ItemDefinition

import static plugin.Plugin.*

@SubscribesTo(CommandEvent)
class ItemCommand extends Command {

	override execute(Player plr, String[] args) {
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

	override test(CommandEvent event) {
		event.name == "pickup" || event.name == "item"
	}

}
