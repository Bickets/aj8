package org.apollo.service;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A class which manages {@link Service}s.
 *
 * @author Graham
 */
public final class ServiceManager {

    /**
     * The logger for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

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
	logger.fine("Registering service: " + service + "...");
	services.add(service);
    }

    /**
     * Starts all the services.
     */
    public void startAll() {
	logger.info("Starting services...");
	for (Service service : services) {
	    logger.fine("Starting service: " + service + "...");
	    service.start();
	}
    }

}
