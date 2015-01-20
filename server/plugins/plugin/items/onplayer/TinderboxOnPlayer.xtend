package plugin.items.onplayer

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemOnPlayerActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemOnPlayerActionEvent)
class TinderboxOnPlayer implements EventSubscriber<ItemOnPlayerActionEvent> {

	override subscribe(EventContext context, Player player, ItemOnPlayerActionEvent event) {
		player.sendMessage("Why would you do that to someone? You're sick.")
	}

	override test(ItemOnPlayerActionEvent event) {
		event.item.id == 590 && event.interfaceId == InventoryConstants.INVENTORY_ID
	}

}
