package org.apollo.io.player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.security.PlayerCredentials;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * The player serialize is responsible for managing load and save events for a
 * specified player or credentials.
 * 
 * <p>
 * Every {@link PlayerSerializer player serializer} implementation offers a
 * {@link Cache cache} of {@link PlayerCredentials#getEncodedUsername() encoded
 * player names} to {@link Player players}, not every implementation utilizes or
 * is required to utilize this feature.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham
 * 
 * @see {@link PlayerSerializer#playerCache}
 */
public abstract class PlayerSerializer {

	/**
	 * The world this player is in.
	 */
	protected final World world;

	/**
	 * A {@link Cache} of previously logged in {@link Player player's} which
	 * expire after <tt>10</tt> {@link TimeUnit#MINUTES minutes}. If the player
	 * logs out and attempts to log back in before their cache is expired; we
	 * don't have to waste essential processing time doing I/O repeatedly for
	 * redundant login requests.
	 */
	private final Cache<Long, Player> playerCache = CacheBuilder.newBuilder().initialCapacity(100).expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * Constructs a new {@link PlayerSerializer}.
	 *
	 * @param world The world this player is in.
	 */
	public PlayerSerializer(World world) {
		this.world = world;
	}

	/**
	 * This event is fired when a player attempts to log-in to the game,
	 * attempting to load a local or remote save for the specified credentials
	 * passed.
	 *
	 * @param credentials The credentials passed, typically used to identify a
	 *            player based on unique username.
	 * @return The response of this load request.
	 * @throws SQLException If some database access error occurs.
	 * @throws IOException If some I/O exception occurs.
	 */
	protected abstract PlayerSerializerResponse loadPlayer(PlayerCredentials credentials) throws SQLException, IOException;

	/**
	 * This event is fired when a player is logged out of the game naturally.
	 *
	 * @param player The player who has logged out.
	 * @throws SQLException If some database access error occurs.
	 * @throws IOException If some I/O exception occurs.
	 */
	protected abstract void savePlayer(Player player) throws SQLException, IOException;

	/**
	 * Returns the world this player is in.
	 */
	public final World getWorld() {
		return world;
	}

	/**
	 * Associates a {@link Player player} with their
	 * {@link PlayerCredentials#getEncodedUsername() encoded name} in this
	 * cache. If the cache previously contained a value associated with the key,
	 * the old value is replaced by the new value.
	 * 
	 * @param player The player to add to the cache.
	 */
	public final void appendToCache(Player player) {
		playerCache.put(player.getEncodedName(), player);
	}

	/**
	 * Discards the value for the specified encoded name from the cache.
	 * 
	 * @param encodedName he encoded name of the player to remove from the
	 *            cache.
	 */
	public final void removeFromCache(long encodedName) {
		playerCache.invalidate(encodedName);
	}

	/**
	 * Attempts to return a {@link Player player} from the cache for the
	 * specified encoded name.
	 * 
	 * @param encodedName The encoded name of the player to retrieve from the
	 *            cache.
	 * @return The player within the cache or {@code null} if the player does
	 *         not exist for the specified encoded name.
	 */
	public final Player getFromCache(long encodedName) {
		return playerCache.getIfPresent(encodedName);
	}

}