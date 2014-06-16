package org.apollo.game.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Manages internal logic for a specified {@link Event}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <E> The {@link Event} we are subscribing.
 */
public interface EventSubscriber<E extends Event> {

    /**
     * Manages the logic for the specified event.
     * 
     * @param event The event to manage.
     * @see {@link Subscribe} Marks a method as an event handler as used by some
     *      {@link EventBus}.
     */
    @Subscribe
    void subscribe(E event);

}