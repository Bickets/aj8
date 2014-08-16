package org.apollo.fs;

/**
 * Represents an entry within some {@link Archive}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class ArchiveEntry {

    /**
     * The bytes within this entry.
     */
    private final byte[] bytes;

    /**
     * The hashed name of this entry.
     */
    private final int name;

    /**
     * Constructs a new {@link ArchiveEntry} with the entries data and hashed
     * name.
     *
     * @param bytes The bytes within this entry.
     * @param name The hashed name of this entry.
     */
    public ArchiveEntry(byte[] bytes, int name) {
	this.bytes = bytes;
	this.name = name;
    }

    /**
     * Returns the data within this entry.
     */
    public byte[] getBytes() {
	return bytes;
    }

    /**
     * Returns the hashed name of this entry.
     */
    public int getName() {
	return name;
    }

}