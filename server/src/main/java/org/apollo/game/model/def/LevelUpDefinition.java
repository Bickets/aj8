package org.apollo.game.model.def;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apollo.util.GsonUtil;

/**
 * Represents a level up definition.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class LevelUpDefinition {

    /**
     * An array of level up definitions.
     */
    private static LevelUpDefinition[] definitions;

    /**
     * The interface id of this definition.
     */
    private final int interfaceId;

    /**
     * The first child id of this definition.
     */
    private final int firstChildId;

    /**
     * The second child id of this definition.
     */
    private final int secondChildId;

    /**
     * Constructs a new {@link LevelUpDefinition}.
     *
     * @param interfaceId The interface id.
     * @param firstChildId The first child id.
     * @param secondChildId The second child if.
     */
    public LevelUpDefinition(int interfaceId, int firstChildId, int secondChildId) {
	this.interfaceId = interfaceId;
	this.firstChildId = firstChildId;
	this.secondChildId = secondChildId;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

    /**
     * Returns the first child id.
     */
    public int getFirstChildId() {
	return firstChildId;
    }

    /**
     * Returns the second child id.
     */
    public int getSecondChildId() {
	return secondChildId;
    }

    /**
     * Initializes the definitions.
     *
     * @throws IOException If some I/O exception occurs.
     */
    public static void init() throws IOException {
	Path path = Paths.get("data/io", "level_up_definitions.json");
	try (BufferedReader reader = Files.newBufferedReader(path)) {
	    definitions = GsonUtil.fromJson(reader, LevelUpDefinition[].class);
	}
    }

    /**
     * Returns a single level up definition from the specified id if such
     * definition exists.
     *
     * @param id The id of the definition.
     * @return A single level up definition if and only if the specified
     *         definition from the id exists, otherwise {@code null}.
     * @throws IndexOutOfBoundsException If the specified id is out of bounds.
     */
    public static LevelUpDefinition fromId(int id) {
	if (id < 0 || id >= definitions.length) {
	    throw new IndexOutOfBoundsException(id + " is out of bounds.");
	}
	return definitions[id];
    }

}