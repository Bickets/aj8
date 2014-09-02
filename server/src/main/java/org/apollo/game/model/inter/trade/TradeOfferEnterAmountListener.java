package org.apollo.game.model.inter.trade;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.EnterAmountListener;

/**
 * An {@link EnterAmountListener} for offernig items in a trade.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeOfferEnterAmountListener implements EnterAmountListener {

    /**
     * The player who is offering the items in the trade.
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
     * Constructs a new {@link TradeOfferEnterAmountListener} with the specified
     * player, item slot and item id.
     *
     * @param player The player.
     * @param slot The slot.
     * @param id The id.
     */
    public TradeOfferEnterAmountListener(Player player, int slot, int id) {
	this.player = player;
	this.slot = slot;
	this.id = id;
    }

    @Override
    public void amountEntered(int amount) {
	if (player.getInterfaceSet().contains(TradeConstants.TRADE_WINDOW_ID)) {
	    TradeUtils.offer(player, slot, id, amount);
	}
    }

}