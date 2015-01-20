package org.apollo.game.model.region;

import static org.apollo.game.model.region.Region.SIZE;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Position;

/**
 * A repository of regions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class RegionRepository {

	/**
	 * A map of region ids to regions.
	 */
	private final Map<Integer, Region> regions = new HashMap<>();

	/**
	 * Gets the region for the specified {@link Position}.
	 *
	 * @param position The position of the region.
	 * @return The region for the specified position.
	 */
	public Region getRegion(Position position) {
		int regionX = position.getX() >> 6;
		int regionY = position.getY() >> 6;
		int id = regionX + regionY * SIZE;

		Region region = regions.get(id);
		if (region == null) {
			regions.put(id, new Region());
			return regions.get(id);
		}

		return region;
	}

}