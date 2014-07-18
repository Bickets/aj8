package org.apollo.io.player.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.game.crypto.BCrypt;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.PlayerConstants;
import org.apollo.io.player.PlayerSanctionProvider;
import org.apollo.io.player.PlayerSanctionResponse;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.io.player.PlayerSerializerResponse;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.security.PlayerCredentials;

public final class JdbcPlayerSerializer implements Closeable, PlayerSerializer {

    private static final Logger LOGGER = Logger.getGlobal();

    private final Connection connection;
    private final PreparedStatement loginStatement;
    private final PlayerSanctionProvider sanctionProvider;
    private final Table[] tables;

    public JdbcPlayerSerializer(String url, String username, String password) throws SQLException {
	connection = DriverManager.getConnection(url, username, password);
	connection.setAutoCommit(false);
	sanctionProvider = new JdbcSanctionProvider(connection);
	loginStatement = connection.prepareStatement("SELECT id, password FROM players WHERE username = ?;");
	tables = new Table[] { new PlayersTable(connection),
		new SkillsTable(connection), new AppearanceTable(connection),
		new ItemsTable(connection, "inventory") {
		    @Override
		    public Inventory getInventory(Player player) {
			return player.getInventory();
		    }
		}, new ItemsTable(connection, "equipment") {
		    @Override
		    public Inventory getInventory(Player player) {
			return player.getEquipment();
		    }
		}, new ItemsTable(connection, "bank") {
		    @Override
		    public Inventory getInventory(Player player) {
			return player.getBank();
		    }
		} };
    }

    @Override
    public PlayerSerializerResponse loadPlayer(PlayerCredentials credentials) throws IOException, SQLException {
	loginStatement.setString(1, credentials.getUsername());

	try {
	    PlayerSanctionResponse response = sanctionProvider.check(credentials);
	    if (response.getStatus() != LoginConstants.STATUS_OK) {
		return new PlayerSerializerResponse(response.getStatus());
	    }

	    try (ResultSet set = loginStatement.executeQuery()) {
		Player player = new Player(credentials, PlayerConstants.SPAWN_POSITION);

		/* The account doesn't exist, let's create it. */
		if (!set.first()) {
		    return new PlayerSerializerResponse(LoginConstants.STATUS_OK, player);
		}

		int id = set.getInt("id");
		String hashedPassword = set.getString("password");

		/*
		 * Wrong password, increment failed count and return invalid
		 * credentials.
		 */
		if (!BCrypt.checkpw(credentials.getPassword(), hashedPassword)) {
		    return new PlayerSerializerResponse(LoginConstants.STATUS_INVALID_CREDENTIALS);
		}

		/*
		 * Success, we can load the player's tables and set the database
		 * id.
		 */
		player.setDatabaseId(id);

		for (Table table : tables) {
		    table.load(player);
		}

		return new PlayerSerializerResponse(LoginConstants.STATUS_OK, player);

	    }
	} catch (SQLException | IOException ex) {
	    LOGGER.log(Level.SEVERE, "Loading player " + credentials.getUsername() + " failed.", ex);
	    return new PlayerSerializerResponse(LoginConstants.STATUS_COULD_NOT_COMPLETE);
	}
    }

    @Override
    public void savePlayer(Player player) throws IOException, SQLException {
	try {
	    for (Table table : tables) {
		table.save(player);
	    }

	    connection.commit();
	} catch (SQLException | IOException ex) {
	    connection.rollback();
	    LOGGER.log(Level.SEVERE, "Saving player " + player.getName() + " failed.", ex);
	}
    }

    @Override
    public void close() throws IOException {
	try {
	    connection.close();
	} catch (SQLException ex) {
	    throw new IOException(ex);
	}
    }

}
