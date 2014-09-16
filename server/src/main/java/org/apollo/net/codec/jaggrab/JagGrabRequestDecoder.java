package org.apollo.net.codec.jaggrab;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apollo.net.codec.jaggrab.JagGrabRequest.JAGGRAB_ROOT;
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
		checkArgument(!msg.startsWith(JAGGRAB_ROOT), "Request : " + msg + " is not valid.");

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