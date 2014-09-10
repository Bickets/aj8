package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.msg.impl.GameCharacterHintIconMessage
import org.apollo.game.msg.impl.PositionHintIconMessage
import org.eclipse.xtend.lib.annotations.Data

import static org.apollo.game.msg.impl.HintIconMessage.HintIconType.*
import static plugin.Plugin.*

@SubscribesTo(CommandEvent)
@Data class HintCommand extends Command {
	val World world

	override execute(Player player, String[] args) {
		val typeId = toInt(args.get(0))
		var type = switch typeId {
			case 1: MOB
			case 2: CENTRAL
			case 3: WEST
			case 4: EAST
			case 5: SOUTH
			case 6: NORTH
			case 10: PLAYER
		}

		if (type == MOB) {
			var index = toInt(args.get(1))
			if (index < 1 || index >= world.mobRepository.capacity) {
				return
			}

			val mob = world.mobRepository.get(index)
			if (mob == null || mob.index != index) {
				return
			}

			player.send(new GameCharacterHintIconMessage(mob))
		}

		if (type == PLAYER) {
			var index = toInt(args.get(1))
			if (index < 1 || index >= world.playerRepository.capacity) {
				return
			}

			val other = world.playerRepository.get(index)
			if (other == null || other.index != index) {
				return
			}

			player.send(new GameCharacterHintIconMessage(other))
		}

		if (type != PLAYER && type != MOB) {
			val x = toInt(args.get(1))
			val y = toInt(args.get(2))
			val height = toInt(args.get(3))

			player.send(new PositionHintIconMessage(type, new Position(x, y), height))
		}
	}

	override test(CommandEvent event) {
		event.name == "hint"
	}

}
