package plugin.mobs

import org.apollo.game.event.Event
import org.apollo.game.model.Direction
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.eclipse.xtend.lib.annotations.Data

@Data class MobSpawnEvent implements Event {

	val World world

	def spawn(int id, Position position, Direction direction) {
		spawn("n/a", id, position, direction)
	}

	def spawn(int id, Position position) {
		spawn("n/a", id, position)
	}

	def spawn(String name, int id, Position position) {
		spawn(name, id, position, Direction.NORTH)
	}

	def spawn(String name, int id, Position position, Direction direction) {
		val mob = new Mob(id, position, direction)
		world.register(mob)
	}

}
