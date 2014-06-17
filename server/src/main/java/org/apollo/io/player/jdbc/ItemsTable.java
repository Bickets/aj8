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

public abstract class ItemsTable extends Table {

    private final PreparedStatement loadStatement;
    private final PreparedStatement saveStatement;
    private final String type;

    public ItemsTable(Connection connection, String type) throws SQLException {
	loadStatement = connection.prepareStatement("SELECT * FROM items WHERE player_id = ? AND type = ?;");
	saveStatement = connection.prepareStatement("INSERT INTO items (player_id, type, slot, item, amount) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE item = VALUES(item), amount = VALUES(amount);");
	this.type = type;
    }

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
