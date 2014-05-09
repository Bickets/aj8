package org.apollo.fs;

public final class MapEntry {

    private final int mapFile;

    private final int scapeFile;

    public MapEntry(int hash, int mapFile, int scapeFile, boolean preload) {
	this.mapFile = mapFile;
	this.scapeFile = scapeFile;
    }

    public int getMapFile() {
	return mapFile;
    }

    public int getScapeFile() {
	return scapeFile;
    }

}