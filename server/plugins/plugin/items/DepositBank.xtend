package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.Player
import org.apollo.game.model.inter.bank.BankConstants
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener
import org.apollo.game.model.inter.bank.BankUtils
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemActionEvent)
class DepositBank implements EventSubscriber<ItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!player.interfaceSet.contains(BankConstants.BANK_WINDOW_ID, BankConstants.SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		var amount = InterfaceOption.optionToAmount(event.option)
		if (amount == -1) {
			player.interfaceSet.openEnterAmountDialog(
				new BankDepositEnterAmountListener(player, event.slot, event.id))
		} else {
			if (!BankUtils.deposit(player, event.slot, event.id, amount)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.BANK_SIDEBAR_INVENTORY_ID
	}

}
