package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.io.player.PlayerSanctionProvider;
import org.apollo.io.player.PlayerSanctionResponse;
import org.apollo.security.PlayerCredentials;

public final class JdbcSanctionProvider implements PlayerSanctionProvider {

    private final PreparedStatement sanctionStatement;
    private final PreparedStatement closeStatement;

    protected JdbcSanctionProvider(Connection connection) throws SQLException {
	sanctionStatement = connection.prepareStatement("SELECT UNIX_TIMESTAMP() as now, id, type, UNIX_TIMESTAMP(expire) as expire FROM sanctions WHERE ((username = ?) OR (address IS NOT NULL AND address = ?)) AND active = 0 ORDER BY id DESC;");
	closeStatement = connection.prepareStatement("UPDATE sanctions SET active = 1 WHERE id = ?;");
    }

    @Override
    public PlayerSanctionResponse check(PlayerCredentials credentials) throws IOException, SQLException {
	sanctionStatement.setString(1, credentials.getUsername());
	sanctionStatement.setString(2, credentials.getAddress());

	PlayerSanctionResponse response = PlayerSanctionResponse.OK;

	try (ResultSet set = sanctionStatement.executeQuery()) {
	    while (set.next()) {
		int id = set.getInt("id");
		long now = set.getLong("now");
		long expire = set.getLong("expire");
		String type = set.getString("type");

		if (expire != 0 && now >= expire) {
		    closeStatement.setInt(1, id);
		    closeStatement.executeUpdate();
		    continue;
		}

		switch (type) {
    		case "disabled":
    		    return response = PlayerSanctionResponse.DISABLED;
    		case "muted":
    		    response = PlayerSanctionResponse.OK;
    		    break;
		}
	    }
	}

	return response;
    }

}