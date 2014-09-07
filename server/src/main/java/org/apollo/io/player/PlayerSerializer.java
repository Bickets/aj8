package org.apollo.io.player;

import java.io.IOException;
import java.sql.SQLException;

import org.apollo.game.model.Player;
import org.apollo.security.PlayerCredentials;

/**
 * The player serialize is responsible for managing load and save events for a
 * specified player or credentials.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham
 */
public interface PlayerSerializer {

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
	PlayerSerializerResponse loadPlayer(PlayerCredentials credentials) throws SQLException, IOException;

	/**
	 * This event is fired when a player is logged out of the game naturally.
	 *
	 * @param player The player who has logged out.
	 * @throws SQLException If some database access error occurs.
	 * @throws IOException If some I/O exception occurs.
	 */
	void savePlayer(Player player) throws SQLException, IOException;

}