package org.apollo.game.model.def;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apollo.net.codec.game.GamePacketType;
import org.apollo.util.GsonUtil;

/**
 * Represents meta-data about game packets.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GamePacketDefinition {

	/**
	 * The length of a packet used to denote a variable byte header.
	 */
	public static final int VAR_BYTE = -1;

	/**
	 * The length of a packet used to denote a variable short header.
	 */
	public static final int VAR_SHORT = -2;

	/**
	 * Represents the opcode of this packet definition.
	 */
	private final int opcode;

	/**
	 * Represents the length of this packet definition.
	 */
	private final int length;

	/**
	 * Constructs a new {@link GamePacketDefinition} with the specified opcode
	 * and length.
	 *
	 * @param opcode The opcode of this game packet definition.
	 * @param length The length of this game packet definition.
	 */
	protected GamePacketDefinition(int opcode, int length) {
		this.opcode = opcode;
		this.length = length;
	}

	/**
	 * Represents the incoming game packet definitions.
	 */
	private static GamePacketDefinition[] incomingDefinitions;

	/**
	 * Represents the outgoing game packet definitions.
	 */
	private static GamePacketDefinition[] outgoingDefinitions;

	/**
	 * Initializes the definitions.
	 *
	 * @throws IOException If some I/O error occurs.
	 */
	public static void init() throws IOException {
		Path path = Paths.get("data/io", "incoming_packet_meta.json");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			incomingDefinitions = GsonUtil.fromJson(reader, GamePacketDefinition[].class);
		}

		path = Paths.get("data/io", "outgoing_packet_meta.json");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			outgoingDefinitions = GsonUtil.fromJson(reader, GamePacketDefinition[].class);
		}
	}

	/**
	 * Returns the incoming game packet definition for the specified opcode if
	 * it is within bounds otherwise a {@link IndexOutOfBoundsException} is
	 * thrown.
	 *
	 * @param opcode The opcode.
	 * @return The incoming game packet definition.
	 */
	public static GamePacketDefinition incomingDefinition(int opcode) {
		if (opcode < 0 || opcode > incomingDefinitions.length) {
			throw new IndexOutOfBoundsException();
		}

		return incomingDefinitions[opcode];
	}

	/**
	 * Returns the outgoing game packet definition for the specified opcode if
	 * it is within bounds otherwise a {@link IndexOutOfBoundsException} is
	 * thrown.
	 *
	 * @param opcode The opcode.
	 * @return The outgoing game packet definition.
	 */
	public static GamePacketDefinition outgoingDefinition(int opcode) {
		if (opcode < 0 || opcode > outgoingDefinitions.length) {
			throw new IndexOutOfBoundsException();
		}

		return outgoingDefinitions[opcode];
	}

	/**
	 * Returns the type of this game packet definition as specified by its
	 * length.
	 */
	public GamePacketType getType() {
		switch (length) {
		case VAR_BYTE:
			return GamePacketType.VARIABLE_BYTE;
		case VAR_SHORT:
			return GamePacketType.VARIABLE_SHORT;
		default:
			return GamePacketType.FIXED;
		}
	}

	/**
	 * Returns this game packet definitions opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Returns this game packet definitions length.
	 */
	public int getLength() {
		return length;
	}

}