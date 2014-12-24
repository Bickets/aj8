package plugin.commands

import org.apollo.game.command.CommandEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Player
import org.apollo.game.model.skill.Skill
import org.apollo.game.model.skill.SkillSet

import static plugin.Plugin.*

@SubscribesTo(CommandEvent)
class LevelCommand extends Command {

	override execute(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("Syntax is ::level [skill_id] [level]")
			return
		}

		val skill_id = toInt(args.get(0))
		var level = toInt(args.get(1))

		if (skill_id < 0 || level < 1 || level > SkillSet.MAXIMUM_LEVEL || skill_id > SkillSet.SKILL_COUNT) {
			player.sendMessage("Skill [skill_id=" + skill_id + ", level=" + level + "] is not valid.")
			return
		}

		if (skill_id == Skill.HITPOINTS && level < 10) {
			level = 10
		}

		val skills = player.skillSet

		/* If the level is lower than our current, we need to set the skill */
		if (level < skills.getSkill(skill_id).maximumLevel) {
			skills.setSkill(skill_id, new Skill(SkillSet.getExperienceForLevel(level), level, level))
		} else {

			/* Otherwise add experience like normal */
			skills.addExperience(skill_id, SkillSet.getExperienceForLevel(level))
		}

	}

	override test(CommandEvent event) {
		event.name == "level"
	}

}
