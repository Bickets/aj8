package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * A {@link Table} which serializes player inventories.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ItemsTable implements Table {

	/**
	 * A prepared statement which selects the players inventory from the
	 * database.
	 */
	private final PreparedStatement loadStatement;

	/**
	 * A prepared statement which inserts the players inventory to the database.
	 */
	private final PreparedStatement saveStatement;

	/**
	 * Represents the type of inventory as a {@code String}.
	 */
	private final String type;

	/**
	 * Constructs a new {@link ItemsTable} with the specified database
	 * connection and inventory type.
	 *
	 * @param connection The database connection.
	 * @param type The type of inventory.
	 * @throws SQLException If some database access error occurs.
	 */
	protected ItemsTable(Connection connection, String type) throws SQLException {
		loadStatement = connection.prepareStatement("SELECT * FROM items WHERE player_id = ? AND type = ?;");
		saveStatement = connection.prepareStatement("INSERT INTO items (player_id, type, slot, item, amount) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE item = VALUES(item), amount = VALUES(amount);");
		this.type = type;
	}

	/**
	 * Returns an the inventory instance for a specified player.
	 *
	 * @param player The player who owns the inventory.
	 * @return The inventory.
	 */
	public abstract Inventory getInventory(Player player);

	@Override
	public void load(Player player) throws SQLException, IOException {
		loadStatement.setInt(1, player.getDatabaseId());
		loadStatement.setString(2, type);

		Inventory inventory = getInventory(player);
		try (ResultSet set = loadStatement.executeQuery()) {
			while (set.next()) {
				int slot = set.getInt("slot");
				int item = set.getInt("item");
				int amount = set.getInt("amount");

				if (set.wasNull()) {
					inventory.set(slot, null);
				} else {
					inventory.set(slot, new Item(item, amount));
				}
			}
		}
	}

	@Override
	public void save(Player player) throws SQLException, IOException {
		saveStatement.setInt(1, player.getDatabaseId());
		saveStatement.setString(2, type);

		Inventory inventory = getInventory(player);
		Item[] items = inventory.getItems();
		for (int slot = 0; slot < items.length; slot++) {
			Item item = items[slot];

			saveStatement.setInt(3, slot);
			if (item == null) {
				saveStatement.setNull(4, Types.INTEGER);
				saveStatement.setNull(5, Types.INTEGER);
			} else {
				saveStatement.setInt(4, item.getId());
				saveStatement.setInt(5, item.getAmount());
			}

			saveStatement.addBatch();
		}

		saveStatement.executeBatch();
	}

}