package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.Player
import org.apollo.game.model.^def.EquipmentDefinition
import org.apollo.game.model.^def.ItemDefinition
import org.apollo.game.model.inv.InventoryConstants
import org.apollo.game.model.skill.Skill

import static org.apollo.game.model.EquipmentConstants.*
import static plugin.Plugin.*

@SubscribesTo(ItemActionEvent)
class WearEquipment implements EventSubscriber<ItemActionEvent> {

	def wear(Player player, int id, int slot) {
		val inventory = player.inventory
		val equipment = player.equipment

		val item = inventory.get(slot)
		val itemDef = item.definition
		val equipDef = EquipmentDefinition.forId(item.id)

		if (equipDef == null) {
			player.sendMessage(
				"[id=" + id + ", name=" + itemDef.name + "]: is not wearable, if it should be report this message.")
			return false
		}

		val equipSlot = equipDef.slot

		closeInterfaces(player)

		if (checkReqs(player, equipDef)) {
			return false
		}

		val weapon = equipment.get(WEAPON)
		val shield = equipment.get(SHIELD)

		if (equipDef.twoHanded) {
			var slots = if(weapon != null && shield != null) 1 else 0
			if (inventory.freeSlots < slots) {
				inventory.forceCapacityExceeded
				return false
			}

			equipment.set(WEAPON, inventory.reset(slot))

			if(weapon != null) inventory.add(weapon)
			if(shield != null) inventory.add(equipment.reset(SHIELD))

			return true
		}

		if (equipSlot == SHIELD && weapon != null && EquipmentDefinition.forId(weapon.id).twoHanded) {
			equipment.set(SHIELD, inventory.reset(slot))
			inventory.add(equipment.reset(WEAPON))
			return true
		}

		val previous = equipment.get(equipSlot)

		if (previous != null && previous.id == item.id && itemDef.stackable) {
			val amount = item.amount + previous.amount

			if (amount > 0) {
				return equipment.add(inventory.reset(slot)) == null
			}

			val normalized = amount - Integer.MAX_VALUE
			val removed = item.amount - normalized

			if (normalized == item.amount) {
				equipment.set(equipSlot, item)
				inventory.set(slot, previous)
				return false
			}

			inventory.remove(item.id, removed)
			equipment.add(item.id, removed)
			return true
		}

		equipment.reset(equipSlot)
		inventory.remove(item)
		equipment.set(equipSlot, item)
		if(previous != null) inventory.add(previous)
		return true
	}

	def checkReqs(Player player, EquipmentDefinition equipDef) {
		val skillSet = player.skillSet
		if (skillSet.getSkill(Skill.ATTACK).maximumLevel < equipDef.attackLevel) {
			player.sendMessage("You need an Attack level of " + equipDef.attackLevel + " to equip this item.")
			return true
		}
		if (skillSet.getSkill(Skill.STRENGTH).maximumLevel < equipDef.strengthLevel) {
			player.sendMessage("You need a Strength level of " + equipDef.strengthLevel + " to equip this item.")
			return true
		}
		if (skillSet.getSkill(Skill.DEFENCE).maximumLevel < equipDef.defenceLevel) {
			player.sendMessage("You need a Defence level of " + equipDef.defenceLevel + " to equip this item.")
			return true
		}
		if (skillSet.getSkill(Skill.RANGED).maximumLevel < equipDef.rangedLevel) {
			player.sendMessage("You need a Ranged level of " + equipDef.rangedLevel + " to equip this item.")
			return true
		}
		if (skillSet.getSkill(Skill.MAGIC).maximumLevel < equipDef.magicLevel) {
			player.sendMessage("You need a Magic level of " + equipDef.magicLevel + " to equip this item.")
			return true
		}
	}

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!wear(player, event.id, event.slot)) {
			context.breakSubscriberChain
			return
		}
	}

	override test(ItemActionEvent event) {
		val definition = ItemDefinition.forId(event.id)

		wearable(definition) && event.interfaceId == InventoryConstants.INVENTORY_ID &&
			event.option == InterfaceOption.OPTION_ONE
	}

	// TODO: Maybe move this into the definition class itself..
	def wearable(ItemDefinition ^def) {
		val actions = def.inventoryActions

		if(actions.size == 0) return false

		return switch actions.get(1) {
			case "Wield": true
			case "Wear": true
			case "Ride": true
			case "Hold": true
			default: false
		}
	}

}
