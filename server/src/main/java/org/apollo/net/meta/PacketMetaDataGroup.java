package org.apollo.net.meta;

/**
 * A class which contains a group of {@link PacketMetaData} objects.
 * 
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PacketMetaDataGroup {

    /**
     * The incoming packet lengths array.
     */
    public static final int[] PACKET_LENGTHS = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
	    0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
	    0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
	    0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
	    2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
	    0, 0, 0, 12, 0, 0, 0, 0, 8, 0, // 50
	    0, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
	    6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
	    0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
	    0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
	    0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
	    0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
	    1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
	    0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
	    0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
	    0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
	    0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
	    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
	    0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
	    0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
	    2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
	    4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
	    0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
	    1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
	    0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
	    0, 0, 6, 6, 0, 0, // 250
    };

    /**
     * Creates a {@link PacketMetaDataGroup} from the packet length array.
     * 
     * @param lengthArray The packet length array.
     * @return The {@link PacketMetaDataGroup} object.
     * @throws IllegalArgumentException if the array length is not 256 or if
     *             there is an element in the array with a value below -3.
     */
    public static PacketMetaDataGroup create() {
	if (PACKET_LENGTHS.length != 256) {
	    throw new IllegalArgumentException();
	}
	PacketMetaDataGroup grp = new PacketMetaDataGroup();
	for (int i = 0; i < PACKET_LENGTHS.length; i++) {
	    int length = PACKET_LENGTHS[i];
	    PacketMetaData metaData = null;
	    if (length < -3) {
		throw new IllegalArgumentException();
	    } else if (length == -2) {
		metaData = PacketMetaData.createVariableShort();
	    } else if (length == -1) {
		metaData = PacketMetaData.createVariableByte();
	    } else {
		metaData = PacketMetaData.createFixed(length);
	    }
	    grp.packets[i] = metaData;
	}
	return grp;
    }

    /**
     * The array of {@link PacketMetaData} objects.
     */
    private final PacketMetaData[] packets = new PacketMetaData[PACKET_LENGTHS.length];

    /**
     * This constructor should not be called directly. Use the {@link #create()}
     * method instead.
     */
    private PacketMetaDataGroup() {

    }

    /**
     * Gets the meta data for the specified packet.
     * 
     * @param opcode The opcode of the packet.
     * @return The {@link PacketMetaData}, or {@code null} if the packet does
     *         not exist.
     * @throws IllegalArgumentException if the opcode is not in the range 0 to
     *             255.
     */
    public PacketMetaData getMetaData(int opcode) {
	if (opcode < 0 || opcode >= packets.length) {
	    throw new ArrayIndexOutOfBoundsException();
	}
	return packets[opcode];
    }

}
