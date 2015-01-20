package org.apollo.service;

/**
 * A listener which listens for {@link Service} events.
 *
 * <p>
 * This is a functional interface whose functional method is {@link #execute()}
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@FunctionalInterface
public interface ServiceListener {

	/**
	 * Executes this service event.
	 */
	void execute();

}