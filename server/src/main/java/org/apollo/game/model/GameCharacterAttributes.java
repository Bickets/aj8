package org.apollo.game.model;

import org.apollo.game.attribute.AttributeKey;
import org.apollo.game.attribute.AttributeMap;
import org.apollo.game.model.inter.trade.TradeSession;

/**
 * The sole purpose of this class is to store, get and modify attributes for
 * some {@link GameCharacter}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameCharacterAttributes {

	/**
	 * The attribute map for the {@link GameCharacter}.
	 */
	private final AttributeMap attributes;

	/**
	 * Constructs a new {@link GameCharacterAttributes} with the specified
	 * {@link AttributeMap}.
	 *
	 * @param attributes The attribute map.
	 */
	protected GameCharacterAttributes(AttributeMap attributes) {
		this.attributes = attributes;
	}

	/**
	 * An attribute key representing whether or not this character is
	 * teleporting.
	 */
	private static final AttributeKey<Boolean> TELEPORTING = AttributeKey.valueOf("teleporting", false);

	/**
	 * An attribute key representing a {@code boolean} which denotes whether or
	 * not this character is withdrawing noted items from their bank.
	 */
	private static final AttributeKey<Boolean> WITHDRAWING_NOTES = AttributeKey.valueOf("withdrawing_notes", false);

	/**
	 * An attribute key representing a {@code boolean} which denotes whether or
	 * not this characters client is within focus.
	 */
	private static final AttributeKey<Boolean> CLIENT_WINDOW_FOCUSED = AttributeKey.valueOf("client_window_focused", true);

	/**
	 * An attribute key representing a {@link TradeSession} which specifies this
	 * characters current trading session.
	 */
	private static final AttributeKey<TradeSession> TRADE_SESSION = AttributeKey.valueOf("trade_session");

	/**
	 * An attribute key representing that a request to trade has been
	 * initialized.
	 */
	private static final AttributeKey<Player> TRADE_REQUEST = AttributeKey.valueOf("trade_request");

	/**
	 * Checks if this player is currently teleporting.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isTeleporting() {
		return attributes.get(TELEPORTING);
	}

	/**
	 * Sets the teleporting attribute.
	 *
	 * @param value {@code true} if the player is teleporting, {@code false} if
	 *            not.
	 */
	public void setTeleporting(boolean value) {
		attributes.set(TELEPORTING, value);
	}

	/**
	 * Returns whether or not this character is withdrawing notes.
	 */
	public boolean isWithdrawingNotes() {
		return attributes.get(WITHDRAWING_NOTES);
	}

	/**
	 * Returns whether or not the client window is in focus.
	 */
	public boolean isClientWindowFocused() {
		return attributes.get(CLIENT_WINDOW_FOCUSED);
	}

	/**
	 * Set the client windows focused state.
	 */
	public void setClientWindowFocused(boolean flag) {
		attributes.set(CLIENT_WINDOW_FOCUSED, flag);
	}

	/**
	 * Sets the withdrawing notes state.
	 */
	public void setWithdrawingNotes(boolean flag) {
		attributes.set(WITHDRAWING_NOTES, flag);
	}

	/**
	 * Returns the current trade session.
	 */
	public TradeSession getTradeSession() {
		return attributes.get(TRADE_SESSION);
	}

	/**
	 * Sets the current trade session.
	 */
	public void setTradeSession(TradeSession session) {
		attributes.set(TRADE_SESSION, session);
	}

	/**
	 * Returns the current trade request.
	 */
	public Player getTradeRequest() {
		return attributes.get(TRADE_REQUEST);
	}

	/**
	 * Sets the current trade request.
	 */
	public void setTradeRequest(Player player) {
		attributes.set(TRADE_REQUEST, player);
	}

}