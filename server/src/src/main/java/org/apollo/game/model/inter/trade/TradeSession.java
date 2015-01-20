package org.apollo.game.model.inter.trade;

import java.util.List;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * Represents a trade session between two {@link Player}s.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeSession {

	/**
	 * The player who owns this trade session.
	 */
	private final Player player;

	/**
	 * The other player in this trade session.
	 */
	private final Player other;

	/**
	 * The current trade status this trade session is in.
	 */
	private TradeStatus status = TradeStatus.INITIALIZED;

	/**
	 * The current stage of this trade session.
	 */
	private TradeStage stage = TradeStage.FIRST_SCREEN;

	/**
	 * Constructs a new {@link TradeSession} with the specified players.
	 *
	 * @param player The player who owns this trade session.
	 * @param other The other player in this trade session.
	 */
	public TradeSession(Player player, Player other) {
		this.player = player;
		this.other = other;
	}

	/**
	 * Returns the owner of this trade session.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the other player in this trade session.
	 */
	public Player getOther() {
		return other;
	}

	/**
	 * Returns the current status of this trade session.
	 */
	public TradeStatus getStatus() {
		return status;
	}

	/**
	 * Returns the current stage of this trade session.
	 */
	public TradeStage getStage() {
		return stage;
	}

	/**
	 * Called when this trade session has reached a checkpoint, which updates
	 * the current status of this trade session.
	 */
	public void checkpoint(TradeStatus status) {
		this.status = status;
	}

	/**
	 * Called when this trade session has reached a checkpoint, which updates
	 * the current stage of this trade session.
	 */
	public void checkpoint(TradeStage stage) {
		this.stage = stage;
	}

	/**
	 * Attempts to verify the current trade session, returns {@code true} if and
	 * only if this trade session is valid otherwise {@code false}.
	 */
	public boolean verify() {
		Inventory cloned = player.getInventory().clone();

		List<Item> removed = cloned.removeAll(player.getTrade().getItems());
		if (removed.size() > 0) {
			player.sendMessage("There was an issue verifying this trade, please decline and try again.");
			other.sendMessage("There was an issue verifying this trade, please decline and try again.");
			return false;
		}

		List<Item> added = cloned.addAll(other.getTrade().getItems());
		if (added.size() > 0) {
			player.sendMessage("You do not have enough free inventory space to complete that transaction.");
			other.sendMessage("Other player does not have enough free inventory space.");
			return false;
		}

		return true;
	}

	/**
	 * Declines this trade session, the {@code player} declined the trade.
	 */
	public void decline() {
		player.sendMessage("Trade declined.");
		other.sendMessage("Other player declined the trade.");

		decline(player);
		decline(other);
	}

	/**
	 * Declines this trade session.
	 *
	 * @param player The player who declined this trade session.
	 */
	private void decline(Player player) {
		player.getInterfaceSet().removeListener();
		player.getInterfaceSet().close();

		player.getAttributes().setTradeSession(null);

		Inventory inventory = player.getInventory();
		Inventory trade = player.getTrade();
		Inventory cloned = trade.clone();

		trade.clear();
		inventory.addAll(cloned.getItems());

		trade.forceRefresh();
		inventory.forceRefresh();
	}

}