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

    public static final int VAR_BYTE = -1;
    public static final int VAR_SHORT = -2;

    private final int opcode;
    private final int length;

    protected GamePacketDefinition(int opcode, int length) {
	this.opcode = opcode;
	this.length = length;
    }

    private static GamePacketDefinition[] incomingDefinitions;
    private static GamePacketDefinition[] outgoingDefinitions;

    public static void init() throws IOException {
	Path path = Paths.get("data/io", "incoming_packet_meta.json");
	try (BufferedReader reader = Files.newBufferedReader(path)) {
	    int[] values = GsonUtil.fromJson(reader, int[].class);
	    incomingDefinitions = new GamePacketDefinition[values.length];
	    for (int opcode = 0; opcode < values.length; opcode++) {
		incomingDefinitions[opcode] = new GamePacketDefinition(opcode, values[opcode]);
	    }
	}

	path = Paths.get("data/io", "outgoing_packet_meta.json");
	try (BufferedReader reader = Files.newBufferedReader(path)) {
	    int[] values = GsonUtil.fromJson(reader, int[].class);
	    outgoingDefinitions = new GamePacketDefinition[values.length];
	    for (int opcode = 0; opcode < values.length; opcode++) {
		outgoingDefinitions[opcode] = new GamePacketDefinition(opcode, values[opcode]);
	    }
	}
    }

    public static GamePacketDefinition incomingDefinition(int opcode) {
	if (opcode < 0 || opcode > incomingDefinitions.length) {
	    throw new IndexOutOfBoundsException();
	}

	return incomingDefinitions[opcode];
    }

    public static GamePacketDefinition outgoingDefinition(int opcode) {
	if (opcode < 0 || opcode > outgoingDefinitions.length) {
	    throw new IndexOutOfBoundsException();
	}

	return outgoingDefinitions[opcode];
    }

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

    public int getOpcode() {
	return opcode;
    }

    public int getLength() {
	return length;
    }

}
