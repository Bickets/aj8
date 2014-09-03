package plugin.location

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.Direction
import org.apollo.game.model.Position
import plugin.mobs.MobSpawnEvent

@SubscribesTo(MobSpawnEvent)
class Lumbridge implements EventSubscriber<MobSpawnEvent> {

	override subscribe(MobSpawnEvent event) {
		event.spawn("man", 1, new Position(3222, 3222, 0), Direction.NORTH)
	}

}
