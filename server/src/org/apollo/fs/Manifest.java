package org.apollo.fs;

public final class Manifest {

    private final MapIndex mapIndex = new MapIndex();
    private final ManifestEntry[] entries;

    public Manifest() {
	String[] prefixes = new String[] { "model", "anim", "midi", "map" };
	entries = new ManifestEntry[prefixes.length];
	for (int i = 0; i < prefixes.length; i++) {
	    entries[i] = new ManifestEntry(prefixes[i]);
	}
    }

    public void unpack(Archive archive) {
	for (ManifestEntry entry : entries) {
	    entry.unpack(archive);
	}

	mapIndex.unpack(archive);
    }

}