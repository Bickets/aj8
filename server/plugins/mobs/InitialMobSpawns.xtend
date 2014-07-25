package mobs

import java.util.ArrayList
import org.apollo.game.model.Direction
import org.apollo.game.model.Mob
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.slf4j.LoggerFactory

import static org.apollo.game.model.Direction.EAST

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
