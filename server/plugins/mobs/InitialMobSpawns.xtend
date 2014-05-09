package mobs

import java.util.ArrayList
import java.util.logging.Logger
import org.apollo.game.model.Direction
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World

@Data class InitialMobSpawns {
	val World world
	val mobs = new ArrayList<Mob>
	val logger = Logger::getLogger(InitialMobSpawns.name)

	def init() {
		addAll

		mobs.forEach[mob|world.register(mob)]
		logger.info('Loaded ' + mobs.size + ' mob spawns.')
	}

	def addAll() {
		add(1, new Position(3222, 3222), Direction::EAST)
	}

	def add(int id, Position position, Direction direction) {
		mobs.add(new Mob(id, position, direction))
	}
}
