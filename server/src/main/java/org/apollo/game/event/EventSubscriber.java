package org.apollo.game.event;

import com.google.common.eventbus.Subscribe;

/**
 * Represents a single subscriber for some {@link Event}.
 * 
 * This is a functional interface whose functional method is
 * {@link #subscribe(Event)}
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <E> The type of event to the subscriber.
 */
@FunctionalInterface
public interface EventSubscriber<E extends Event> {

    /**
     * A handler method for the specified event. Handler methods are denoted by
     * being marked with the {@link Subscribe} annotation, having the
     * {@code public} visibility modifier and return the type of {@code void}.
     * Handler methods may not contain more than one argument due to the use of
     * reflection for invoking some handler method after an event has been
     * posted.
     * 
     * <p>
     * It is not recommended to create explicit subscribe-able methods for
     * non-event types.
     * </p>
     * 
     * @param event The event to subscribe.
     */
    @Subscribe
    void subscribe(E event);

}