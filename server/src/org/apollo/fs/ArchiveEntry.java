package org.apollo.fs;

public final class ArchiveEntry {

    private final byte[] bytes;

    private final int name;

    public ArchiveEntry(byte[] bytes, int name) {
	this.bytes = bytes;
	this.name = name;
    }

    public byte[] getBytes() {
	return bytes;
    }

    public int getName() {
	return name;
    }

}