package org.apollo.game.msg.impl;

import java.awt.Point;

import org.apollo.game.msg.Message;

/**
 * A message which is decoded when the mouse is clicked on the game screen.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MouseClickMessage implements Message {

	/**
	 * Represents the type of click, left or right.
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum ClickType {
		/**
		 * Represents a left click.
		 */
		LEFT_CLICK(0),

		/**
		 * Represents a right click.
		 */
		RIGHT_CLICK(1);

		/**
		 * The identifier of each click type.
		 */
		private final int id;

		/**
		 * Constructs a new {@link ClickType} with the specified click
		 * identifier.
		 *
		 * @param id The identifier of this click type.
		 */
		private ClickType(int id) {
			this.id = id;
		}

		/**
		 * Returns a click type for the specified id or throws an
		 * {@link IllegalArgumentException} if the click type doesn't exist.
		 *
		 * @param id The identifier of the click type we're trying to find.
		 * @return The click type, if possible.
		 */
		public static ClickType forId(int id) {
			for (ClickType type : values()) {
				if (type.id == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Unknown click type for id: " + id);
		}
	}

	/**
	 * The time of this click compared to the last click received.
	 */
	private final int time;

	/**
	 * The type of click.
	 */
	private final ClickType type;

	/**
	 * The point on the game screen where the user clicked.
	 */
	private final Point point;

	/**
	 * Constructs a new {@link MouseClickEvent} with the specified time, type
	 * and point.
	 *
	 * @param time The time from the last occurrence of a click.
	 * @param type The type of this click.
	 * @param point The point where this click occured.
	 */
	public MouseClickMessage(int time, ClickType type, Point point) {
		this.time = time;
		this.type = type;
		this.point = point;
	}

	/**
	 * Returns the time of this click.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Returns the type of this click.
	 */
	public ClickType getType() {
		return type;
	}

	/**
	 * Returns the point of occurrence of this click.
	 */
	public Point getPoint() {
		return point;
	}

}