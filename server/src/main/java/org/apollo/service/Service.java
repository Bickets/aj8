package org.apollo.service;

/**
 * Represents a service that the server provides.
 *
 * <p>
 * This is a functional interface whose functional method is {@link #start()}
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@FunctionalInterface
public interface Service {

	/**
	 * Starts the service.
	 */
	void start();

}