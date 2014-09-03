package plugin.items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.InventoryConstants
import org.apollo.game.model.inter.bank.BankConstants
import org.apollo.game.model.inter.bank.BankUtils
import org.apollo.game.model.inter.bank.BankWithdrawEnterAmountListener

@SubscribesTo(ItemActionEvent)
class WithdrawBank implements EventSubscriber<ItemActionEvent> {

	override subscribe(ItemActionEvent event) {
		var amount = InterfaceOption.optionToAmount(event.option)
		if (amount == -1) {
			event.player.interfaceSet.openEnterAmountDialog(
				new BankWithdrawEnterAmountListener(event.player, event.slot, event.id))
		} else {
			if (!BankUtils.withdraw(event.player, event.slot, event.id, amount)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.BANK_INVENTORY_ID &&
			event.player.interfaceSet.contains(BankConstants.BANK_WINDOW_ID, BankConstants.SIDEBAR_ID)
	}

}
