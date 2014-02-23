package commands

import org.apollo.game.command.Command
import org.apollo.game.command.PrivilegedCommandListener
import org.apollo.game.model.Player
import org.apollo.game.model.Player.PrivilegeLevel
import org.apollo.game.model.SkillSet

class LevelCommand extends PrivilegedCommandListener {

	new() {
		super(PrivilegeLevel::ADMINISTRATOR)
	}

	override executePrivileged(Player player, Command command) {
		val args = command.arguments

		if (isDigit(args.get(0)) && isDigit(args.get(1))) {
			val id = Integer.valueOf(args.get(0))
			val level = Integer.valueOf(args.get(1))

			if (id <= player.skillSet.size) {
				if (level > 0 && level < 99) {
					player.skillSet.addExperience(id, SkillSet::getExperienceForLevel(level));
				}
			}
		}

	}

	override getName() {
		"level"
	}

	def isDigit(String string) {
		string.matches("\\d+")
	}

}
