package org.apollo.net.codec.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.apollo.game.msg.Message;
import org.apollo.game.msg.MessageTranslator;

/**
 * A {@link MessageToMessageEncoder} which encodes {@link Message}s into
 * {@link GamePacket}s.
 *
 * @author Graham
 */
public final class GameMessageEncoder extends MessageToMessageEncoder<Message> {

    /**
     * The message translator.
     */
    private final MessageTranslator translator;

    /**
     * Constructs a new {@link GameMessageEncoder}.
     *
     * @param translator The message translator.
     */
    public GameMessageEncoder(MessageTranslator translator) {
	this.translator = translator;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
	GamePacket packet = translator.encode(msg);
	if (packet != null) {
	    out.add(packet);
	}
    }

}