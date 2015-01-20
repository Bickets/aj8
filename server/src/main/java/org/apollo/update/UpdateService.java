package org.apollo.update;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apollo.service.Service;

/**
 * A class which services file requests.
 *
 * @author Graham
 */
public final class UpdateService extends Service {

	/**
	 * The number of threads per request type.
	 */
	private static final int THREADS_PER_REQUEST_TYPE = Runtime.getRuntime().availableProcessors();

	/**
	 * The number of request types.
	 */
	private static final int REQUEST_TYPES = 3;

	/**
	 * The total number of threads to use for this update service.
	 */
	private static final int TOTAL_THREADS = REQUEST_TYPES * THREADS_PER_REQUEST_TYPE;

	/**
	 * The executor service.
	 */
	private final ExecutorService service;

	/**
	 * A {@link Set} of request workers.
	 */
	private final Set<RequestWorker<?, ?>> workers = new HashSet<>();

	/**
	 * The update dispatcher.
	 */
	private final UpdateDispatcher dispatcher = new UpdateDispatcher();

	/**
	 * Creates the update service.
	 */
	public UpdateService() {
		service = Executors.newFixedThreadPool(TOTAL_THREADS);
	}

	/**
	 * Gets the update dispatcher.
	 *
	 * @return The update dispatcher.
	 */
	public UpdateDispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public void init() {
		for (int i = 0; i < THREADS_PER_REQUEST_TYPE; i++) {
			workers.add(new JagGrabRequestWorker(dispatcher, getFileSystem()));
			workers.add(new OnDemandRequestWorker(dispatcher, getFileSystem()));
			workers.add(new HttpRequestWorker(dispatcher, getFileSystem()));
		}

		workers.forEach(service::submit);
	}

}