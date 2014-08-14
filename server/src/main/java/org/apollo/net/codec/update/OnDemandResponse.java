package org.apollo.net.codec.update;

import io.netty.buffer.ByteBuf;

/**
 * Represents a single 'on-demand' response.
 *
 * @author Graham
 */
public final class OnDemandResponse {

    /**
     * The requested files index.
     */
    private final int index;

    /**
     * The requested files id.
     */
    private final int id;

    /**
     * The file size.
     */
    private final int fileSize;

    /**
     * The chunk id.
     */
    private final int chunkId;

    /**
     * The chunk data.
     */
    private final ByteBuf chunkData;

    /**
     * Creates the on-demand response.
     *
     * @param index The index of this file.
     * @param id The id of this file.
     * @param fileSize The size, in bytes of this file.
     * @param chunkId This files chunk id.
     * @param chunkData This files chunk data.
     */
    public OnDemandResponse(int index, int id, int fileSize, int chunkId, ByteBuf chunkData) {
	this.index = index;
	this.id = id;
	this.fileSize = fileSize;
	this.chunkId = chunkId;
	this.chunkData = chunkData;
    }

    /**
     * Returns the requested files index.
     */
    public int getIndex() {
	return index;
    }

    /**
     * Returns the requested files id.
     */
    public int getId() {
	return id;
    }

    /**
     * Gets the file size.
     *
     * @return The file size.
     */
    public int getFileSize() {
	return fileSize;
    }

    /**
     * Gets the chunk id.
     *
     * @return The chunk id.
     */
    public int getChunkId() {
	return chunkId;
    }

    /**
     * Gets the chunk data.
     *
     * @return The chunk data.
     */
    public ByteBuf getChunkData() {
	return chunkData;
    }

    @Override
    public String toString() {
	return "OnDemandResponse [index=" + index + ", id=" + id + ", fileSize=" + fileSize + ", chunkId=" + chunkId + ", chunkData=" + chunkData + "]";
    }

}