package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.KeepAliveEvent;
import org.apollo.game.model.Player;

/**
 * Astrect Development
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * Sep 9, 2013
 * KeepAliveEventHandler.java
 * 
 * @see org.apollo.game.event.impl.KeepAliveEvent
 * @see org.apollo.game.event.handler.EventHandler
 */
public final class KeepAliveEventHandler extends EventHandler<KeepAliveEvent> {

    @Override
    public void handle(Player player, KeepAliveEvent event) {
        // XXX: Raw event handler.
    }

}