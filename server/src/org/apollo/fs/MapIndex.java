package org.apollo.fs;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public final class MapIndex {

    private final Map<Integer, MapEntry> entries = new HashMap<>();

    public MapIndex() {

    }

    public void unpack(Archive archive) {
	ByteBuffer buffer = ByteBuffer.wrap(archive.get("map_index"));

	int amountEntries = buffer.capacity() / 7;
	for (int i = 0; i < amountEntries; i++) {
	    int hash = buffer.getShort();
	    int mapFile = buffer.getShort() & 0xffff;
	    int scapeFile = buffer.getShort() & 0xffff;
	    boolean preload = buffer.get() == 1;

	    entries.put(hash, new MapEntry(hash, mapFile, scapeFile, preload));
	}
    }

    public MapEntry entryFor(int regionX, int regionY) {
	return entries.get(hash(regionX, regionY));
    }

    private static int hash(int regionX, int regionY) {
	return regionX << 8 | regionY;
    }

}