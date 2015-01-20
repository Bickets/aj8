package org.apollo.game.model.inter.trade;

import static org.apollo.game.model.inter.trade.TradeConstants.TRADING_WITH_MESSAGE_ID;
import static org.apollo.game.model.inter.trade.TradeStage.FIRST_SCREEN;
import static org.apollo.game.model.inter.trade.TradeStatus.ACCEPTED_FIRST;
import static org.apollo.game.model.inter.trade.TradeStatus.INITIALIZED;
import static org.apollo.game.model.inter.trade.TradeStatus.UPDATING_ITEMS;
import static org.apollo.game.model.inter.trade.TradeStatus.VERIFYING;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.inv.InventoryAdapter;
import org.apollo.game.model.inv.InventoryListener;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * An {@link InventoryListener} which updates during a trade session.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeInventoryListener extends InventoryAdapter {

	/**
	 * The current trade session.
	 */
	private final TradeSession session;

	/**
	 * The other trade session.
	 */
	private final TradeSession otherSession;

	/**
	 * Constructs a new {@link TradeInventoryListener}.
	 *
	 * @param session The current trade session.
	 * @param otherSession The other trade session.
	 */
	public TradeInventoryListener(TradeSession session, TradeSession otherSession) {
		this.session = session;
		this.otherSession = otherSession;
	}

	@Override
	public void itemUpdated(Inventory inventory, int slot, Item item) {
		itemsUpdated(inventory);
	}

	@Override
	public void itemsUpdated(Inventory inventory) {
		Player player = session.getPlayer();
		Player other = otherSession.getPlayer();

		if (!validStatus(session.getStatus()) && !validStatus(otherSession.getStatus()) || !validStage(session.getStage()) && !validStage(otherSession.getStage())) {
			return;
		}

		if (session.getStatus() != UPDATING_ITEMS || otherSession.getStatus() != UPDATING_ITEMS) {
			session.checkpoint(UPDATING_ITEMS);
			otherSession.checkpoint(UPDATING_ITEMS);

			player.send(new SetInterfaceTextMessage(TradeConstants.FIRST_SCREEN_MESSAGE_ID, ""));
			other.send(new SetInterfaceTextMessage(TradeConstants.FIRST_SCREEN_MESSAGE_ID, ""));
		}

		player.send(new SetInterfaceTextMessage(TRADING_WITH_MESSAGE_ID, buildTitle(other)));
		other.send(new SetInterfaceTextMessage(TRADING_WITH_MESSAGE_ID, buildTitle(player)));
	}

	/**
	 * Builds and returns the current trade title for the specified
	 * {@link Player}.
	 */
	private String buildTitle(Player player) {
		Inventory inventory = player.getInventory();
		return "Trading with: " + player.getDisplayName() + " who has @gre@" + inventory.freeSlots() + " @yel@ free slots.";
	}

	/**
	 * Returns {@code true} if and only if the specified {@link TradeStatus} is
	 * valid otherwise {@code false}.
	 */
	private boolean validStatus(TradeStatus status) {
		return status == INITIALIZED || status == ACCEPTED_FIRST || status == UPDATING_ITEMS || status == VERIFYING;
	}

	/**
	 * Returns {@code true} if and only if the specified {@link TradeStage} is
	 * valid otherwise {@code false}.
	 */
	private boolean validStage(TradeStage stage) {
		return stage == FIRST_SCREEN;
	}

}