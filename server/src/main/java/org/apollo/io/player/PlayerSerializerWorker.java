package org.apollo.io.player;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.Player;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.session.GameSession;
import org.apollo.net.session.LoginSession;
import org.apollo.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages load and save requests from the specified {@link PlayerSerializer}
 * also any verification required is done here before any serialization has
 * taken place.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerSerializerWorker {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(PlayerSerializerWorker.class);

	/**
	 * The player serializer.
	 */
	private final PlayerSerializer serializer;

	/**
	 * Constructs a new {@link PlayerSerializerWorker} with specified
	 * serializer.
	 *
	 * @param serializer The player serializer.
	 */
	public PlayerSerializerWorker(PlayerSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * The {@link ExecutorService} to which workers are submitted.
	 */
	private final ExecutorService executor = Executors.newCachedThreadPool(ThreadUtil.build("PlayerSerializer"));

	/**
	 * Submits a login request.
	 *
	 * @param session The session submitting this request.
	 * @param request The login request.
	 * @param fileSystem The file system
	 * @throws IOException If some I/O exception occurs.
	 */
	public void submitLoadRequest(LoginSession session, LoginRequest request, FileSystem fileSystem) throws IOException {
		executor.submit(() -> {
			try {
				PlayerSerializerResponse response = serializer.loadPlayer(request.getCredentials());
				session.handlePlayerLoaderResponse(request, response);
			} catch (Exception e) {
				logger.error("Unable to load players game.", e);
				session.handlePlayerLoaderResponse(request, new PlayerSerializerResponse(LoginConstants.STATUS_COULD_NOT_COMPLETE));
			}
		});
	}

	/**
	 * Submits a save request.
	 *
	 * @param session The session submitting this request.
	 * @param player The player to save.
	 */
	public void submitSaveRequest(GameSession session, Player player) {
		executor.submit(() -> {
			try {
				serializer.savePlayer(player);
			} catch (Exception e) {
				logger.error("Unable to save players game.", e);
			} finally {
				session.handlePlayerSaverResponse();
			}
		});
	}

}