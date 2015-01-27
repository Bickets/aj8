package plugin.bank

import org.apollo.game.model.Player
import org.apollo.game.model.inter.bank.BankConstants
import org.apollo.game.model.inter.bank.BankInterfaceListener
import org.apollo.game.model.inv.InventoryConstants
import org.apollo.game.model.inv.SynchronizationInventoryListener

class Bank {

	def static openBank(Player player) {
		val invListener = new SynchronizationInventoryListener(player, InventoryConstants.BANK_SIDEBAR_INVENTORY_ID)
		val bankListener = new SynchronizationInventoryListener(player, InventoryConstants.BANK_INVENTORY_ID)

		player.inventory.addListener(invListener)
		player.bank.addListener(bankListener)

		player.inventory.forceRefresh
		player.bank.forceRefresh

		val interListener = new BankInterfaceListener(player, invListener, bankListener)

		player.interfaceSet.openWindowWithSidebar(interListener, BankConstants.BANK_WINDOW_ID,
			BankConstants.SIDEBAR_ID)
	}

}
