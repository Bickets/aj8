package mobs

import org.apollo.game.model.Direction
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World

@Data abstract class MobSpawn {

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

	def void spawn();

}
