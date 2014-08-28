package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * Represents the message sent to open the welcome screen interface.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class WelcomeScreenMessage extends Message {

    /**
     * Represents the number of days since the last time your recovery questions
     * were changed.
     */
    private final int lastRecoveryChange;

    /**
     * Represents the number of unread messages you have.
     */
    private final int unreadMessages;

    /**
     * Will warn a player if they are logging in a non-members server with their
     * members account.
     */
    private final boolean membersWarning;

    /**
     * Represents the last login address, as an {@code int}.
     */
    private final int lastAddress;

    /**
     * Represents the number of days since you last logged in.
     */
    private final int lastLogin;

    /**
     * Constructs a new {@link WelcomeScreenMessage} with the specied last
     * recovery change, unread messages, members warning, last address and last
     * login.
     *
     * @param lastRecovryChange The last time the recovery questions were
     *            changed.
     * @param unreadMessages The amount of unread messages.
     * @param membersWarning Whether or not to warn a member of logging into a
     *            free server.
     * @param lastAddress The last address used to login.
     * @param lastLogin The amount of days since the last login.
     */
    public WelcomeScreenMessage(int lastRecoveryChange, int unreadMessages, boolean membersWarning, int lastAddress, int lastLogin) {
	this.lastRecoveryChange = lastRecoveryChange;
	this.unreadMessages = unreadMessages;
	this.membersWarning = membersWarning;
	this.lastAddress = lastAddress;
	this.lastLogin = lastLogin;
    }

    /**
     * Returns the last recovery change.
     */
    public int getLastRecoveryChange() {
	return lastRecoveryChange;
    }

    /**
     * Returns the amount of unread messages.
     */
    public int getUnreadMessages() {
	return unreadMessages;
    }

    /**
     * Returns whether or not we should warn a member for logging into the wrong
     * server.
     */
    public boolean isMembersWarning() {
	return membersWarning;
    }

    /**
     * Returns the last login address.
     */
    public int getLastAddress() {
	return lastAddress;
    }

    /**
     * Returns the amount of days since last login.
     */
    public int getLastLogin() {
	return lastLogin;
    }

}