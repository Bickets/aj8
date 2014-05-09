package commands

import org.apollo.game.command.Command
import org.apollo.game.command.PrivilegedCommandListener
import org.apollo.game.model.Player
import org.apollo.game.model.Player.PrivilegeLevel
import org.apollo.game.model.^def.ItemDefinition

class ItemCommand extends PrivilegedCommandListener {

	new() {
		super(PrivilegeLevel::ADMINISTRATOR)
	}

	override executePrivileged(Player player, Command command) {
		val args = command.arguments

		if (!isDigit(args.get(0)) || !isDigit(args.get(1))) {
			player.sendMessage('Syntax is ::' + getName() + ' [id, amount]')
			return
		}

		val id = Integer.valueOf(args.get(0))
		val amount = Integer.valueOf(args.get(1))

		if (id > ItemDefinition::count || id <= 0 || amount <= 0 || amount > Integer.MAX_VALUE) {
			player.sendMessage('Syntax is ::' + getName() + ' [id, amount]')
			return
		}

		player.inventory.add(id, amount)
	}

	override getName() {
		"item"
	}

	def isDigit(String string) {
		string.matches("\\d+")
	}

}
