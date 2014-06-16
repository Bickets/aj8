package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Player;

public final class AppearanceTable extends Table {

    private final PreparedStatement loadStatement;
    private final PreparedStatement saveStatement;

    public AppearanceTable(Connection connection) throws SQLException {
	loadStatement = connection.prepareStatement("SELECT * FROM appearance WHERE player_id = ?;");
	saveStatement = connection.prepareStatement("INSERT INTO appearance (player_id, gender, style, color) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE gender = VALUES(gender), style = VALUES(style), color = VALUES(color);");
    }

    @Override
    public void load(Player player) throws SQLException, IOException {
	loadStatement.setInt(1, player.getDatabaseId());

	try (ResultSet set = loadStatement.executeQuery()) {
	    while (set.next()) {
		String style = set.getString("style");
		String color = set.getString("color");
		int genderInt = set.getInt("gender");

		Gender gender = Gender.valueOf(genderInt);
//		String[] _styles = StringUtils.split(style, DELIMITER);
//		String[] _colors = StringUtils.split(color, DELIMITER);

//		int[] styles = ArrayUtil.parseIntegerArray(_styles);
//		int[] colors = ArrayUtil.parseIntegerArray(_colors);

//		player.setAppearance(new Appearance(gender, styles, colors));
	    }
	}
    }

    @Override
    public void save(Player player) throws SQLException, IOException {
	saveStatement.setInt(1, player.getDatabaseId());

	Appearance appearance = player.getAppearance();

	saveStatement.setInt(2, appearance.getGender().toInteger());

//	String styles = StringUtils.join(appearance.getStyle(), DELIMITER);
//	String colors = StringUtils.join(appearance.getColors(), DELIMITER);
//
//	saveStatement.setString(3, styles);
//	saveStatement.setString(4, colors);

	saveStatement.execute();
    }

}