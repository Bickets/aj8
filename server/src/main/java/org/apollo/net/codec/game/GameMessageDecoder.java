package org.apollo.net.codec.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apollo.game.msg.Message;
import org.apollo.game.msg.MessageTranslator;

/**
 * A {@link MessageToMessageDecoder} that decodes {@link GamePacket}s into
 * {@link Message}s.
 *
 * @author Graham
 */
public final class GameMessageDecoder extends MessageToMessageDecoder<GamePacket> {

    /**
     * The message translator.
     */
    private final MessageTranslator translator;

    /**
     * Constructs a new {@link GameMessageDecoder}.
     *
     * @param translator The messa translator.
     */
    public GameMessageDecoder(MessageTranslator translator) {
	this.translator = translator;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, GamePacket msg, List<Object> out) {
	Message message = translator.decode(msg);
	if (message != null) {
	    out.add(message);
	}
    }

}
