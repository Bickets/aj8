package org.apollo.game.model.inter;

/**
 * Listens to interface-related events.
 *
 * <p>
 * This is a functional interface whose functional method is {@link #close()}
 *
 * @author Graham
 */
@FunctionalInterface
public interface InterfaceListener {

    /**
     * Called when the interface has been closed.
     */
    void close();

}