package items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inv.SynchronizationInventoryListener
import org.apollo.game.common.Interfaces.InterfaceOption

class RemoveEquipment implements EventSubscriber<ItemActionEvent> {

	def remove(Player player, int id, int slot) {
		val inventory = player.inventory
		val equipment = player.equipment

		var hasRoomForStackable = inventory.contains(id) && inventory.get(slot).definition.stackable
		if (inventory.freeSlots < 1 && !hasRoomForStackable) {
			inventory.forceCapacityExceeded
			return
		}
 
		if (slot < 0 || slot >= equipment.capacity) {
			return
		}

		val item = equipment.get(slot)
		if (item == null || item.id != id) {
			return
		}

		var removed = true

		inventory.stopFiringEvents
		equipment.stopFiringEvents

		try {
			equipment.set(slot, null)
			var copy = item
			inventory.add(item.id, item.amount)
			if (copy != null) {
				removed = false
				equipment.set(slot, copy)
			}
		} finally {
			inventory.startFiringEvents
			equipment.startFiringEvents
		}

		if (removed) {
			inventory.forceRefresh(slot)
			equipment.forceRefresh(slot)
		} else {
			inventory.forceCapacityExceeded
		}
	}

	override subscribe(ItemActionEvent event) {
		remove(event.player, event.id, event.slot)
	}

	override test(ItemActionEvent event) {
		event.interfaceId == SynchronizationInventoryListener.EQUIPMENT_ID && event.option == InterfaceOption.OPTION_ONE
	}

}
