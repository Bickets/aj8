package org.apollo.io.player;

import java.io.IOException;
import java.sql.SQLException;

import org.apollo.security.PlayerCredentials;

/**
 * Provides sanction information for a specified player.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@FunctionalInterface
public interface PlayerSanctionProvider {

    /**
     * Returns a status response based on the player credentials.
     *
     * @param credentials The players credentials.
     * @return A response based on the players credentials.
     * @throws IOException If some I/O exception occurs.
     * @throws SQLException If some database access error occurs.
     */
    PlayerSanctionResponse check(PlayerCredentials credentials) throws IOException, SQLException;

}