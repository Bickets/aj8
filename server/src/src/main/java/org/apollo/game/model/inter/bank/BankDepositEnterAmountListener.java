package org.apollo.game.model.inter.bank;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.EnterAmountListener;

/**
 * An {@link EnterAmountListener} for depositing items.
 *
 * @author Graham
 */
public final class BankDepositEnterAmountListener implements EnterAmountListener {

	/**
	 * The player who is interacting with the bank.
	 */
	private final Player player;

	/**
	 * The item slot.
	 */
	private final int slot;

	/**
	 * The item id.
	 */
	private final int id;

	/**
	 * Creates the bank deposit amount listener.
	 *
	 * @param player The player.
	 * @param slot The slot.
	 * @param id The id.
	 */
	public BankDepositEnterAmountListener(Player player, int slot, int id) {
		this.player = player;
		this.slot = slot;
		this.id = id;
	}

	@Override
	public void amountEntered(int amount) {
		if (player.getInterfaceSet().contains(BankConstants.BANK_WINDOW_ID)) {
			BankUtils.deposit(player, slot, id, amount);
		}
	}

}