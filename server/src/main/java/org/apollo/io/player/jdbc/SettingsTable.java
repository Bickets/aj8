package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.game.model.Player;

/**
 * A {@link Table} which serializes common settings of a player.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class SettingsTable extends Table {

    /**
     * A prepared statement which selects the players settings from the
     * database.
     */
    private final PreparedStatement settingsLoadStatement;

    /**
     * A prepared statement which inserts the players settings to the database.
     */
    private final PreparedStatement settingsSaveStatement;

    /**
     * Constructs a new {@link SettingsTable} with the specified database
     * connection.
     * 
     * @param connection The database connection.
     * @throws SQLException If some database access error occurs.
     */
    protected SettingsTable(Connection connection) throws SQLException {
	settingsLoadStatement = connection.prepareStatement("SELECT * FROM settings WHERE player_id = ?;");
	settingsSaveStatement = connection.prepareStatement("INSERT INTO settings (player_id, setting, value) VALUES (?, ?) ON DUPLICATE KEY UPDATE valu = VALUES(value);");
    }

    @Override
    public void load(Player player) throws SQLException, IOException {
	settingsLoadStatement.setInt(1, player.getDatabaseId());

	try (ResultSet set = settingsLoadStatement.executeQuery()) {
	    while (set.next()) {
		String setting = set.getString("setting");
		boolean value = set.getBoolean("value");

		switch (setting) {
		case "designed_character":
		    player.setDesignedCharacter(value);
		    break;
		}
	    }
	}
    }

    @Override
    public void save(Player player) throws SQLException, IOException {
	settingsSaveStatement.setInt(1, player.getDatabaseId());

	settingsSaveStatement.setString(2, "designed_character");
	settingsSaveStatement.setBoolean(3, player.hasDesignedCharacter());
	settingsSaveStatement.addBatch();

	settingsSaveStatement.executeBatch();
    }

}