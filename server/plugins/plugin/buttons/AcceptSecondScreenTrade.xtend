package plugin.buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.trade.TradeStage
import org.apollo.game.model.inter.trade.TradeStatus
import org.apollo.game.msg.impl.SetInterfaceTextMessage
import plugin.Plugin

import static org.apollo.game.model.inter.trade.TradeConstants.*
import static org.apollo.game.model.inter.trade.TradeStage.*
import static org.apollo.game.model.inter.trade.TradeStatus.*

@SubscribesTo(ButtonActionEvent)
class AcceptSecondScreenTrade extends Plugin implements EventSubscriber<ButtonActionEvent> {

	override subscribe(ButtonActionEvent event) {
		val plr = event.player
		val session = plr.fields.tradeSession

		if (session == null) {
			return
		}

		val other = session.other

		if (other == null || session.player != plr || other == plr) {
			return
		}

		val otherSession = other.fields.tradeSession

		if (otherSession == null) {
			return
		}

		if (validStatus(session.status) && validStatus(otherSession.status) && validStage(session.stage) && validStage(otherSession.stage)) {
			plr.send(new SetInterfaceTextMessage(SECOND_SCREEN_TITLE_ID, "Waiting for other player..."))
			other.send(new SetInterfaceTextMessage(SECOND_SCREEN_TITLE_ID, "Other player has accepted."))

			session.checkpoint(ACCEPTED_SECOND)
		}

		if (session.status == ACCEPTED_SECOND && otherSession.status == ACCEPTED_SECOND) {
			session.checkpoint(VERIFYING)
			otherSession.checkpoint(VERIFYING)

			if (session.verify()) {
				session.checkpoint(FINISHED)
				otherSession.checkpoint(FINISHED)

				finish(plr, other)
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
		other.inventory.addAll(player.items)
		player.inventory.addAll(other.items)
		closeInterfaces(player)
		closeInterfaces(other)
	}

	def items(Player player) {
		val trade = player.trade
		val cloned = trade.clone

		trade.clear
		trade.forceRefresh

		return cloned.items
	}

	override test(ButtonActionEvent event) {
		event.id == 3546 && event.player.interfaceSet.contains(CONFIRM_TRADE_WINDOW_ID, CONFIRM_SIDEBAR_ID)
	}

}
