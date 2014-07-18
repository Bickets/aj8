package org.apollo.game.model.inter;

/**
 * Listens to interface-related events.
 *
 * @author Graham
 */
@FunctionalInterface
public interface InterfaceListener {

    /**
     * Called when the interface has been closed.
     */
    void interfaceClosed();

}
