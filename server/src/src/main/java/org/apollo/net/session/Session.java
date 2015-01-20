package org.apollo.net.session;

import io.netty.channel.ChannelHandlerContext;

/**
 * A session which is used as the attachment of a {@link ChannelHandlerContext}
 * in Netty.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Session {

	/**
	 * This sessions channel context.
	 */
	private final ChannelHandlerContext ctx;

	/**
	 * Creates a session for the specified channel context.
	 *
	 * @param ctx This sessions channel context.
	 */
	public Session(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * Returns the context of this sessions channel.
	 */
	protected final ChannelHandlerContext ctx() {
		return ctx;
	}

	/**
	 * Processes a message received from the channel.
	 *
	 * @param message The message.
	 */
	public abstract void messageReceived(Object message) throws Exception;

	/**
	 * Destroys this session.
	 */
	public void destroy() {
		/* Intended to be overridden. */
	}

}