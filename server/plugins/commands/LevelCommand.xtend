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

		if (!isDigit(args.get(0)) || !isDigit(args.get(1))) {
			player.sendMessage('Syntax is ::' + getName() + ' [skill_id, level]')
			return
		}

		val id = Integer.valueOf(args.get(0))
		val level = Integer.valueOf(args.get(1))

		if (id > SkillSet::SKILL_COUNT || id < 0 || level < 0 || level > 99) {
			player.sendMessage('Syntax is ::' + getName() + ' [skill_id, level]')
			return
		}

		player.skillSet.addExperience(id, SkillSet::getExperienceForLevel(level));
	}

	override getName() {
		"level"
	}

	def isDigit(String string) {
		string.matches("\\d+")
	}

}
