package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.^def.ItemDefinition
import org.apollo.game.model.inter.Interfaces.InterfaceOption
import org.apollo.game.model.inter.bank.BankConstants
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemActionEvent)
class DepositBank implements EventSubscriber<ItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!player.interfaceSet.contains(BankConstants.BANK_WINDOW_ID, BankConstants.SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		val id = ItemDefinition.noteToItem(event.id)
		val amount = InterfaceOption.optionToAmount(event.option)

		if (amount == -1) {
			player.interfaceSet.openEnterAmountDialog(new BankDepositEnterAmountListener(player, event.slot, id))
		} else {
			if (!player.inventory.swap(player.bank, event.slot, id, amount, false)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.BANK_SIDEBAR_INVENTORY_ID
	}

}
