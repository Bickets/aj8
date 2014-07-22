package org.apollo.fs;

public final class MapEntry {

    private final int areaHashCode;

    private final int mapFile;

    private final int landscapeFile;

    private final boolean preload;

    public MapEntry(int areaHashCode, int mapFile, int scapeFile, boolean preload) {
	this.areaHashCode = areaHashCode;
	this.mapFile = mapFile;
	this.landscapeFile = scapeFile;
	this.preload = preload;
    }

    public int getAreaHashCode() {
	return areaHashCode;
    }

    public int getMapFile() {
	return mapFile;
    }

    public int getScapeFile() {
	return landscapeFile;
    }

    public boolean isPreload() {
	return preload;
    }

}