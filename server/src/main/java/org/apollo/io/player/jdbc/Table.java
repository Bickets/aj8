package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.SQLException;

import org.apollo.game.model.Player;

/**
 * Represents a table within a JDBC database.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Table {

    /**
     * Loads a table for the specified {@link Player}.
     * 
     * @param player The player.
     * @throws SQLException If some database access error occurs.
     * @throws IOException If some I/O exception occurs.
     */
    public abstract void load(Player player) throws SQLException, IOException;

    /**
     * Saves a table for the specified {@link Player}.
     * 
     * @param player The player.
     * @throws SQLException If some database access error occurs.
     * @throws IOException If some I/O exception occurs.
     */
    public abstract void save(Player player) throws SQLException, IOException;

}