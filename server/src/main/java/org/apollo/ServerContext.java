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

	private final Server server;

	public ServerContext(Server server) {
		this.server = server;
	}

	public <T extends Service> boolean serviceExists(Class<T> clazz) {
		return server.serviceExists(clazz);
	}

	public <T extends Service> T getService(Class<T> clazz) {
		return server.getService(clazz);
	}

	public FileSystem getFileSystem() {
		return server.getFileSystem();
	}

	public World getWorld() {
		return server.getWorld();
	}

	public MessageTranslator getMessageTranslator() {
		return server.getMessageTranslator();
	}

	public PlayerSerializerWorker getSerializerWorker() {
		return server.getSerializerWorker();
	}

	public ClientSynchronizer getClientSynchronizer() {
		return server.getClientSynchronizer();
	}

}