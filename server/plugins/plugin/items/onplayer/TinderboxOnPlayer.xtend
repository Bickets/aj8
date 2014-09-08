package plugin.items.onplayer

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemOnPlayerActionEvent
import org.apollo.game.model.InventoryConstants

@SubscribesTo(ItemOnPlayerActionEvent)
class TinderboxOnPlayer implements EventSubscriber<ItemOnPlayerActionEvent> {

	override subscribe(ItemOnPlayerActionEvent event) {
		event.player.sendMessage("You're just sick, aren't you?")
	}

	override test(ItemOnPlayerActionEvent event) {
		event.item.id == 590 && event.interfaceId == InventoryConstants.INVENTORY_ID
	}

}
