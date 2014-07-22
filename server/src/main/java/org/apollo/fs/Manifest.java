package org.apollo.fs;

import java.io.IOException;

public final class Manifest {

    private static final String[] ROOTS = { "model", "anim", "midi", "map" };

    private final MapIndex mapIndex = new MapIndex();
    private final ManifestEntry[] entries;

    public Manifest() {
	entries = new ManifestEntry[ROOTS.length];
	for (int i = 0; i < ROOTS.length; i++) {
	    entries[i] = new ManifestEntry(ROOTS[i]);
	}
    }

    public void unpack(Archive archive) throws IOException {
	for (ManifestEntry entry : entries) {
	    entry.unpack(archive);
	}

	mapIndex.unpack(archive);
    }

    public MapIndex getMapIndex() {
	return mapIndex;
    }

    public ManifestEntry getManifest(int index) {
	return entries[index - 1];
    }

}