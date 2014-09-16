package org.apollo.game.sync.task;

import org.apollo.game.sync.ClientSynchronizer;

/**
 * Represents some task that must be completed during the client synchronization
 * process (see {@link ClientSynchronizer} for more information).
 *
 * <p>
 * This is a functional interface whose functional method is {@link #run()}
 *
 * @author Graham
 */
@FunctionalInterface
public interface SynchronizationTask extends Runnable {

}