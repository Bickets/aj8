package org.apollo.io.player.jdbc;

import static java.time.temporal.ChronoField.MILLI_OF_DAY;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;

import org.apollo.game.crypto.BCrypt;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.PlayerConstants;
import org.apollo.game.model.World;
import org.apollo.io.player.PlayerSanctionProvider;
import org.apollo.io.player.PlayerSanctionResponse;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.io.player.PlayerSerializerResponse;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.security.PlayerCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link PlayerSerializer} implementation which supports the JDBC MySQL
 * protocol.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class JdbcPlayerSerializer extends PlayerSerializer implements Closeable {

	/**
	 * Represents the maximum amount of failed logins.
	 */
	private static final int MAXIMUM_FAILED_LOGIN_ATTEMPTS = 5;

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(JdbcPlayerSerializer.class);

	/**
	 * The database connection.
	 */
	private final Connection connection;

	/**
	 * A prepared statement which selects the necessary information from the
	 * players table required in order to query the {@link #tables}.
	 */
	private final PreparedStatement loginStatement;

	/**
	 * A prepared statement which selects information about the current amount
	 * of failed login attempts.
	 */
	private final PreparedStatement selectFailedLogins;

	/**
	 * A prepared statement which inserts failed login user information to the
	 * database.
	 */
	private final PreparedStatement insertFailedLogins;

	/**
	 * A prepared statement which closes failed login users.
	 */
	private final PreparedStatement closeFailedLogins;

	/**
	 * The sanction provider which checks a players sanctions.
	 */
	private final PlayerSanctionProvider sanctionProvider;

	/**
	 * An array of {@link Table}s used to load and save player information.
	 */
	private final Table[] tables;

	/**
	 * Constructs a new {@link JdbcPlayerSerializer} with the specified url,
	 * username and password used for connecting to the database.
	 *
	 * @param url The database url.
	 * @param username The database username.
	 * @param password The database password.
	 * @param world The world this player is in.
	 * @throws SQLException If some database access error occurs.
	 */
	public JdbcPlayerSerializer(String url, String username, String password, World world) throws SQLException {
		super(world);
		connection = DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);
		sanctionProvider = new JdbcSanctionProvider(connection);
		loginStatement = connection.prepareStatement("SELECT id, password FROM players WHERE username = ?;");
		selectFailedLogins = connection.prepareStatement("SELECT UNIX_TIMESTAMP() as now, count, UNIX_TIMESTAMP(expire) as expire FROM failed_logins WHERE player_id = ?;");
		insertFailedLogins = connection.prepareStatement("INSERT INTO failed_logins (player_id, expire) VALUES (?, ?) ON DUPLICATE KEY UPDATE count = count + 1;");
		closeFailedLogins = connection.prepareStatement("DELETE FROM failed_logins WHERE player_id = ?;");
		tables = new Table[] { new PlayersTable(connection),
				new SkillsTable(connection), new AppearanceTable(connection),
				new SettingsTable(connection),
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
				Player player = new Player(credentials, PlayerConstants.SPAWN_POSITION, world);

				/* The account doesn't exist, let's create it. */
				if (!set.first()) {
					return new PlayerSerializerResponse(LoginConstants.STATUS_OK, player);
				}

				int id = set.getInt("id");
				String hashedPassword = set.getString("password");

				/*
				 * Check to be sure we aren't blocked from logging in.
				 */
				if (failedAttempts(id) >= MAXIMUM_FAILED_LOGIN_ATTEMPTS) {
					return new PlayerSerializerResponse(LoginConstants.STATUS_TOO_MANY_LOGINS);
				}

				/*
				 * Wrong password, increment failed count and return invalid
				 * credentials.
				 */
				if (!BCrypt.checkpw(credentials.getPassword(), hashedPassword)) {
					incrementFailedAttempts(id);
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
		} catch (SQLException | IOException e) {
			logger.error("Loading player {} failed.", credentials.getUsername(), e);
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
		} catch (SQLException | IOException e) {
			connection.rollback();
			logger.error("Saving player {} failed.", player.getName(), e);
		}
	}

	/**
	 * Returns the amount of failed login attempts for the specified players id.
	 *
	 * @param id The players id.
	 * @return Returns the amount of failed login attempts.
	 * @throws SQLException If some database access error occurs.
	 */
	private int failedAttempts(int id) throws SQLException {
		selectFailedLogins.setInt(1, id);

		try (ResultSet set = selectFailedLogins.executeQuery()) {
			if (!set.first()) {
				return 0;
			}

			long now = set.getLong("now");
			int count = set.getInt("count");
			long expire = set.getLong("expire");

			/* expired, we can remove it. */
			if (now >= expire) {
				closeFailedLogins.setInt(1, id);
				closeFailedLogins.execute();
				return 0;
			}

			return count;
		}
	}

	/**
	 * Increments the failed login attempts for the specified player's id.
	 *
	 * @param id The players id.
	 * @throws SQLException If some database access error occurs.
	 */
	private void incrementFailedAttempts(int id) throws SQLException {
		insertFailedLogins.setInt(1, id);

		LocalTime now = LocalTime.now();
		LocalTime expire = now.plus(1, MINUTES);
		Timestamp timestamp = new Timestamp(expire.getLong(MILLI_OF_DAY));

		insertFailedLogins.setTimestamp(2, timestamp);
		insertFailedLogins.execute();
	}

	@Override
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

}