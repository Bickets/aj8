package plugin.trade

import org.apollo.game.model.Player
import org.apollo.game.model.inter.trade.TradeInterfaceListener
import org.apollo.game.model.inter.trade.TradeInventoryListener
import org.apollo.game.model.inter.trade.TradeSession
import org.apollo.game.model.inv.InventoryListener
import org.apollo.game.model.inv.SynchronizationInventoryListener

import static org.apollo.game.model.InventoryConstants.*
import static org.apollo.game.model.inter.trade.TradeConstants.*

class Trade {

	def static openTrade(Player player, Player other) {
		var session = player.fields.tradeSession
		var otherSession = other.fields.tradeSession

		if (session != null || otherSession != null) {
			return
		}

		session = new TradeSession(player, other)
		otherSession = new TradeSession(other, player)

		val updateListener = new TradeInventoryListener(session, otherSession)

		player.register(updateListener, new SynchronizationInventoryListener(other, OTHER_TRADE_INVENTORY_ID))
		other.register(updateListener, new SynchronizationInventoryListener(player, OTHER_TRADE_INVENTORY_ID))

		player.initialize(TRADE_WINDOW_ID, SIDEBAR_ID)
		other.initialize(TRADE_WINDOW_ID, SIDEBAR_ID)

		player.fields.tradeSession = session
		other.fields.tradeSession = otherSession
	}

	def static initialize(Player player, int windowId, int sidebarId) {
		val invListener = new SynchronizationInventoryListener(player, TRADE_SIDEBAR_INVENTORY_ID)
		val tradeInvListener = new SynchronizationInventoryListener(player, TRADE_INVENTORY_ID)

		player.inventory.addListener(invListener)
		player.trade.addListener(tradeInvListener)

		player.inventory.forceRefresh
		player.trade.forceRefresh

		val interListener = new TradeInterfaceListener(player, invListener, tradeInvListener)
		player.interfaceSet.openWindowWithSidebar(interListener, windowId, sidebarId)
	}

	def static register(Player player, InventoryListener updateListener, InventoryListener syncListener) {
		player.trade.addListener(updateListener)
		player.trade.addListener(syncListener)
	}

}
