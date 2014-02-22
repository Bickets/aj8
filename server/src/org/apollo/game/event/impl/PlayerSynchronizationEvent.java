
package org.apollo.game.event.impl;

import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;
import org.apollo.game.sync.seg.SynchronizationSegment;

/**
 * An event which is sent to synchronize the players.
 * @author Graham
 */
public final class PlayerSynchronizationEvent extends Event
{

	/**
	 * The last known region.
	 */
	private final Position lastKnownRegion;

	/**
	 * The players position.
	 */
	private final Position position;

	/**
	 * A flag indicating if the region has changed.
	 */
	private final boolean regionChanged;

	/**
	 * The current players synchronization segment.
	 */
	private final SynchronizationSegment segment;

	/**
	 * The number of local players.
	 */
	private final int localPlayers;

	/**
	 * A list of segments.
	 */
	private final List<SynchronizationSegment> segments;


	/**
	 * Creates the player synchronization event.
	 * @param lastKnownRegion The last known region.
	 * @param position The players current position.
	 * @param regionChanged A flag indicating if the region has changed.
	 * @param segment The current players synchronization segment.
	 * @param localPlayers The number of local players.
	 * @param segments A list of segments.
	 */
	public PlayerSynchronizationEvent( Position lastKnownRegion, Position position, boolean regionChanged, SynchronizationSegment segment, int localPlayers, List<SynchronizationSegment> segments )
	{
		this.lastKnownRegion = lastKnownRegion;
		this.position = position;
		this.regionChanged = regionChanged;
		this.segment = segment;
		this.localPlayers = localPlayers;
		this.segments = segments;
	}


	/**
	 * Gets the last known region.
	 * @return The last known region.
	 */
	public Position getLastKnownRegion()
	{
		return lastKnownRegion;
	}


	/**
	 * Gets the players position.
	 * @return The players position.
	 */
	public Position getPosition()
	{
		return position;
	}


	/**
	 * Checks if the region has changed.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasRegionChanged()
	{
		return regionChanged;
	}


	/**
	 * Gets the current players segment.
	 * @return The current players segment.
	 */
	public SynchronizationSegment getSegment()
	{
		return segment;
	}


	/**
	 * Gets the number of local players.
	 * @return The number of local players.
	 */
	public int getLocalPlayers()
	{
		return localPlayers;
	}


	/**
	 * Gets the synchronization segments.
	 * @return The segments.
	 */
	public List<SynchronizationSegment> getSegments()
	{
		return segments;
	}

}
