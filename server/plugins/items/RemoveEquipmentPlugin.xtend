package items

import org.apollo.game.interact.ItemActionListener
import org.apollo.game.model.InterfaceConstants.InterfaceOption
import org.apollo.game.model.Player
import org.apollo.game.model.inv.SynchronizationInventoryListener

class RemoveEquipmentPlugin extends ItemActionListener {

	new() {
		super(SynchronizationInventoryListener.EQUIPMENT_ID)
	}

	override handle(int id, int slot, InterfaceOption option, int interfaceId, Player player) {
		if (option != InterfaceOption.OPTION_ONE) {
			return
		}

		player.remove(id, slot)
	}

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

}
