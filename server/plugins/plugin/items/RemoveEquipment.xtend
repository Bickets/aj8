package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.Interfaces.InventoryAmountOption
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemActionEvent)
class RemoveEquipment implements EventSubscriber<ItemActionEvent> {

	def remove(Player player, int id, int slot) {
		val inventory = player.inventory
		val equipment = player.equipment

		val item = equipment.get(slot)
		val itemDef = item.definition

		if (inventory.freeSlots == 0 && !itemDef.stackable) {
			inventory.forceCapacityExceeded
			return false
		}

		inventory.stopFiringEvents
		equipment.stopFiringEvents

		var removed = true

		try {
			val remaining = inventory.add(id, item.amount)
			removed = remaining.present
			equipment.set(slot, if(removed) null else remaining.get)
		} finally {
			inventory.startFiringEvents
			equipment.startFiringEvents
		}

		if (removed) {
			inventory.forceRefresh
			equipment.forceRefresh(slot)
		} else {
			inventory.forceCapacityExceeded
		}

		return true
	}

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!remove(player, event.id, event.slot)) {
			context.breakSubscriberChain
			return
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.EQUIPMENT_INVENTORY_ID && event.option == InventoryAmountOption.OPTION_ONE
	}

}
