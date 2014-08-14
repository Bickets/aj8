package org.apollo.fs;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class CachedFileIndex implements FileIndex {

    private LoadingCache<Integer, byte[]> cache;

    private final FileIndex index;

    public CachedFileIndex(FileIndex index, int maximumSize) {
	this.index = index;
	configure(maximumSize);
    }

    public void configure(int maximumSize) {
	if (cache != null) {
	    cache.invalidateAll();
	}

	cache = CacheBuilder.newBuilder().maximumSize(maximumSize).build(new CacheLoader<Integer, byte[]>() {
	    @Override
	    public byte[] load(Integer file) throws Exception {
		return index.get(file);
	    }
	});
    }

    @Override
    public byte[] get(int file) throws IOException {
	try {
	    return cache.get(file);
	} catch (ExecutionException ex) {
	    return null;
	}
    }

}