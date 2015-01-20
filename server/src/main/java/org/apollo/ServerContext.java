package org.apollo;

import org.apollo.fs.FileSystem;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.game.sync.ClientSynchronizer;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.service.Service;

/**
 * Represents the context of this {@link Server}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ServerContext {

	/**
	 * The {@link Server} this {@link ServerContext} represents.
	 */
	private final Server server;

	/**
	 * Constructs a new {@link ServerContext} with the specified {@link Server}.
	 *
	 * @param server The server this context represents.
	 */
	public ServerContext(Server server) {
		this.server = server;
	}

	/**
	 * Checks if the specified service class exists.
	 *
	 * @param clazz The services class.
	 * @return {@code true} if and only if the service exists otherwise
	 *         {@code false}.
	 */
	public <T extends Service> boolean serviceExists(Class<T> clazz) {
		return server.serviceExists(clazz);
	}

	/**
	 * Returns the {@link Service} for the specified service class.
	 *
	 * @param clazz The services class.
	 * @return The {@link Service} object for the specified service class.
	 */
	public <T extends Service> T getService(Class<T> clazz) {
		return server.getService(clazz);
	}

	/**
	 * Returns this servers file system.
	 */
	public FileSystem getFileSystem() {
		return server.getFileSystem();
	}

	/**
	 * Returns this servers world.
	 */
	public World getWorld() {
		return server.getWorld();
	}

	/**
	 * Returns this servers message translator.
	 */
	public MessageTranslator getMessageTranslator() {
		return server.getMessageTranslator();
	}

	/**
	 * Returns this servers player serializer worker.
	 */
	public PlayerSerializerWorker getSerializerWorker() {
		return server.getSerializerWorker();
	}

	/**
	 * Returns this servers client synchronizer.
	 */
	public ClientSynchronizer getClientSynchronizer() {
		return server.getClientSynchronizer();
	}

}