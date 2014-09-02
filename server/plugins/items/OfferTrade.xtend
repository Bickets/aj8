package items

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Interfaces.InterfaceOption
import org.apollo.game.model.InventoryConstants
import org.apollo.game.model.inter.trade.TradeConstants
import org.apollo.game.model.inter.trade.TradeOfferEnterAmountListener
import org.apollo.game.model.inter.trade.TradeUtils

@SubscribesTo(ItemActionEvent)
class OfferTrade implements EventSubscriber<ItemActionEvent> {

	override subscribe(ItemActionEvent event) {
		var amount = InterfaceOption.optionToAmount(event.option)
		if (amount == -1) {
			event.player.interfaceSet.openEnterAmountDialog(
				new TradeOfferEnterAmountListener(event.player, event.slot, event.id))
		} else {
			if (!TradeUtils.offer(event.player, event.slot, event.id, amount)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.TRADE_SIDEBAR_INVENTORY_ID &&
			event.player.interfaceSet.contains(TradeConstants.TRADE_WINDOW_ID, TradeConstants.SIDEBAR_ID)
	}

}
