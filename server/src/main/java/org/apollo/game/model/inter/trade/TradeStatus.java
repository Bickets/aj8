package org.apollo.game.model.inter.trade;

/**
 * Represents the current status of a trade session.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum TradeStatus {

    /**
     * Signals that the trade session has been initialized.
     */
    INITIALIZED,

    /**
     * Signals that the trade session is updating items on the trade interface.
     */
    UPDATING_ITEMS,

    /**
     * Signals that both parties within a single trade session have accepted the
     * first screen.
     */
    ACCEPTED_FIRST,

    /**
     * Signals that the trade session is verifying the performed actions.
     */
    VERIFYING,

    /**
     * Signals that both parties within a single trade session have accepted the
     * second screen.
     */
    ACCEPTED_SECOND

}