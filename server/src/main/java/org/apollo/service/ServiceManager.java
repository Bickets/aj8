package org.apollo.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which manages {@link Service}s.
 *
 * @author Graham
 */
public final class ServiceManager {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

	/**
	 * The service set.
	 */
	private final Set<Service> services = new HashSet<>();

	/**
	 * Constructs a new {@link ServiceManager}.
	 */
	public ServiceManager() {

	}

	/**
	 * Registers a service.
	 *
	 * @param service The service to register.
	 */
	public void register(Service service) {
		services.add(service);
	}

	/**
	 * Starts all the services.
	 */
	public void startAll() {
		services.forEach(s -> s.start());
		logger.info("Started {} services.", services.size());
	}

}