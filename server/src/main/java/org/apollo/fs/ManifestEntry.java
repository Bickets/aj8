package org.apollo.fs;

import java.nio.ByteBuffer;

public final class ManifestEntry {

    private final String prefix;

    private int[] versions = new int[0];
    private int[] crcs = new int[0];

    public ManifestEntry(String prefix) {
	this.prefix = prefix;
    }

    public void unpack(Archive archive) {
	ByteBuffer versionBuf = ByteBuffer.wrap(archive.get(String.format("%s_version", prefix)));

	int amountEntries = versionBuf.capacity() / 2;
	versions = new int[amountEntries];
	crcs = new int[amountEntries];

	for (int i = 0; i < amountEntries; i++) {
	    versions[i] = versionBuf.getShort() & 0xffff;
	}

	ByteBuffer crcBuf = ByteBuffer.wrap(archive.get(String.format("%s_crc", prefix)));

	for (int i = 0; i < amountEntries; i++) {
	    crcs[i] = crcBuf.getInt();
	}
    }

}