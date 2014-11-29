package org.apollo.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.FileSystem;
import org.apollo.net.codec.update.OnDemandRequest;
import org.apollo.net.codec.update.OnDemandResponse;

/**
 * A worker which services 'on-demand' requests.
 *
 * @author Graham
 */
public final class OnDemandRequestWorker extends RequestWorker<OnDemandRequest, FileSystem> {

	/**
	 * The maximum length of a chunk, in bytes.
	 */
	private static final int CHUNK_LENGTH = 500;

	/**
	 * Creates the 'on-demand' request worker.
	 *
	 * @param dispatcher The dispatcher.
	 * @param fs The file system.
	 */
	public OnDemandRequestWorker(UpdateDispatcher dispatcher, FileSystem fs) {
		super(dispatcher, fs);
	}

	@Override
	protected ChannelRequest<OnDemandRequest> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException {
		return dispatcher.nextOnDemandRequest();
	}

	@Override
	protected void service(FileSystem fs, Channel channel, OnDemandRequest request) throws IOException {
		ByteBuffer buf = fs.getFile(request.getIndex(), request.getId());
		int length = buf.remaining();

		for (int chunk = 0; buf.remaining() > 0; chunk++) {
			int chunkSize = buf.remaining();
			if (chunkSize > CHUNK_LENGTH) {
				chunkSize = CHUNK_LENGTH;
			}

			byte[] tmp = new byte[chunkSize];
			buf.get(tmp, 0, tmp.length);
			ByteBuf chunkData = Unpooled.wrappedBuffer(tmp, 0, chunkSize);

			OnDemandResponse response = new OnDemandResponse(request.getIndex(), request.getId(), length, chunk, chunkData);
			channel.writeAndFlush(response);
		}
	}

}