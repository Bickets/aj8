package org.apollo.game.event;

import java.util.function.Predicate;

/**
 * Represents an {@link Event} predicate (a {@code boolean} valued function).
 * 
 * This is a functional interface whose functional method is
 * {@link #test(Event)}
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <E> The type of event to the predicate.
 */
@FunctionalInterface
public interface EventPredicate<E extends Event> extends Predicate<E> {

}