package org.apollo.game.event;

/**
 * Represents a single subscriber for some {@link Event}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <E> The type of event to the subscriber.
 */
public interface EventSubscriber<E extends Event> extends EventPredicate<E> {

    /**
     * A handler method which executes event specific logic if and only if
     * {@link #test(Event)} returns {@code true}.
     * 
     * @param event The event to subscribe.
     */
    void subscribe(E event);

    @Override
    default boolean test(E event) {
	// Method intended to be overridden.
	return true;
    }

}