package buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Inventory
import org.apollo.game.model.Player
import org.apollo.game.model.inter.InterfaceListener
import org.apollo.game.model.inter.trade.TradeStage
import org.apollo.game.model.inter.trade.TradeStatus
import org.apollo.game.msg.impl.SetInterfaceTextMessage
import org.apollo.util.NumberUtil

import static org.apollo.game.model.inter.trade.TradeConstants.*
import static org.apollo.game.model.inter.trade.TradeStage.*
import static org.apollo.game.model.inter.trade.TradeStatus.*

@SubscribesTo(ButtonActionEvent)
class AcceptFirstScreenTrade implements EventSubscriber<ButtonActionEvent> {

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

		if (validStatus(session.status) && validStatus(otherSession.status) && validStage(session.stage) &&
			validStage(otherSession.stage)) {
			plr.send(new SetInterfaceTextMessage(FIRST_SCREEN_MESSAGE_ID, "Waiting for other player..."))
			other.send(new SetInterfaceTextMessage(FIRST_SCREEN_MESSAGE_ID, "Other player has accepted."))

			session.checkpoint(ACCEPTED_FIRST)
		}

		if (session.status == ACCEPTED_FIRST && otherSession.status == ACCEPTED_FIRST) {
			session.checkpoint(VERIFYING)
			otherSession.checkpoint(VERIFYING)

			if (session.verify()) {
				session.checkpoint(SECOND_SCREEN)
				otherSession.checkpoint(SECOND_SCREEN)

				openSecond(plr, other)
			}
		}
	}

	def validStatus(TradeStatus status) {
		status == INITIALIZED || status == ACCEPTED_FIRST || status == UPDATING_ITEMS
	}

	def validStage(TradeStage stage) {
		stage == FIRST_SCREEN
	}

	def openSecond(Player player, Player other) {
		player.send(new SetInterfaceTextMessage(VALUES_MESSAGE_ID, buildSecondScreenMessage(player.trade)))
		other.send(new SetInterfaceTextMessage(VALUES_MESSAGE_ID, buildSecondScreenMessage(other.trade)))

		player.send(new SetInterfaceTextMessage(OTHER_VALUES_MESSAGE_ID, buildSecondScreenMessage(other.trade)))
		other.send(new SetInterfaceTextMessage(OTHER_VALUES_MESSAGE_ID, buildSecondScreenMessage(player.trade)))

		player.openSecondWindow
		other.openSecondWindow
	}

	def openSecondWindow(Player player) {
		player.send(new SetInterfaceTextMessage(SECOND_SCREEN_TITLE_ID, "Are you sure you want to make this trade?"))
		player.interfaceSet.openWindowWithSidebar(new SecondScreenInterfaceListener(player), CONFIRM_TRADE_WINDOW_ID,
			CONFIRM_SIDEBAR_ID)
	}

	@Data static class SecondScreenInterfaceListener implements InterfaceListener {
		val Player player

		override close() {
			val session = player.fields.tradeSession
			val otherSession = session.other.fields.tradeSession

			if (validStage(session.stage) && validStage(otherSession.stage)) {
				session.decline();
			}
		}

		def validStage(TradeStage stage) {
			stage != FINISHED
		}
	}

	def buildSecondScreenMessage(Inventory inventory) {
		if (inventory.freeSlots == 28) {
			return "Absolutely nothing!"
		}

		val items = inventory.items
		val bldr = new StringBuilder
		val filtered = items.filter[it != null]

		filtered.forEach [
			bldr.append("@or1@").append(it.definition.name)
			if (it.amount > 1) {
				bldr.append(" @whi@x ")
				bldr.append(if(it.amount > 1000) NumberUtil.format(it.amount) else it.amount)
			}
			bldr.append("\\n")
		]

		return bldr.toString
	}

	override test(ButtonActionEvent event) {
		event.id == 3420 && event.player.interfaceSet.contains(TRADE_WINDOW_ID, SIDEBAR_ID)
	}

}
