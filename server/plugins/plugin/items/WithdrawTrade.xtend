package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.Interfaces.InterfaceOption
import org.apollo.game.model.inter.trade.TradeConstants
import org.apollo.game.model.inter.trade.TradeUtils
import org.apollo.game.model.inter.trade.TradeWithdrawEnterAmountListener
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemActionEvent)
class WithdrawTrade implements EventSubscriber<ItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!player.interfaceSet.contains(TradeConstants.TRADE_WINDOW_ID, TradeConstants.SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		var amount = InterfaceOption.optionToAmount(event.option)
		if (amount == -1) {
			player.interfaceSet.openEnterAmountDialog(
				new TradeWithdrawEnterAmountListener(player, event.slot, event.id))
		} else {
			if (!TradeUtils.withdraw(player, event.slot, event.id, amount)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.TRADE_INVENTORY_ID
	}

}
