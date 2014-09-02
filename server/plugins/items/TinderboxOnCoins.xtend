package items

import org.apollo.game.interact.ItemOnItemEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.event.EventSubscriber

@SubscribesTo(ItemOnItemEvent)
class ItemOnItem implements EventSubscriber<ItemOnItemEvent> {

	override subscribe(ItemOnItemEvent event) {
		event.player.sendMessage("You decide it's not a good idea to try and burn your coins!")
	}

	override test(ItemOnItemEvent event) {
		event.canCombine(995, 590)
	}

}
