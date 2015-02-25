package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.game.model.Player;
import org.apollo.game.model.appearance.Appearance;
import org.apollo.game.model.appearance.Gender;

/**
 * A {@link Table} which serializes player appearance.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class AppearanceTable implements Table {

	/**
	 * A prepared statement which selects the players gender from the database.
	 */
	private final PreparedStatement genderLoadStatement;

	/**
	 * A prepared statement which selects the players appearance styles from the
	 * database.
	 */
	private final PreparedStatement styleLoadStatement;

	/**
	 * A prepared statement which selects the players appearance colors from the
	 * database.
	 */
	private final PreparedStatement colorLoadStatement;

	/**
	 * A prepared statement which inserts the players gender into the database.
	 */
	private final PreparedStatement genderSaveStatement;

	/**
	 * A prepared statement which inserts the players appearance styles into the
	 * database.
	 */
	private final PreparedStatement styleSaveStatement;

	/**
	 * A prepared statement which inserts the players appearance colors into the
	 * database.
	 */
	private final PreparedStatement colorSaveStatement;

	/**
	 * Constructs a new {@link AppearanceTable} with the specified database
	 * connection.
	 *
	 * @param connection The database connection.
	 * @throws SQLException If some database access error occurs.
	 */
	protected AppearanceTable(Connection connection) throws SQLException {
		genderLoadStatement = connection.prepareStatement("SELECT * FROM gender WHERE player_id = ?;");
		styleLoadStatement = connection.prepareStatement("SELECT * FROM style WHERE player_id = ?;");
		colorLoadStatement = connection.prepareStatement("SELECT * FROM color WHERE player_id = ?;");

		genderSaveStatement = connection.prepareStatement("INSERT INTO gender (player_id, gender) VALUES (?, ?) ON DUPLICATE KEY UPDATE gender = VALUES(gender);");
		styleSaveStatement = connection.prepareStatement("INSERT INTO style (player_id, style) VALUES (?, ?) ON DUPLICATE KEY UPDATE style = VALUES(style);");
		colorSaveStatement = connection.prepareStatement("INSERT INTO color (player_id, color) VALUES (?, ?) ON DUPLICATE KEY UPDATE color = VALUES(color);");
	}

	@Override
	public void load(Player player) throws SQLException, IOException {
		genderLoadStatement.setInt(1, player.getDatabaseId());
		styleLoadStatement.setInt(1, player.getDatabaseId());
		colorLoadStatement.setInt(1, player.getDatabaseId());

		Gender gender = Gender.MALE;

		try (ResultSet set = genderLoadStatement.executeQuery()) {
			while (set.next()) {
				int genderInt = set.getInt("gender");

				gender = Gender.valueOf(genderInt);
			}
		}

		int[] styles = new int[7];

		int index = 0;
		try (ResultSet set = styleLoadStatement.executeQuery()) {
			while (set.next()) {
				int style = set.getInt("style");

				styles[index++] = style;
			}
		}

		int[] colors = new int[5];

		index = 0;
		try (ResultSet set = colorLoadStatement.executeQuery()) {
			while (set.next()) {
				int color = set.getInt("color");

				colors[index++] = color;
			}
		}

		player.setAppearance(new Appearance(gender, styles, colors));
	}

	@Override
	public void save(Player player) throws SQLException, IOException {
		genderSaveStatement.setInt(1, player.getDatabaseId());
		styleSaveStatement.setInt(1, player.getDatabaseId());
		colorSaveStatement.setInt(1, player.getDatabaseId());

		Appearance appearance = player.getAppearance();

		genderSaveStatement.setInt(2, appearance.getGender().toInteger());

		for (int style : appearance.getStyle()) {
			styleSaveStatement.setInt(2, style);
			styleSaveStatement.addBatch();
		}

		for (int color : appearance.getColors()) {
			colorSaveStatement.setInt(2, color);
			colorSaveStatement.addBatch();
		}

		genderSaveStatement.execute();
		styleSaveStatement.executeBatch();
		colorSaveStatement.executeBatch();
	}

}