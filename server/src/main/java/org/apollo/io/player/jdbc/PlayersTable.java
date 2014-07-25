package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.game.crypto.BCrypt;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;

/**
 * A {@link Table} which serializes player information.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayersTable extends Table {

    /**
     * A prepared statement which selects player information from the database.
     */
    private final PreparedStatement loadStatement;

    /**
     * A prepared statement which inserts player information to the database.
     */
    private final PreparedStatement saveStatement;

    /**
     * Constructs a new {@link PlayersTable} with the specified database
     * connection.
     * 
     * @param connection The database connection.
     * @throws SQLException If some database access error occurs.
     */
    protected PlayersTable(Connection connection) throws SQLException {
	loadStatement = connection.prepareStatement("SELECT * FROM players WHERE id = ?");
	saveStatement = connection.prepareStatement("REPLACE INTO players (id, username, password, rights, members, x, y, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
    }

    @Override
    public void load(Player player) throws SQLException, IOException {
	loadStatement.setInt(1, player.getDatabaseId());

	try (ResultSet set = loadStatement.executeQuery()) {
	    if (!set.first()) {
		throw new IOException();
	    }

	    boolean members = set.getBoolean("members");
	    player.setMembers(members);

	    int privilegeLevelInteger = set.getInt("rights");
	    PrivilegeLevel privilegeLevel = PrivilegeLevel.valueOf(privilegeLevelInteger);
	    player.setPrivilegeLevel(privilegeLevel);

	    int x = set.getInt("x");
	    int y = set.getInt("y");
	    int height = set.getInt("height");
	    player.setPosition(new Position(x, y, height));
	}
    }

    @Override
    public void save(Player player) throws SQLException {
	saveStatement.setInt(1, player.getDatabaseId());
	saveStatement.setString(2, player.getName());
	saveStatement.setString(3, BCrypt.hashpw(player.getPassword(), BCrypt.gensalt(12)));
	saveStatement.setInt(4, player.getPrivilegeLevel().toInteger());
	saveStatement.setBoolean(5, player.isMembers());

	Position position = player.getPosition();
	saveStatement.setInt(6, position.getX());
	saveStatement.setInt(7, position.getY());
	saveStatement.setInt(8, position.getHeight());

	saveStatement.execute();
    }

}