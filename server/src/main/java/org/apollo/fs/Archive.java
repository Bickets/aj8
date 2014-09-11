package org.apollo.fs;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;
import org.apollo.util.NameUtil;

/**
 * Represents an archive within the file system.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class Archive {

	/**
	 * A map of integer keys to entries within this archive.
	 */
	private final Map<Integer, ArchiveEntry> entries = new HashMap<>();

	/**
	 * The bytes within this archive.
	 */
	private final byte[] bytes;

	/**
	 * Denotes whether or not this archive is compressed.
	 */
	private boolean packed;

	/**
	 * Constructs a new {@link Archive} with the expected data.
	 *
	 * @param bytes The archives data.
	 */
	public Archive(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * Decodes this archives contents into {@link ArchiveEntry}s
	 *
	 * @return An instance of this archive.
	 * @throws IOException If some I/O exception occurs.
	 */
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

			byte[] entryBytes;

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

	/**
	 * Returns an the data of an archive entry based on its name.
	 *
	 * @param name The name of the archive entry.
	 * @return The archive entries name.
	 */
	public byte[] get(String name) {
		ArchiveEntry entry = requireNonNull(entries.get(NameUtil.hash(name)));
		return entry.getBytes();
	}

	/**
	 * Returns whether or not this archive is compressed.
	 */
	public boolean isPacked() {
		return packed;
	}

}