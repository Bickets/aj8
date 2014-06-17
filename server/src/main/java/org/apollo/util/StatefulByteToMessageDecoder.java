package org.apollo.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * A stateful implementation of a {@link ByteToMessageDecoder} which may be
 * extended and used by other classes. The current state is tracked by this
 * class and is a user-specified enumeration. The state may be changed by
 * calling the {@link StatefulByteToMessageDecoder#setState(Enum)} method. The
 * current state is supplied as a parameter in the
 * {@link StatefulByteToMessageDecoder#decode(ChannelHandlerContext, Channel, ByteBuf, Enum)}
 * and
 * {@link StatefulByteToMessageDecoder#decodeLast(ChannelHandlerContext, Channel, ByteBuf, Enum)}
 * methods. This class is not thread safe: it is recommended that the state is
 * only set in the decode methods overridden. {@code null} states are not
 * permitted. If you do not need state management, the
 * {@link ByteToMessageDecoder} class should be used instead.
 *
 * @author Graham
 * @param <T> The state enumeration.
 */
public abstract class StatefulByteToMessageDecoder<T extends Enum<T>> extends ByteToMessageDecoder {

    /**
     * The current state.
     */
    private T state;

    /**
     * Creates the stateful frame decoder with the specified initial state.
     *
     * @param state The initial state.
     */
    public StatefulByteToMessageDecoder(T state) {
	setState(state);
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
	decode(ctx, in, out, state);
    }

    /**
     * Decodes the received packets into a frame.
     *
     * @param ctx The current context of this handler.
     * @param in The cumulative buffer, which may contain zero or more bytes.
     * @param out The {@link List} to which decoded messages should be added
     * @param state The current state. The state may be changed by calling
     *            {@link #setState(Enum)}.
     */
    protected abstract void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, T state);

    @Override
    protected final void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
	decodeLast(ctx, in, out, state);
    }

    /**
     * Decodes remaining data before the channel is closed into a frame. You may
     * override this method, but it is not required. If you do not, remaining
     * data will be discarded!
     *
     * @param ctx The current context of this handler.
     * @param in The cumulative buffer, which may contain zero or more bytes.
     * @param out The {@link List} to which decoded messages should be added
     * @param state The current state. The state may be changed by calling
     *            {@link #setState(Enum)}.
     */
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, T state) {

    }

    /**
     * Sets a new state.
     *
     * @param state The new state.
     * @throws NullPointerException If the state is {@code null}.
     */
    public final void setState(T state) {
	if (state == null) {
	    throw new NullPointerException("state cannot be null");
	}
	this.state = state;
    }

}
