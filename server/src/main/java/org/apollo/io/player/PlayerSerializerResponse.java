package org.apollo.io.player;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apollo.net.codec.login.LoginConstants.STATUS_OK;
import static org.apollo.net.codec.login.LoginConstants.STATUS_RECONNECTION_OK;

import org.apollo.game.model.Player;

/**
 * A response for the
 * {@link PlayerSerializer#loadPlayer(org.apollo.security.PlayerCredentials)}
 * call.
 *
 * @author Graham
 */
public final class PlayerSerializerResponse {

	/**
	 * The status code.
	 */
	private final int status;

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates a {@link PlayerSerializerResponse} with only a status code.
	 *
	 * @param status The status code.
	 */
	public PlayerSerializerResponse(int status) {
		this(status, null);
	}

	/**
	 * Creates a {@link PlayerSerializerResponse} with a status code and player.
	 *
	 * @param status The status code.
	 * @param player The player.
	 * @throws IllegalArgumentException if the status code does not need
	 *             {@link Player}.
	 */
	public PlayerSerializerResponse(int status, Player player) {
		checkArgument((status == STATUS_OK || status == STATUS_RECONNECTION_OK) && player == null, "Status : " + status + " cannot be sent without an instance of Player");
		this.status = status;
		this.player = player;
	}

	/**
	 * Gets the status code.
	 *
	 * @return The status code.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Gets the player.
	 *
	 * @return The player, or {@code null} if there is no player in this
	 *         response.
	 */
	public Player getPlayer() {
		return player;
	}

}