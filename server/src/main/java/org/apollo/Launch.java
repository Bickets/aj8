package org.apollo;

import static org.apollo.net.NetworkConstants.HTTP_PORT;
import static org.apollo.net.NetworkConstants.JAGGRAB_PORT;
import static org.apollo.net.NetworkConstants.SERVICE_PORT;

import java.io.IOException;

import org.apollo.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The entry point of this application, this class is responsible for
 * initializing the Apollo server.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
final class Launch {

	/**
	 * The directory where the {@link FileSystem} is located.
	 */
	private static final String FILE_SYSTEM_DIR = "data/fs";

	/**
	 * The entry point of this application, which implements the command-line
	 * interface.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Launch.class);

		try {
			Server server = new Server(FileSystem.create(FILE_SYSTEM_DIR));
			server.init();
			server.start();
			server.bind(SERVICE_PORT, HTTP_PORT, JAGGRAB_PORT);
		} catch (IOException reason) {
			logger.error("Fatal error whilst initializing the server", reason);
		}
	}

}