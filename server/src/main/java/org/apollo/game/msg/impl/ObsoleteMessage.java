package org.apollo.game.msg.impl;

import io.netty.buffer.ByteBuf;

import org.apollo.game.msg.Message;

/**
 * A message which marks a specified packet obsolete, it is either unused or
 * serves no purpose in our implementation/
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ObsoleteMessage implements Message {

	/**
	 * Represents the payload of this message, the data it sends.
	 */
	private final ByteBuf payload;

	/**
	 * Constructs a new {@link ObsoleteMessage} with the specified payload.
	 * 
	 * @param payload The payload of this message.
	 */
	public ObsoleteMessage(ByteBuf payload) {
		this.payload = payload;
	}

	/**
	 * Returns the payload of this message.
	 */
	public ByteBuf getPayload() {
		return payload;
	}

}