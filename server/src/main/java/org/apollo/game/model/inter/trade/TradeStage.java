package org.apollo.game.model.inter.trade;

/**
 * Represents the current stage this trade session is at.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum TradeStage {

    /**
     * Signals that this trade session is at the first screen.
     */
    FIRST_SCREEN,

    /**
     * Signals that this trade session is at the second screen.
     */
    SECOND_SCREEN,

    /**
     * Signals that this trade session is finished.
     */
    FINISHED

}