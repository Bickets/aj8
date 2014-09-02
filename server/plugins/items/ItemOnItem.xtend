package items

import org.apollo.game.interact.ItemOnItemEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.event.EventSubscriber

@SubscribesTo(ItemOnItemEvent)
class ItemOnItem implements EventSubscriber<ItemOnItemEvent> {

	override subscribe(ItemOnItemEvent event) {
		val player = event.player
		
		if (combination(event, 995, 590)) {
			player.sendMessage("You decide it's not a good idea to try and burn your coins!")
		}
	}

	def combination(ItemOnItemEvent event, int itemOne, int itemTwo) {
		(event.receiver.id == itemOne && event.sender.id == itemTwo) ||
			(event.receiver.id == itemTwo && event.sender.id == itemOne)
	}
}