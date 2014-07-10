package org.apollo.game.event;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An event provider provides support for dynamic {@link Event} posting,
 * depriving and providing {@link EventSubscriber}s
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public interface EventProvider {

    /**
     * Provides an {@link EventSubscriber} for the specified event.
     * 
     * @param subscriber The subscriber to provide.
     */
    <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber);

    /**
     * Deprives an {@link EventSubscriber} for the specified event.
     * 
     * @param subscriber The subscriber to deprive.
     */
    <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber);

    /**
     * Posts an {@link Event}, notifying all provided subscribers.
     * 
     * @param event The event to post.
     */
    <E extends Event> void post(E event);

    /**
     * Posts an {@link Event}, if and only if the specified predicate tests
     * {@code true}, notifying all provided subscribers.
     * 
     * @param event The event to post, may not be {@code null}.
     * @param predicate The events predicate, may not be {@code null}.
     */
    default <E extends Event> void post(E event, EventPredicate<E> predicate) {
	checkNotNull(predicate);

	if (predicate.test(event)) {
	    post(event);
	}
    }

}