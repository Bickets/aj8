package org.apollo.fs;

import java.nio.ByteBuffer;

/**
 * Represents an entry within the manifest archive.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class ManifestEntry {

	/**
	 * The prefix of the manifest entry.
	 */
	private final String prefix;

	/**
	 * Constructs a new {@link ManifestEntry} with the specified entry prefix.
	 *
	 * @param prefix The prefix of the manifest entry.
	 */
	public ManifestEntry(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Unpacks the manifest entry from the specified archive.
	 *
	 * @param archive The archive.
	 */
	public void unpack(Archive archive) {
		ByteBuffer versionBuf = ByteBuffer.wrap(archive.get(String.format("%s_version", prefix)));

		int amountEntries = versionBuf.capacity() / 2;
		int[] versions = new int[amountEntries];
		int[] crcs = new int[amountEntries];

		for (int i = 0; i < amountEntries; i++) {
			versions[i] = versionBuf.getShort() & 0xffff;
		}

		ByteBuffer crcBuf = ByteBuffer.wrap(archive.get(String.format("%s_crc", prefix)));

		for (int i = 0; i < amountEntries; i++) {
			crcs[i] = crcBuf.getInt();
		}
	}

}