package location

import mobs.MobSpawn
import org.apollo.game.model.Direction
import org.apollo.game.model.Position
import org.apollo.game.model.World

class Lumbridge extends MobSpawn {

	new(World world) {
		super(world)
	}

	override spawn() {
		spawn("man", 1, new Position(3222, 3222, 0), Direction.NORTH)
	}

}
