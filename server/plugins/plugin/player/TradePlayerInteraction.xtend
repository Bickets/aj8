package plugin.player

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.PlayerActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.PlayerContextMenuOption
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction
import plugin.trade.Trade

@SubscribesTo(PlayerActionEvent)
class TradePlayerInteraction implements EventSubscriber<PlayerActionEvent> {

	override subscribe(EventContext context, Player player, PlayerActionEvent event) {
		if (!player.hasContextMenuOption(PlayerContextMenuOption.TRADE)) {
			context.breakSubscriberChain
			return
		}

		val other = event.other

		/* Set the current request; trading a different user cancels the previous request */
		player.attributes.tradeRequest = other

		val request = player.attributes.tradeRequest
		val otherRequest = other.attributes.tradeRequest

		/* Compare in reverse; player's will never be null however requests may be. */
		if (other.equals(request) && player.equals(otherRequest)) {
			Trade.open(player, other)
		} else {

			/* Only send request message if it is not time to open the trade; no need to double request. */
			player.sendMessage("Sending trade request...")
			other.sendMessage(player.displayName + ":tradereq:")
		}
	}

	override test(PlayerActionEvent event) {
		event.action == InteractContextMenuAction.ACTION_FOUR
	}

}
