package org.apollo.game.model.inter.bank;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceListener;
import org.apollo.game.model.inv.InventoryListener;

/**
 * An {@link InterfaceListener} which removes the {@link InventoryListener}s
 * when the bank is closed.
 *
 * @author Graham
 */
public final class BankInterfaceListener implements InterfaceListener {

    /**
     * The player who is interacting with the bank.
     */
    private final Player player;

    /**
     * The inventory's inventory listener.
     */
    private final InventoryListener invListener;

    /**
     * The bank's inventory listener.
     */
    private final InventoryListener bankListener;

    /**
     * Creates the bank interface listener.
     *
     * @param player The player.
     * @param invListener The inventory listener.
     * @param bankListener The bank listener.
     */
    public BankInterfaceListener(Player player, InventoryListener invListener, InventoryListener bankListener) {
	this.player = player;
	this.invListener = invListener;
	this.bankListener = bankListener;
    }

    @Override
    public void close() {
	player.getInventory().removeListener(invListener);
	player.getBank().removeListener(bankListener);
    }

}