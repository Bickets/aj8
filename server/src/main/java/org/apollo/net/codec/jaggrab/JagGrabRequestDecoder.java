package org.apollo.net.codec.jaggrab;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.StringTokenizer;

/**
 * A {@link MessageToMessageDecoder} for the JAGGRAB protocol.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class JagGrabRequestDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) {
		if (!msg.startsWith(JagGrabRequest.JAGGRAB_ROOT)) {
			throw new RuntimeException("invalid jaggrab request");
		}

		StringTokenizer tokenizer = new StringTokenizer(msg);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.startsWith("/")) {
				out.add(new JagGrabRequest(token));
				break;
			}
		}
	}

}