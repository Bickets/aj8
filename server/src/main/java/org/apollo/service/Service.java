package org.apollo.service;

import org.apollo.ServerContext;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.game.sync.ClientSynchronizer;
import org.apollo.io.player.PlayerSerializerWorker;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mysql.fabric.Server;

/**
 * Represents a {@link Server} provided service.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Service {

	/**
	 * An enumeration representing the state of a service.
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	protected enum State {
		START, END
	}

	/**
	 * A {@link Multimap} of {@link State}s to {@link ServiceListener}s.
	 */
	private final Multimap<State, ServiceListener> listeners = HashMultimap.create();

	/**
	 * The context of the {@link Server} this service is being provided by.
	 */
	private ServerContext context;

	/**
	 * Sets the current context of the {@link Server} this service is provided
	 * by.
	 */
	public final void setContext(ServerContext context) {
		this.context = context;
	}

	/**
	 * Returns the {@link ServerContext} this service is provided by.
	 */
	public ServerContext getContext() {
		return context;
	}

	/**
	 * Checks if the specified service class exists.
	 *
	 * @param clazz The services class.
	 * @return {@code true} if and only if the service exists otherwise
	 *         {@code false}.
	 */
	public <T extends Service> boolean serviceExists(Class<T> clazz) {
		return context.serviceExists(clazz);
	}

	/**
	 * Returns the {@link Service} for the specified service class.
	 *
	 * @param clazz The services class.
	 * @return The {@link Service} object for the specified service class.
	 */
	public <T extends Service> T getService(Class<T> clazz) {
		return context.getService(clazz);
	}

	/**
	 * Returns this servers file system.
	 */
	public FileSystem getFileSystem() {
		return context.getFileSystem();
	}

	/**
	 * Returns this servers world.
	 */
	public World getWorld() {
		return context.getWorld();
	}

	/**
	 * Returns this servers message translator.
	 */
	public MessageTranslator getMessageTranslator() {
		return context.getMessageTranslator();
	}

	/**
	 * Returns this servers player serializer worker.
	 */
	public PlayerSerializerWorker getSerializerWorker() {
		return context.getSerializerWorker();
	}

	/**
	 * Returns this servers client synchronizer.
	 */
	public ClientSynchronizer getClientSynchronizer() {
		return context.getClientSynchronizer();
	}

	/**
	 * Notifies the specified {@link ServiceListener} that this service has been
	 * started.
	 *
	 * @param listener The listener to notify.
	 * @see {@link State#START}
	 */
	public void onStart(ServiceListener listener) {
		on(State.START, listener);
	}

	/**
	 * Notifies the specified {@link ServiceListener} that the state has
	 * changed, as specified by {@code state}.
	 *
	 * @param state The state this service has changed to.
	 * @param listener The listener to notify.
	 */
	public void on(State state, ServiceListener listener) {
		listeners.put(state, listener);
	}

	/**
	 * Notifies all {@link #listeners} that this services state has changed, as
	 * specified by {@code state}.
	 *
	 * @param state The state this service has changed to.
	 */
	private void executeListeners(State state) {
		listeners.get(state).forEach(initializer -> initializer.execute());
	}

	/**
	 * Initializes this service.
	 */
	public abstract void init();

	/**
	 * Notifies all listeners that this service has started,
	 */
	public void start() {
		executeListeners(State.START);
	}

}