package org.apollo.update;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.codec.update.OnDemandRequest;

/**
 * A class which dispatches requests to worker threads.
 *
 * @author Graham
 */
public final class UpdateDispatcher {

    /**
     * The maximum size of a queue before requests are rejected.
     */
    private static final int MAXIMUM_QUEUE_SIZE = 1024;

    /**
     * A queue for pending 'on-demand' requests.
     */
    private final BlockingQueue<ChannelRequest<OnDemandRequest>> onDemandQueue = new PriorityBlockingQueue<>();

    /**
     * A queue for pending JAGGRAB requests.
     */
    private final BlockingQueue<ChannelRequest<JagGrabRequest>> jagGrabQueue = new LinkedBlockingQueue<>();

    /**
     * A queue for pending HTTP requests.
     */
    private final BlockingQueue<ChannelRequest<HttpRequest>> httpQueue = new LinkedBlockingQueue<>();

    /**
     * Gets the next 'on-demand' request from the queue, blocking if none are
     * available.
     *
     * @return The 'on-demand' request.
     * @throws InterruptedException if the thread is interrupted.
     */
    ChannelRequest<OnDemandRequest> nextOnDemandRequest() throws InterruptedException {
	return onDemandQueue.take();
    }

    /**
     * Gets the next JAGGRAB request from the queue, blocking if none are
     * available.
     *
     * @return The JAGGRAB request.
     * @throws InterruptedException if the thread is interrupted.
     */
    ChannelRequest<JagGrabRequest> nextJagGrabRequest() throws InterruptedException {
	return jagGrabQueue.take();
    }

    /**
     * Gets the next HTTP request from the queue, blocking if none are
     * available.
     *
     * @return The HTTP request.
     * @throws InterruptedException if the thread is interrupted.
     */
    ChannelRequest<HttpRequest> nextHttpRequest() throws InterruptedException {
	return httpQueue.take();
    }

    /**
     * Dispatches an 'on-demand' request.
     *
     * @param channel The channel.
     * @param request The request.
     */
    public void dispatch(Channel channel, OnDemandRequest request) {
	if (onDemandQueue.size() >= MAXIMUM_QUEUE_SIZE) {
	    channel.close();
	    return;
	}
	onDemandQueue.add(new ChannelRequest<>(channel, request));
    }

    /**
     * Dispatches a JAGGRAB request.
     *
     * @param channel The channel.
     * @param request The request.
     */
    public void dispatch(Channel channel, JagGrabRequest request) {
	if (jagGrabQueue.size() >= MAXIMUM_QUEUE_SIZE) {
	    channel.close();
	    return;
	}
	jagGrabQueue.add(new ChannelRequest<>(channel, request));
    }

    /**
     * Dispatches a HTTP request.
     *
     * @param channel The channel.
     * @param request The request.
     */
    public void dispatch(Channel channel, HttpRequest request) {
	if (httpQueue.size() >= MAXIMUM_QUEUE_SIZE) {
	    channel.close();
	    return;
	}
	httpQueue.add(new ChannelRequest<>(channel, request));
    }

}