package org.apollo.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

import org.apollo.util.ByteBufferUtil;

public final class Index implements FileIndex {

    public static final int FILE_BLOCK_SIZE = 6;
    public static final int DATA_BLOCK_HEADER_SIZE = 8;
    public static final int DATA_BLOCK_SIZE = 520;

    private final ByteBuffer buffer = ByteBuffer.allocate(DATA_BLOCK_SIZE);
    private final SeekableByteChannel dataChannel;
    private final SeekableByteChannel indexChannel;
    private final int id;

    public Index(SeekableByteChannel data, SeekableByteChannel index, int id) {
	this.dataChannel = data;
	this.indexChannel = index;
	this.id = ++id;
    }

    public byte[] get(int file) throws IOException {
	buffer.clear();

	buffer.limit(FILE_BLOCK_SIZE);
	indexChannel.position(file * (long) FILE_BLOCK_SIZE);
	indexChannel.read(buffer);

	buffer.flip();

	int length = ByteBufferUtil.readMedium(buffer);
	int block = ByteBufferUtil.readMedium(buffer);

	if (length < 1) {
	    return null;
	}

	byte[] bytes = new byte[length];
	int offset = 0;
	int chunk = 0;
	while (offset < length) {

	    int dataLength = length - offset;
	    if (dataLength > 512) {
		dataLength = 512;
	    }

	    buffer.clear();

	    buffer.limit(dataLength + DATA_BLOCK_HEADER_SIZE);
	    dataChannel.position(block * (long) DATA_BLOCK_SIZE);
	    dataChannel.read(buffer);

	    buffer.flip();

	    int fileCheck = buffer.getShort() & 0xffff;
	    int chunkCheck = buffer.getShort() & 0xffff;
	    int nextBlock = ByteBufferUtil.readMedium(buffer);
	    int idCheck = buffer.get() & 0xff;

	    if (fileCheck != file || chunkCheck != chunk || idCheck != id) {
		throw new IOException();
	    }

	    buffer.get(bytes, offset, dataLength);
	    offset += dataLength;

	    block = nextBlock;
	    chunk++;
	}

	return bytes;
    }

}