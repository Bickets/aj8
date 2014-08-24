package items

import common.Plugin
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inv.SynchronizationInventoryListener

@SubscribesTo(ItemActionEvent)
class RemoveEquipment extends Plugin implements EventSubscriber<ItemActionEvent> {

	def remove(Player player, int id, int slot) {
		val inventory = player.inventory
		val equipment = player.equipment

		val item = equipment.get(slot)
		if (slot < 0 || slot >= equipment.capacity) {
			return
		}

		if (item == null || item.id != id) {
			return
		}

		var hasRoomForStackable = inventory.contains(id) && item.definition.stackable
		if (inventory.freeSlots < 1 && !hasRoomForStackable) {
			inventory.forceCapacityExceeded
			return
		}

		inventory.stopFiringEvents
		equipment.stopFiringEvents

		try {
			equipment.set(slot, null)
			inventory.add(item.id, item.amount)
		} finally {
			inventory.startFiringEvents
			equipment.startFiringEvents
		}

		inventory.forceRefresh(slot)
		equipment.forceRefresh(slot)
	}

	override subscribe(ItemActionEvent event) {
		remove(event.player, event.id, event.slot)
	}

	override test(ItemActionEvent event) {
		closeInterfaces(event.player)
		event.interfaceId == SynchronizationInventoryListener.EQUIPMENT_ID
	}

}
