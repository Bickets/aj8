package plugin.items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.GroundItem
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.InventoryConstants
import org.apollo.game.msg.impl.GroundItemMessage
import org.apollo.game.msg.impl.PositionMessage

@SubscribesTo(ItemActionEvent)
class DropItem implements EventSubscriber<ItemActionEvent> {

	override subscribe(ItemActionEvent event) {
		val player = event.player
		val item = player.inventory.reset(event.slot)
		player.send(new PositionMessage(player.getLastKnownRegion(), player.getPosition()));
		player.send(new GroundItemMessage(new GroundItem(player.position, player.world, item), 0))
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.INVENTORY_ID && event.option == InterfaceOption.OPTION_THREE
	}

}
