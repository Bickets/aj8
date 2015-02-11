package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inv.InventoryConstants
import org.apollo.game.model.inter.Interfaces.InventoryAmountOption

@SubscribesTo(ItemActionEvent)
class DropItem implements EventSubscriber<ItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.INVENTORY_ID && event.option == InventoryAmountOption.OPTION_TEN
	}

}
