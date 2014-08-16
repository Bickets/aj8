package org.apollo.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

import org.apollo.util.ByteBufferUtil;

/**
 * Represents a standard file index.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class Index implements FileIndex {

    /**
     * The default file block size.
     */
    public static final int FILE_BLOCK_SIZE = 6;

    /**
     * The default block header size.
     */
    public static final int DATA_BLOCK_HEADER_SIZE = 8;

    /**
     * The default block size.
     */
    public static final int DATA_BLOCK_SIZE = 520;

    /**
     * A byte buffer allocated byte count is {@link #DATA_BLOCK_SIZE}.
     */
    private final ByteBuffer buffer = ByteBuffer.allocate(DATA_BLOCK_SIZE);

    /**
     * A channel of bytes within this indexes data file.
     */
    private final SeekableByteChannel dataChannel;

    /**
     * A channel of bytes within this indexes index file.
     */
    private final SeekableByteChannel indexChannel;

    /**
     * The id of this file index.
     */
    private final int id;

    /**
     * Constructs a new {@link Index} with the specified data channel, index
     * channel and id.
     *
     * @param dataChannel A channel of bytes within this indexes data file.
     * @param indexChannel A channel of bytes within this indexes index file.
     * @param id The id of this file index.
     */
    public Index(SeekableByteChannel dataChannel, SeekableByteChannel indexChannel, int id) {
	this.dataChannel = dataChannel;
	this.indexChannel = indexChannel;
	this.id = ++id;
    }

    @Override
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

	    int fileCheck = buffer.getShort() & 0xFFFF;
	    int chunkCheck = buffer.getShort() & 0xFFFF;
	    int nextBlock = ByteBufferUtil.readMedium(buffer);
	    int idCheck = buffer.get() & 0xFF;

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