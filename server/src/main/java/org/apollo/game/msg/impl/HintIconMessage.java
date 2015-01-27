package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which displays a 'hint' arrow icon over some entity within the game
 * world.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class HintIconMessage implements Message {

	/**
	 * The different types for hint icons.
	 *
	 * @author James Monger
	 */
	public enum HintIconType {
		/**
		 * The different types of hint icon and their numeric values.
		 */
		MOB(1), CENTRAL(2), WEST(3), EAST(4), SOUTH(5), NORTH(6), PLAYER(10);

		/**
		 * Store the numeric value of the hint icon so that it can be sent to
		 * the client.
		 */
		private final int index;

		/**
		 * Construct a new hint icon.
		 *
		 * @param index The numeric value to to be sent to the client.
		 */
		private HintIconType(int index) {
			this.index = index;
		}

		/**
		 * Get the numeric value of a hint icon.
		 *
		 * @return The numeric value.
		 */
		public final int getIndex() {
			return index;
		}

	}

	/**
	 * The type of hint icon this message represents.
	 */
	private final HintIconType type;

	/**
	 * Constructs a new {@link HintIconMessage} with the specified type.
	 *
	 * @param type The hint icon type.
	 */
	public HintIconMessage(HintIconType type) {
		this.type = type;
	}

	/**
	 * Returns the hint icons type.
	 */
	public final HintIconType getType() {
		return type;
	}

}