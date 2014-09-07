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
	private final PreparedStatement loadStatement;

	/**
	 * A prepared statement which inserts the players settings to the database.
	 */
	private final PreparedStatement saveStatement;

	/**
	 * Constructs a new {@link SettingsTable} with the specified database
	 * connection.
	 * 
	 * @param connection The database connection.
	 * @throws SQLException If some database access error occurs.
	 */
	protected SettingsTable(Connection connection) throws SQLException {
		loadStatement = connection.prepareStatement("SELECT * FROM settings WHERE player_id = ?;");
		saveStatement = connection.prepareStatement("INSERT INTO settings (player_id, setting, value) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value = VALUES(value);");
	}

	@Override
	public void load(Player player) throws SQLException, IOException {
		loadStatement.setInt(1, player.getDatabaseId());

		try (ResultSet set = loadStatement.executeQuery()) {
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
		saveStatement.setInt(1, player.getDatabaseId());

		saveStatement.setString(2, "designed_character");
		saveStatement.setBoolean(3, player.hasDesignedCharacter());
		saveStatement.addBatch();

		saveStatement.executeBatch();
	}

}