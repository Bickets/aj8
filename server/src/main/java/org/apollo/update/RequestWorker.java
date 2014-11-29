package org.apollo.update;

import io.netty.channel.Channel;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base class for request workers.
 *
 * @author Graham
 * @param <T> The type of request.
 * @param <P> The type of provider.
 */
public abstract class RequestWorker<T, P> implements Runnable {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(RequestWorker.class);

	/**
	 * The resource provider.
	 */
	private final P provider;

	/**
	 * The update dispatcher.
	 */
	private final UpdateDispatcher dispatcher;

	/**
	 * A flag indicating if the worker should be running.
	 */
	private boolean running = true;

	/**
	 * Creates the request worker with the specified file system.
	 *
	 * @param dispatcher The update dispatcher.
	 * @param provider The resource provider.
	 */
	public RequestWorker(UpdateDispatcher dispatcher, P provider) {
		this.provider = provider;
		this.dispatcher = dispatcher;
	}

	/**
	 * Stops this worker. The worker's thread may need to be interrupted.
	 */
	public final void stop() {
		synchronized (this) {
			running = false;
		}
	}

	@Override
	public final void run() {
		for (;;) {
			synchronized (this) {
				if (!running) {
					break;
				}
			}

			ChannelRequest<T> request;
			try {
				request = nextRequest(dispatcher);
			} catch (InterruptedException e) {
				continue;
			}

			Channel channel = request.getChannel();

			try {
				service(provider, channel, request.getRequest());
			} catch (IOException reason) {
				channel.close();
				logger.error("Error whilst servicing provider", reason);
			}
		}
	}

	/**
	 * Gets the next request.
	 *
	 * @param dispatcher The dispatcher.
	 * @return The next request.
	 * @throws InterruptedException if the thread is interrupted.
	 */
	protected abstract ChannelRequest<T> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException;

	/**
	 * Services a request.
	 *
	 * @param provider The resource provider.
	 * @param channel The channel.
	 * @param request The request to service.
	 * @throws IOException if an I/O error occurs.
	 */
	protected abstract void service(P provider, Channel channel, T request) throws IOException;

}