package org.apollo.fs;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Represent a {@link FileIndex} that has been cached.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class CachedFileIndex implements FileIndex {

    /**
     * A cache which automatically loads values and keeps them in memory until
     * until either evicted or manually invalidated.
     */
    private LoadingCache<Integer, byte[]> cache;

    /**
     * The file index to cache.
     */
    private final FileIndex index;

    /**
     * Constructs a new {@link CachedFileIndex} with the specified index and
     * maximum size.
     *
     * @param index The file index to cache.
     * @param maximumSize The maximum number of entries this cache can contain.
     */
    public CachedFileIndex(FileIndex index, int maximumSize) {
	this.index = index;
	configure(maximumSize);
    }

    /**
     * Configures this cache, if a cache already exists it is discarded.
     *
     * @param maximumSize The maximum amount of entries this cache can contain.
     */
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