package plugin.items

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.trade.TradeConstants
import org.apollo.game.model.inter.trade.TradeOfferEnterAmountListener
import org.apollo.game.model.inv.InventoryConstants

@SubscribesTo(ItemActionEvent)
class OfferTrade implements EventSubscriber<ItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemActionEvent event) {
		if (!player.interfaceSet.contains(TradeConstants.TRADE_WINDOW_ID, TradeConstants.SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		val amount = event.option.value

		if (amount == -1) {
			player.interfaceSet.openEnterAmountDialog(
				new TradeOfferEnterAmountListener(player, event.slot, event.id))
		} else {
			if (!player.inventory.swap(player.trade, event.slot, event.id, amount, false)) {
				return
			}
		}
	}

	override test(ItemActionEvent event) {
		event.interfaceId == InventoryConstants.TRADE_SIDEBAR_INVENTORY_ID
	}

}
