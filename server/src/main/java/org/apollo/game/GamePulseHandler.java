package org.apollo.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which handles the logic for each pulse of the {@link GameService}.
 *
 * @author Graham
 */
public final class GamePulseHandler implements Runnable {

    /**
     * The logger used to print information and debug messages to the console.
     */
    private final Logger logger = LoggerFactory.getLogger(GamePulseHandler.class);

    /**
     * The {@link GameService}.
     */
    private final GameService service;

    /**
     * Creates the game pulse handler object.
     *
     * @param service The {@link GameService}.
     */
    GamePulseHandler(GameService service) {
	this.service = service;
    }

    @Override
    public void run() {
	try {
	    service.pulse();
	} catch (Throwable t) {
	    logger.error("Exception during pulse.", t);
	}
    }

}