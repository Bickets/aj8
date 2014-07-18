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

public final class PlayersTable extends Table {

    private final PreparedStatement loadStatement;
    private final PreparedStatement saveStatement;

    public PlayersTable(Connection connection) throws SQLException {
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
	saveStatement.setString(3, BCrypt.hashpw(player.getPassword(), BCrypt.gensalt()));
	saveStatement.setInt(4, player.getPrivilegeLevel().toInteger());
	saveStatement.setBoolean(5, player.isMembers());

	Position position = player.getPosition();
	saveStatement.setInt(6, position.getX());
	saveStatement.setInt(7, position.getY());
	saveStatement.setInt(8, position.getHeight());

	saveStatement.execute();
    }

}
