package org.apollo.service;

import org.apollo.ServerContext;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.game.sync.ClientSynchronizer;
import org.apollo.io.player.PlayerSerializerWorker;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public abstract class Service {

	protected enum State {
		START
	}

	private final Multimap<State, ServiceListener> listeners = HashMultimap.create();

	private ServerContext context;

	public void setContext(ServerContext context) {
		this.context = context;
	}

	public ServerContext getContext() {
		return context;
	}

	public <T extends Service> boolean serviceExists(Class<T> clazz) {
		return context.serviceExists(clazz);
	}

	public <T extends Service> T getService(Class<T> clazz) {
		return context.getService(clazz);
	}

	public FileSystem getFileSystem() {
		return context.getFileSystem();
	}

	public World getWorld() {
		return context.getWorld();
	}

	public MessageTranslator getMessageTranslator() {
		return context.getMessageTranslator();
	}

	public PlayerSerializerWorker getSerializerWorker() {
		return context.getSerializerWorker();
	}

	public ClientSynchronizer getClientSynchronizer() {
		return context.getClientSynchronizer();
	}

	public void onStart(ServiceListener listener) {
		on(State.START, listener);
	}

	public void on(State state, ServiceListener listener) {
		listeners.put(state, listener);
	}

	private void executeListeners(State state) {
		listeners.get(state).forEach(initializer -> initializer.execute());
	}

	public abstract void init();

	public void start() {
		executeListeners(State.START);
	}

}