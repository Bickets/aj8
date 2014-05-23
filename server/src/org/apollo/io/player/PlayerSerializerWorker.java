package org.apollo.io.player;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.Player;
import org.apollo.io.player.bin.BinaryPlayerSerializer;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.session.GameSession;
import org.apollo.net.session.LoginSession;
import org.apollo.util.NamedThreadFactory;

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
     * The default player serializer if one was not instantiated.
     */
    private static final PlayerSerializer DEFAULT_SERIALIZER = new BinaryPlayerSerializer();

    /**
     * An instance of {@link Logger} used to log information to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(PlayerSerializerWorker.class.getName());

    /**
     * The player serializer.
     */
    private final PlayerSerializer serializer;

    /**
     * Constructs a new {@link PlayerSerializerWorker} with the default
     * serializer.
     * 
     * @see {@link #DEFAULT_SERIALIZER}.
     */
    public PlayerSerializerWorker() {
	this(DEFAULT_SERIALIZER);
    }

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
    private final ExecutorService executor = Executors.newCachedThreadPool(new NamedThreadFactory("LoginService"));

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
		LOGGER.log(Level.SEVERE, "Unable to load players game.", e);
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
		LOGGER.log(Level.SEVERE, "Unable to save players game.", e);
	    } finally {
		session.handlePlayerSaverResponse();
	    }
	});
    }

}