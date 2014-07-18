package org.apollo.game.event;

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

}