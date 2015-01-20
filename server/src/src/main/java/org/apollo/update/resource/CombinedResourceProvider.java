package org.apollo.update.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * A resource provider composed of multiple resource providers.
 *
 * @author Graham
 */
public final class CombinedResourceProvider implements ResourceProvider {

	/**
	 * A {@link Set} of resource providers.
	 */
	private final Set<ResourceProvider> providers;

	/**
	 * Creates the combined resource providers.
	 *
	 * @param providers The providers this provider delegates to.
	 */
	public CombinedResourceProvider(ResourceProvider... providers) {
		this.providers = ImmutableSet.copyOf(providers);
	}

	@Override
	public boolean accept(String path) throws IOException {
		return true;
	}

	@Override
	public ByteBuffer get(String path) throws IOException {
		for (ResourceProvider provider : providers) {
			if (provider.accept(path)) {
				return provider.get(path);
			}
		}
		return null;
	}

}