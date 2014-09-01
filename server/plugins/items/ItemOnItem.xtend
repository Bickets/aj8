package items

import org.apollo.game.interact.ItemOnItemEvent
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.event.EventSubscriber
import org.apollo.game.model.Item

@SubscribesTo(ItemOnItemEvent)
class ItemOnItem implements EventSubscriber<ItemOnItemEvent> {

	Item receiver
	Item sender

	override subscribe(ItemOnItemEvent event) {
		val player = event.player
		receiver = player.inventory.get(event.receiverSlot)
		sender = player.inventory.get(event.senderSlot)
		player.sendMessage("" + receiver.id)
		player.sendMessage("" + sender.id)
		if (combination(995, 590)) {
			player.sendMessage("Works!")
		}
	}

	def combination(int itemOne, int itemTwo) {
		(receiver.id == itemOne && sender.id == itemTwo) || (receiver.id == itemTwo && sender.id == itemOne)
	}
}
