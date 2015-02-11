package org.apollo.net.codec.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apollo.game.msg.Message;
import org.apollo.game.msg.MessageTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link MessageToMessageDecoder} that decodes {@link GamePacket}s into
 * {@link Message}s.
 *
 * @author Graham
 */
public final class GameMessageDecoder extends MessageToMessageDecoder<GamePacket> {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(GameMessageDecoder.class);

	/**
	 * The message translator.
	 */
	private final MessageTranslator translator;

	/**
	 * Constructs a new {@link GameMessageDecoder}.
	 *
	 * @param translator The message translator.
	 */
	public GameMessageDecoder(MessageTranslator translator) {
		this.translator = translator;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, GamePacket msg, List<Object> out) {
		Message message = translator.decode(msg);

		if (message == null) {
			logger.warn("Missing message decoder for {}", msg.toString());
			return;
		}

		out.add(message);
	}

}