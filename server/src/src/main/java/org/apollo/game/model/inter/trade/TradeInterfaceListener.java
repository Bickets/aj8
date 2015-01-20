package org.apollo.game.model.inter.trade;

import static org.apollo.game.model.inter.trade.TradeStage.SECOND_SCREEN;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceListener;
import org.apollo.game.model.inv.InventoryListener;

/**
 * A trade interface listener which removes inventory listeners when the trade
 * window is closed.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeInterfaceListener implements InterfaceListener {

	/**
	 * The player who is within a trade.
	 */
	private final Player player;

	/**
	 * The inventory's inventory listener.
	 */
	private final InventoryListener invListener;

	/**
	 * The trade's inventory listener.
	 */
	private final InventoryListener tradeListener;

	/**
	 * Constructs a new {@link TradeInterfaceListener} with the specified
	 * player, inventory listener and trade listener.
	 *
	 * @param player The player.
	 * @param invListener The inventory listener.
	 * @param tradeListener The trade listener.
	 */
	public TradeInterfaceListener(Player player, InventoryListener invListener, InventoryListener tradeListener) {
		this.player = player;
		this.invListener = invListener;
		this.tradeListener = tradeListener;
	}

	@Override
	public void close() {
		TradeSession session = player.getAttributes().getTradeSession();
		TradeSession otherSession = session.getOther().getAttributes().getTradeSession();

		if (validStage(session.getStage()) && validStage(otherSession.getStage())) {
			session.decline();
		}

		player.getInventory().removeListener(invListener);
		player.getTrade().removeListener(tradeListener);
	}

	/**
	 * Returns {@code true} if and only if the specified {@link TradeStage} is
	 * valid otherwise {@code false}.
	 */
	private boolean validStage(TradeStage stage) {
		return stage != SECOND_SCREEN;
	}

}