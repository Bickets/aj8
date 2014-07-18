package items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.inter.bank.BankConstants
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener
import org.apollo.game.model.inter.bank.BankUtils

class DepositBank implements EventSubscriber<ItemActionEvent> {

	override subscribe(ItemActionEvent event) {
		var amount = InterfaceOption.optionToAmount(event.option)
		if (amount == -1) {
			event.player.interfaceSet.openEnterAmountDialog(
				new BankDepositEnterAmountListener(event.player, event.slot, event.id))
		} else {
			if (!BankUtils.deposit(event.player, event.slot, event.id, amount)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == BankConstants.SIDEBAR_INVENTORY_ID;
	}

}
