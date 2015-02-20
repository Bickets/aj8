package plugin.mobs

import org.apollo.game.model.Mob
import org.apollo.game.task.Task
import org.eclipse.xtend.lib.annotations.Data

import static plugin.Plugin.*
import static plugin.mobs.MobMovementTask.Stage.*

@Data class MobMovementTask extends Task {

	val Mob mob

	enum Stage {
		NONE,
		WALK_TO_SPAWN,
		WALK_IN_AREA
	}

	def determineNext() {
		if (mob.movementArea == null) {
			if (mob.spawnPosition.getDistance(mob.position) > mob.movementDistance) {
				return WALK_TO_SPAWN
			}

			return NONE
		}

		if (!mob.movementArea.withinArea(mob.position.x, mob.position.y, mob.movementDistance)) {
			return WALK_TO_SPAWN
		}

		if (random(7) == 0 && mob.walkingQueue.size == 0) {
			return WALK_IN_AREA
		}

		return NONE
	}

	override execute() {
		val stage = determineNext

		switch stage {
			case WALK_TO_SPAWN: {
				mob.walkingQueue.clear
				mob.stopAction

				val path = mob.world.pathFinder.find(mob, mob.spawnPosition.x, mob.spawnPosition.y)
				if (path != null) {
					mob.walkingQueue.addFirstStep(path.poll)
					while (!path.empty) {
						mob.walkingQueue.addStep(path.poll)
					}
				}
			}
			case WALK_IN_AREA: {

			}
			default: {
			}
		}
	}

}
