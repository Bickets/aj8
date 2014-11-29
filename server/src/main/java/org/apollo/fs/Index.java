package org.apollo.fs;

import java.nio.ByteBuffer;

import org.apollo.util.ByteBufferUtil;

import com.google.common.base.Preconditions;

public final class Index {

	private final int length;

	private final int id;

	private Index(int length, int id) {
		this.length = length;
		this.id = id;
	}

	public static Index decode(ByteBuffer buffer) {
		int length = ByteBufferUtil.readMedium(buffer);
		int id = ByteBufferUtil.readMedium(buffer);
		return new Index(length, id);
	}

	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

	public void check() {
		Preconditions.checkArgument(length > 0);
	}

}