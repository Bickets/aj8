package plugin.mobs

import org.apollo.game.event.Event
import org.apollo.game.model.Direction
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World
<<<<<<< HEAD
=======
import org.apollo.game.model.area.Area
import org.apollo.game.model.area.PositionArea
import org.apollo.game.task.TaskScheduler
import org.eclipse.xtend.lib.annotations.Data

import static org.apollo.game.model.Direction.*
>>>>>>> origin/master

@Data class MobSpawnEvent implements Event {

	val World world

	def spawn(String name, int id, Position position) {
		spawn(name, id, position, 1, NONE)
	}

	def spawn(String name, int id, Position position, int movementDistance) {
		spawn(name, id, position, movementDistance, Direction.NONE)
	}

	def spawn(String name, int id, Position position, int movementDistance, Direction direction) {
		spawn(name, id, position, new PositionArea(position), movementDistance, direction)
	}

	def spawn(String name, int id, Position position, Area movementArea, int movementDistance, Direction direction) {
		var spawnPosition = position

		if (direction != NONE) {
			spawnPosition = new Position(position.x, position.y + direction.toInteger, position.height)
		}

		val mob = new Mob(id, spawnPosition)
		mob.movementArea = movementArea
		mob.movementDistance = movementDistance

		world.register(mob)

		TaskScheduler.instance.schedule(new MobMovementTask(1, true, mob))
	}

}
