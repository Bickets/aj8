package org.apollo.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;
import org.apollo.util.NameUtil;

public final class Archive {

    private final Map<Integer, ArchiveEntry> entries = new HashMap<>();

    private final byte[] bytes;

    private boolean packed;

    public Archive(byte[] bytes) {
	this.bytes = bytes;
    }

    public Archive decode() throws IOException {
	ByteBuffer buffer = ByteBuffer.wrap(bytes);
	int unpackedSize = ByteBufferUtil.readMedium(buffer);
	int packedSize = ByteBufferUtil.readMedium(buffer);

	byte[] unpackedBytes = bytes;

	packed = packedSize != unpackedSize;

	if (packed) {
	    unpackedBytes = CompressionUtil.unbzip2(bytes, 6, packedSize);
	    buffer = ByteBuffer.wrap(unpackedBytes);
	}

	int amountEntries = buffer.getShort() & 0xff;
	int offset = buffer.position() + amountEntries * 10;

	for (int i = 0; i < amountEntries; i++) {
	    int name = buffer.getInt();
	    int unpacked = ByteBufferUtil.readMedium(buffer);
	    int packed = ByteBufferUtil.readMedium(buffer);

	    byte[] entryBytes = null;

	    if (unpacked != packed) {
		entryBytes = CompressionUtil.unbzip2(unpackedBytes, offset, packed);
	    } else {
		entryBytes = new byte[unpacked];
		System.arraycopy(unpackedBytes, offset, entryBytes, 0, unpacked);
	    }

	    offset += packed;

	    entries.put(name, new ArchiveEntry(entryBytes, name));
	}

	return this;
    }

    public byte[] get(String name) {
	ArchiveEntry entry = Objects.requireNonNull(entries.get(NameUtil.hash(name)));
	return entry.getBytes();
    }

    public boolean isPacked() {
	return packed;
    }

}