package org.apollo.net.codec.game;

import static com.google.common.base.Preconditions.checkArgument;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.apollo.net.NetworkConstants;

/**
 * A class which assists in creating a {@link GamePacket}.
 *
 * @author Graham
 */
public final class GamePacketBuilder {

	/**
	 * The opcode.
	 */
	private final int opcode;

	/**
	 * The {@link GamePacketType}.
	 */
	private final GamePacketType type;

	/**
	 * The buffer.
	 */
	private final ByteBuf buffer = Unpooled.buffer();

	/**
	 * The current mode.
	 */
	private AccessMode mode = AccessMode.BYTE_ACCESS;

	/**
	 * The current bit index.
	 */
	private int bitIndex;

	/**
	 * Creates a raw {@link GamePacketBuilder}.
	 */
	public GamePacketBuilder() {
		opcode = -1;
		type = GamePacketType.RAW;
	}

	/**
	 * Creates the {@link GamePacketBuilder} for a {@link GamePacketType#FIXED}
	 * packet with the specified opcode.
	 *
	 * @param opcode The opcode.
	 */
	public GamePacketBuilder(int opcode) {
		this(opcode, GamePacketType.FIXED);
	}

	/**
	 * Creates the {@link GamePacketBuilder} for the specified packet type and
	 * opcode.
	 *
	 * @param opcode The opcode.
	 * @param type The packet type.
	 */
	public GamePacketBuilder(int opcode, GamePacketType type) {
		this.opcode = opcode;
		this.type = type;
	}

	/**
	 * Creates a {@link GamePacket} based on the current contents of this
	 * builder.
	 *
	 * @return The {@link GamePacket}.
	 */
	public GamePacket toGamePacket() {
		checkArgument(type == GamePacketType.RAW, "Raw packets cannot be built.");
		checkArgument(mode != AccessMode.BYTE_ACCESS, "Only byte accessed packets may be build, switch modes.");
		return new GamePacket(opcode, type, buffer);
	}

	/**
	 * Gets the current length of the builder's buffer.
	 *
	 * @return The length of the buffer.
	 */
	public int getLength() {
		checkByteAccess();
		return buffer.writerIndex();
	}

	/**
	 * Switches this builder's mode to the byte access mode.
	 */
	public void switchToByteAccess() {
		checkArgument(mode == AccessMode.BYTE_ACCESS, "Already in byte access mode.");
		mode = AccessMode.BYTE_ACCESS;
		buffer.writerIndex((bitIndex + 7) / 8);
	}

	/**
	 * Switches this builder's mode to the bit access mode.
	 */
	public void switchToBitAccess() {
		checkArgument(mode == AccessMode.BIT_ACCESS, "Already in bit access mode.");
		mode = AccessMode.BIT_ACCESS;
		bitIndex = buffer.writerIndex() * 8;
	}

	/**
	 * Puts a raw builder. Both builders (this and parameter) must be in byte
	 * access mode.
	 *
	 * @param builder The builder.
	 */
	public void putRawBuilder(GamePacketBuilder builder) {
		checkByteAccess();
		checkArgument(builder.type != GamePacketType.RAW, "Builder must be raw!");
		builder.checkByteAccess();
		putBytes(builder.buffer);
	}

	/**
	 * Puts a raw builder in reverse. Both builders (this and parameter) must be
	 * in byte access mode.
	 *
	 * @param builder The builder.
	 */
	public void putRawBuilderReverse(GamePacketBuilder builder) {
		checkByteAccess();
		checkArgument(builder.type != GamePacketType.RAW, "Builder must be raw!");
		builder.checkByteAccess();
		putBytesReverse(builder.buffer);
	}

	/**
	 * Puts a standard data type with the specified value.
	 *
	 * @param type The data type.
	 * @param value The value.
	 * @throws IllegalStateException if this reader is not in byte access mode.
	 */
	public void put(DataType type, Number value) {
		put(type, DataOrder.BIG, DataTransformation.NONE, value);
	}

	/**
	 * Puts a standard data type with the specified value and byte order.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @param value The value.
	 */
	public void put(DataType type, DataOrder order, Number value) {
		put(type, order, DataTransformation.NONE, value);
	}

	/**
	 * Puts a standard data type with the specified value and transformation.
	 *
	 * @param type The type.
	 * @param transformation The transformation.
	 * @param value The value.
	 */
	public void put(DataType type, DataTransformation transformation, Number value) {
		put(type, DataOrder.BIG, transformation, value);
	}

	/**
	 * Puts a standard data type with the specified value, byte order and
	 * transformation.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @param transformation The transformation.
	 * @param value The value.
	 * @throws IllegalArgumentException if the combination is invalid.
	 */
	public void put(DataType type, DataOrder order, DataTransformation transformation, Number value) {
		checkByteAccess();
		long longValue = value.longValue();
		int length = type.getBytes();
		if (order == DataOrder.BIG) {
			for (int i = length - 1; i >= 0; i--) {
				if (i == 0 && transformation != DataTransformation.NONE) {
					if (transformation == DataTransformation.ADD) {
						buffer.writeByte((byte) (longValue + 128));
					} else if (transformation == DataTransformation.NEGATE) {
						buffer.writeByte((byte) -longValue);
					} else if (transformation == DataTransformation.SUBTRACT) {
						buffer.writeByte((byte) (128 - longValue));
					} else {
						throw new IllegalArgumentException("unknown transformation");
					}
				} else {
					buffer.writeByte((byte) (longValue >> i * 8));
				}
			}
		} else if (order == DataOrder.LITTLE) {
			for (int i = 0; i < length; i++) {
				if (i == 0 && transformation != DataTransformation.NONE) {
					if (transformation == DataTransformation.ADD) {
						buffer.writeByte((byte) (longValue + 128));
					} else if (transformation == DataTransformation.NEGATE) {
						buffer.writeByte((byte) -longValue);
					} else if (transformation == DataTransformation.SUBTRACT) {
						buffer.writeByte((byte) (128 - longValue));
					} else {
						throw new IllegalArgumentException("unknown transformation");
					}
				} else {
					buffer.writeByte((byte) (longValue >> i * 8));
				}
			}
		} else if (order == DataOrder.MIDDLE) {
			if (transformation != DataTransformation.NONE) {
				throw new IllegalArgumentException("middle endian cannot be transformed");
			}
			if (type != DataType.INT) {
				throw new IllegalArgumentException("middle endian can only be used with an integer");
			}
			buffer.writeByte((byte) (longValue >> 8));
			buffer.writeByte((byte) longValue);
			buffer.writeByte((byte) (longValue >> 24));
			buffer.writeByte((byte) (longValue >> 16));
		} else if (order == DataOrder.INVERSED_MIDDLE) {
			if (transformation != DataTransformation.NONE) {
				throw new IllegalArgumentException("inversed middle endian cannot be transformed");
			}
			if (type != DataType.INT) {
				throw new IllegalArgumentException("inversed middle endian can only be used with an integer");
			}
			buffer.writeByte((byte) (longValue >> 16));
			buffer.writeByte((byte) (longValue >> 24));
			buffer.writeByte((byte) longValue);
			buffer.writeByte((byte) (longValue >> 8));
		} else {
			throw new IllegalArgumentException("unknown order");
		}
	}

	/**
	 * Puts a string into the buffer.
	 *
	 * @param str The string.
	 */
	public void putString(String str) {
		checkByteAccess();
		buffer.writeBytes(str.getBytes());
		buffer.writeByte(NetworkConstants.STRING_TERMINATOR);
	}

	/**
	 * Puts a smart into the buffer.
	 *
	 * @param value The value.
	 */
	public void putSmart(int value) {
		checkByteAccess();
		if (value < 128) {
			buffer.writeByte(value);
		} else {
			buffer.writeShort(value);
		}
	}

	/**
	 * Puts the bytes from the specified buffer into this packet's buffer.
	 *
	 * @param buffer The source {@link ByteBuf}.
	 */
	public void putBytes(ByteBuf buffer) {
		byte[] bytes = new byte[buffer.readableBytes()];
		buffer.markReaderIndex();
		try {
			buffer.readBytes(bytes);
		} finally {
			buffer.resetReaderIndex();
		}
		putBytes(bytes);
	}

	/**
	 * Puts the bytes from the specified buffer into this packet's buffer, in
	 * reverse.
	 *
	 * @param buffer The source {@link ByteBuf}.
	 */
	public void putBytesReverse(ByteBuf buffer) {
		byte[] bytes = new byte[buffer.readableBytes()];
		buffer.markReaderIndex();
		try {
			buffer.readBytes(bytes);
		} finally {
			buffer.resetReaderIndex();
		}
		putBytesReverse(bytes);
	}

	/**
	 * Puts the specified byte array into the buffer.
	 *
	 * @param bytes The byte array.
	 */
	public void putBytes(byte[] bytes) {
		buffer.writeBytes(bytes);
	}

	/**
	 * Puts the bytes into the buffer with the specified transformation.
	 *
	 * @param transformation The transformation.
	 * @param bytes The byte array.
	 */
	public void putBytes(DataTransformation transformation, byte[] bytes) {
		if (transformation == DataTransformation.NONE) {
			putBytes(bytes);
		} else {
			for (byte b : bytes) {
				put(DataType.BYTE, transformation, b);
			}
		}
	}

	/**
	 * Puts the specified byte array into the buffer in reverse.
	 *
	 * @param bytes The byte array.
	 */
	public void putBytesReverse(byte[] bytes) {
		checkByteAccess();
		for (int i = bytes.length - 1; i >= 0; i--) {
			buffer.writeByte(bytes[i]);
		}
	}

	/**
	 * Puts the specified byte array into the buffer in reverse with the
	 * specified transformation.
	 *
	 * @param transformation The transformation.
	 * @param bytes The byte array.
	 */
	public void putBytesReverse(DataTransformation transformation, byte[] bytes) {
		if (transformation == DataTransformation.NONE) {
			putBytesReverse(bytes);
		} else {
			for (int i = bytes.length - 1; i >= 0; i--) {
				put(DataType.BYTE, transformation, bytes[i]);
			}
		}
	}

	/**
	 * Puts a single bit into the buffer. If {@code flag} is {@code true}, the
	 * value of the bit is {@code 1}. If {@code flag} is {@code false}, the
	 * value of the bit is {@code 0}.
	 *
	 * @param flag The flag.
	 */
	public void putBit(boolean flag) {
		putBit(flag ? 1 : 0);
	}

	/**
	 * Puts a single bit into the buffer with the value {@code value}.
	 *
	 * @param value The value.
	 */
	public void putBit(int value) {
		putBits(1, value);
	}

	/**
	 * Puts {@code numBits} into the buffer with the value {@code value}.
	 *
	 * @param amount The number of bits to put into the buffer.
	 * @param value The value.
	 */
	public void putBits(int amount, int value) {
		checkArgument(amount < 0 || amount > 32, "Amount of bits must be between 1 and 32 inclusive.");

		checkBitAccess();

		int bytePos = bitIndex >> 3;
		int bitOffset = 8 - (bitIndex & 7);
		bitIndex += amount;

		int requiredSpace = bytePos - buffer.writerIndex() + 1;
		requiredSpace += (amount + 7) / 8;
		buffer.ensureWritable(requiredSpace);

		for (; amount > bitOffset; bitOffset = 8) {
			int tmp = buffer.getByte(bytePos);
			tmp &= ~DataConstants.BIT_MASK[bitOffset];
			tmp |= value >> amount - bitOffset & DataConstants.BIT_MASK[bitOffset];
			buffer.setByte(bytePos++, tmp);
			amount -= bitOffset;
		}
		if (amount == bitOffset) {
			int tmp = buffer.getByte(bytePos);
			tmp &= ~DataConstants.BIT_MASK[bitOffset];
			tmp |= value & DataConstants.BIT_MASK[bitOffset];
			buffer.setByte(bytePos, tmp);
		} else {
			int tmp = buffer.getByte(bytePos);
			tmp &= ~(DataConstants.BIT_MASK[amount] << bitOffset - amount);
			tmp |= (value & DataConstants.BIT_MASK[amount]) << bitOffset - amount;
			buffer.setByte(bytePos, tmp);
		}
	}

	/**
	 * Checks that this builder is in the byte access mode.
	 */
	private void checkByteAccess() {
		checkArgument(mode != AccessMode.BYTE_ACCESS, "For byte-based calls to work, the mode must be byte access");
	}

	/**
	 * Checks that this builder is in the bit access mode.
	 */
	private void checkBitAccess() {
		checkArgument(mode != AccessMode.BIT_ACCESS, "For bit-based calls to work, the mode must be bit access");
	}

}