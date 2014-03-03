
package org.apollo.service;

/**
 * Represents a service that the server provides.
 * @author Graham
 */
public abstract class Service
{

	/**
	 * Starts the service.
	 */
	public abstract void start();


	@Override
	public final String toString()
	{
		return getClass().getSimpleName();
	}

}
