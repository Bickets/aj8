package plugin.items.onplayer

import java.util.Map
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemOnPlayerActionEvent
import org.apollo.game.model.Animation
import org.apollo.game.model.Graphic
import org.apollo.game.model.InventoryConstants
import org.apollo.game.model.Player
import org.apollo.game.model.inter.dialog.DialogueOption
import org.apollo.game.model.inter.dialog.OptionDialogueListener

import static org.apollo.game.model.inter.dialog.DialogueOption.*
import static plugin.Plugin.*

@SubscribesTo(ItemOnPlayerActionEvent)
class ChristmasCracker implements EventSubscriber<ItemOnPlayerActionEvent> {

	val items = newHashMap(
		1038 -> 1 -> 23.81,
		1040 -> 1 -> 22.28,
		1042 -> 1 -> 18.22,
		1044 -> 1 -> 17.21,
		1046 -> 1 -> 11.18,
		1048 -> 1 -> 7.30
	)

	val secondaryItems = newHashMap(
		1973 -> 1 -> 19.07,
		2355 -> 1 -> 13.24,
		1969 -> 1 -> 12.42,
		441 -> 5 -> 11.74,
		1897 -> 1 -> 11.52,
		1718 -> 1 -> 8.53,
		950 -> 1 -> 8.23,
		1635 -> 1 -> 7.18,
		1217 -> 1 -> 4.11,
		563 -> 1 -> 3.96
	)

	override subscribe(ItemOnPlayerActionEvent event) {
		val player = event.player
		val other = event.otherPlayer

		player.interfaceSet.openDialogue(
			new OptionDialogueListener() {
				override optionClicked(DialogueOption option) {
					if (option == FIRST_OPTION) {
						player.sendMessage("You pull a Christmas cracker...")
						other.sendMessage("You pull a Christmas cracker...")

						player.playGraphic(new Graphic(176, 0, 100))
						player.playAnimation(new Animation(451))
						player.inventory.remove(event.item)

						if (random(100) < 50) {
							player.sendMessage("You get the prize from the cracker.")
							other.sendMessage("The person you pull the cracker with gets the prize.")
							player.give
						} else {
							other.sendMessage("You get the prize from the cracker.")
							player.sendMessage("The person you pull the cracker with gets the prize.")
							other.give
						}
					}
				}

				override lines() {
					#["That's okay, I might get a party hat!", "Stop, I want to keep my cracker."]
				}
			})

	}

	def give(Player player) {
		val rewards = newHashSet
		rewards += getReward(items)
		rewards += getReward(secondaryItems)

		rewards.forEach[player.inventory.add(it.key, it.value)]

		player.forceChat("Hey! I got the cracker!")
	}

	def getReward(Map<Pair<Integer, Integer>, Double> items) {
		var random = Math.random * 100
		for (entry : items.entrySet) {
			if ((random -= entry.value) < 0) {
				return entry.key
			}
		}
	}

	override test(ItemOnPlayerActionEvent event) {
		if (event.item.id != 962 || event.interfaceId != InventoryConstants.INVENTORY_ID) {
			return false
		}

		val player = event.player
		val other = event.otherPlayer

		if (player.inventory.freeSlots < 1) {
			player.sendMessage("You do not have enough inventory space to do that.")
			return false
		}

		if (other.inventory.freeSlots < 2) {
			player.sendMessage("Other player does not have enough inventory space to do that.")
			return false
		}

		return true
	}

}
