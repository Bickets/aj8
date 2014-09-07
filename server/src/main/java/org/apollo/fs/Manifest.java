package org.apollo.fs;

import java.io.IOException;

/**
 * Represents the manifest archive within this file system.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class Manifest {

	/**
	 * Represents the root manifest archive entry names.
	 */
	private static final String[] ROOTS = { "model", "anim", "midi", "map" };

	/**
	 * All of the archive entries within the manifest archive.
	 */
	private final ManifestEntry[] entries;

	/**
	 * Constructs a new {@link Manifest}.
	 */
	public Manifest() {
		entries = new ManifestEntry[ROOTS.length];
		for (int i = 0; i < ROOTS.length; i++) {
			entries[i] = new ManifestEntry(ROOTS[i]);
		}
	}

	/**
	 * Unpacks all of the manifest archive entries.
	 *
	 * @param archive The archive.
	 * @throws IOException If some I/O exception occurs.
	 */
	public void unpack(Archive archive) throws IOException {
		for (ManifestEntry entry : entries) {
			entry.unpack(archive);
		}
	}

	/**
	 * Returns a manifest entry from the specified index.
	 *
	 * @param index The index of the manifest entry.
	 * @return The manifest entry for the specified index.
	 * @throws IndexOutOfBoundsException If the specified index does not
	 *             represent any manifest entry.
	 */
	public ManifestEntry getManifest(int index) {
		if (index < 0 || index > entries.length) {
			throw new IndexOutOfBoundsException(index + " does not represent any manifest entry.");
		}
		return entries[index - 1];
	}

}