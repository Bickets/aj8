package plugin.items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.InventoryConstants
import org.apollo.game.model.Item
import org.apollo.game.model.Player

import static plugin.Plugin.*

@SubscribesTo(ItemActionEvent)
class RemoveEquipment implements EventSubscriber<ItemActionEvent> {

	def remove(Player player, int id, int slot) {
		val inventory = player.inventory
		val equipment = player.equipment

		val item = equipment.get(slot)
		val itemDef = item.definition

		closeInterfaces(player)

		if (inventory.freeSlots == 0 && !itemDef.stackable) {
			inventory.forceCapacityExceeded
			return
		}

		inventory.stopFiringEvents
		equipment.stopFiringEvents

		var removed = true

		try {
			val remaining = inventory.add(id, item.amount)
			removed = remaining == 0
			equipment.set(slot, if(removed) null else new Item(id, remaining))
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
	}

	override subscribe(ItemActionEvent event) {
		remove(event.player, event.id, event.slot)
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.EQUIPMENT_INVENTORY_ID
	}

}
