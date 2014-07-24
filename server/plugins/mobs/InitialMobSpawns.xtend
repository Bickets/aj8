package mobs

import java.util.ArrayList
import java.util.logging.Logger
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World

import static org.apollo.game.model.Direction.EAST
import org.apollo.game.model.Direction
import org.slf4j.LoggerFactory

@Data class InitialMobSpawns {
	val World world
	val mobs = new ArrayList<Mob>
	val logger = LoggerFactory.getLogger(InitialMobSpawns)

	def init() {
		addAll

		mobs.map[world.register(it)]
		logger.info('Loaded {} mob spawns.', mobs.size)
	}

	def addAll() {
		add(1, new Position(3222, 3222), EAST)
	}

	def add(int id, Position position, Direction direction) {
		mobs.add(new Mob(id, position, direction))
	}
}
