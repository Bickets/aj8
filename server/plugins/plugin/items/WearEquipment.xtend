package plugin.items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.EquipmentConstants
import org.apollo.game.model.InventoryConstants
import org.apollo.game.model.Player
import org.apollo.game.model.Skill
import org.apollo.game.model.^def.EquipmentDefinition
import org.apollo.game.model.^def.ItemDefinition
import plugin.Plugin

@SubscribesTo(ItemActionEvent)
class WearEquipment extends Plugin implements EventSubscriber<ItemActionEvent> {

	def wear(Player player, int id, int slot) {
		val item = player.inventory.get(slot)
		if (item == null || item.id != id) {
			return
		}

		val itemDef = item.definition
		val equipDef = EquipmentDefinition.forId(item.id)
		if (equipDef == null) {
			return
		}

		closeInterfaces(player)

		val skillSet = player.skillSet
		if (skillSet.getSkill(Skill.ATTACK).maximumLevel < equipDef.attackLevel) {
			player.sendMessage("You need an Attack level of " + equipDef.attackLevel + " to equip this item.")
			return
		}
		if (skillSet.getSkill(Skill.STRENGTH).maximumLevel < equipDef.strengthLevel) {
			player.sendMessage("You need a Strength level of " + equipDef.strengthLevel + " to equip this item.")
			return
		}
		if (skillSet.getSkill(Skill.DEFENCE).maximumLevel < equipDef.defenceLevel) {
			player.sendMessage("You need a Defence level of " + equipDef.defenceLevel + " to equip this item.")
			return
		}
		if (skillSet.getSkill(Skill.RANGED).maximumLevel < equipDef.rangedLevel) {
			player.sendMessage("You need a Ranged level of " + equipDef.rangedLevel + " to equip this item.")
			return
		}
		if (skillSet.getSkill(Skill.MAGIC).maximumLevel < equipDef.magicLevel) {
			player.sendMessage("You need a Magic level of " + equipDef.magicLevel + " to equip this item.")
			return
		}

		val inventory = player.inventory
		val equipment = player.equipment

		val equipmentSlot = equipDef.slot

		// check if there is enough space for a two handed weapon
		if (equipDef.twoHanded) {
			if (equipment.get(EquipmentConstants.SHIELD) != null) {
				val shield = equipment.reset(EquipmentConstants.SHIELD)
				inventory.add(shield)
			}
		}

		// check if a shield is being added with a two handed weapon
		var removeWeapon = false
		if (equipmentSlot == EquipmentConstants.SHIELD) {
			val currentWeapon = equipment.get(EquipmentConstants.WEAPON)
			if (currentWeapon != null) {
				val weaponDef = EquipmentDefinition.forId(currentWeapon.id)
				if (weaponDef.twoHanded) {
					if (inventory.freeSlots < 1) {
						inventory.forceCapacityExceeded
						return
					}
					removeWeapon = true
				}
			}
		}

		val previous = equipment.get(equipmentSlot)
		if (itemDef.stackable && previous != null && previous.getId == item.id) {

			// we know the item is there, so we can let the inventory class
			// do its stacking magic
			inventory.remove(item)
			val tmp = equipment.add(item)
			if (tmp != null) {
				inventory.add(tmp)
			}
		} else {

			// swap the weapons around
			val tmp = equipment.reset(equipmentSlot)
			equipment.set(equipmentSlot, item)
			inventory.reset(slot)
			if (tmp != null) {
				inventory.add(tmp)
			}
		}

		// remove the shield if this weapon is two handed
		if (equipDef.twoHanded) {
			val tmp = equipment.reset(EquipmentConstants.SHIELD)

			// we know tmp will not be null from the check above
			inventory.add(tmp)
		}

		if (removeWeapon) {
			val tmp = equipment.reset(EquipmentConstants.WEAPON)

			// we know tmp will not be null from the check above
			inventory.add(tmp)
		}
	}

	override subscribe(ItemActionEvent event) {
		wear(event.player, event.id, event.slot)
	}

	override test(ItemActionEvent event) {
		val definition = ItemDefinition.forId(event.id)
		if (!definition.inventoryActions.contains("Wield") && !definition.inventoryActions.contains("Wear")) {
			return false
		}
		event.interfaceId == InventoryConstants.INVENTORY_ID
	}

}
