package plugin.trade

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.trade.TradeStage
import org.apollo.game.model.inter.trade.TradeStatus
import org.apollo.game.msg.impl.SetInterfaceTextMessage

import static org.apollo.game.model.inter.trade.TradeConstants.*
import static org.apollo.game.model.inter.trade.TradeStage.*
import static org.apollo.game.model.inter.trade.TradeStatus.*

@SubscribesTo(ButtonActionEvent)
class AcceptSecondScreenTrade implements EventSubscriber<ButtonActionEvent> {

	override subscribe(EventContext context, Player player, ButtonActionEvent event) {
		val session = player.attributes.tradeSession

		if (!player.interfaceSet.contains(CONFIRM_TRADE_WINDOW_ID, CONFIRM_SIDEBAR_ID)) {
			context.breakSubscriberChain
			return
		}

		if (session == null) {
			context.breakSubscriberChain
			return
		}

		val other = session.other

		if (other == null || session.player !== player || other === player) {
			context.breakSubscriberChain
			return
		}

		val otherSession = other.attributes.tradeSession

		if (otherSession == null) {
			context.breakSubscriberChain
			return
		}

		if (validStatus(session.status) && validStatus(otherSession.status) && validStage(session.stage) &&
			validStage(otherSession.stage)) {
			player.send(new SetInterfaceTextMessage(SECOND_SCREEN_TITLE_ID, "Waiting for other player..."))
			other.send(new SetInterfaceTextMessage(SECOND_SCREEN_TITLE_ID, "Other player has accepted."))

			session.checkpoint(ACCEPTED_SECOND)
		}

		if (session.status == ACCEPTED_SECOND && otherSession.status == ACCEPTED_SECOND) {
			session.checkpoint(VERIFYING)
			otherSession.checkpoint(VERIFYING)

			if (session.verify && otherSession.verify) {
				session.checkpoint(FINISHED)
				otherSession.checkpoint(FINISHED)

				finish(player, other)
			} else {
				session.decline(false)
			}
		}
	}

	def validStatus(TradeStatus status) {
		status == VERIFYING || status == ACCEPTED_SECOND || status == UPDATING_ITEMS
	}

	def validStage(TradeStage stage) {
		stage == SECOND_SCREEN
	}

	def finish(Player player, Player other) {
		player.close
		other.close

		other.inventory.addAll(player.items)
		player.inventory.addAll(other.items)
	}

	def close(Player player) {
		player.interfaceSet.close
		player.attributes.tradeSession = null
		player.sendMessage("Accepted trade.")
	}

	def items(Player player) {
		val trade = player.trade
		val cloned = trade.clone

		trade.clear

		return cloned.items
	}

	override test(ButtonActionEvent event) {
		event.id == 3546
	}

}
