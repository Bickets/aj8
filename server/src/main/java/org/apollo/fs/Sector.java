package org.apollo.fs;

import java.nio.ByteBuffer;

import org.apollo.util.ByteBufferUtil;

import com.google.common.base.Preconditions;

public final class Sector {

	private final int indexId;

	private final int chunk;

	private final int nextIndexId;

	private final int cacheId;

	private Sector(int indexId, int chunk, int nextIndexId, int cacheId) {
		this.indexId = indexId;
		this.chunk = chunk;
		this.nextIndexId = nextIndexId;
		this.cacheId = cacheId;
	}

	public static Sector decode(ByteBuffer buffer, byte[] data, int offset, int length) {
		int indexId = buffer.getShort() & 0xFFFF;
		int chunk = buffer.getShort() & 0xFFFF;
		int nextIndexId = ByteBufferUtil.readMedium(buffer);
		int cacheId = buffer.get() & 0xFF;
		buffer.get(data, offset, length);
		return new Sector(indexId, chunk, nextIndexId, cacheId);
	}

	public void check(int cacheId, int indexId, int chunk) {
		Preconditions.checkArgument(this.cacheId == cacheId);
		Preconditions.checkArgument(this.indexId == indexId);
		Preconditions.checkArgument(this.chunk == chunk);
	}

	public int getIndexId() {
		return indexId;
	}

	public int getChunk() {
		return chunk;
	}

	public int getNextIndexId() {
		return nextIndexId;
	}

	public int getCacheId() {
		return cacheId;
	}

}