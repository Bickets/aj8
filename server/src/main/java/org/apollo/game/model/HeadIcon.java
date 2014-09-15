package org.apollo.game.model;

import org.apollo.game.model.HeadIcon.Icon;

/**
 * Represents a {@link GameCharacter}s head icons, any character may have
 * <b>two</b> head icons, one {@link Skull} and one {@link Prayer}, enabling a
 * second of either type will turn the active icon off and the queued one on.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The enumerated, {@link Icon} type.
 */
public final class HeadIcon<E extends Enum<E> & Icon> {

	/**
	 * This package-private icon functional interface is used to get a
	 * {@link HeadIcon}s id represented as a {@code int}. Implementing this
	 * interface is required for the type to be considered a head icon.
	 *
	 * <p>
	 * This is a functional interface whose functional method is
	 * {@link #getId()}
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	@FunctionalInterface
	protected interface Icon {

		/**
		 * Returns this icons id.
		 */
		int getId();

	}

	/**
	 * Represents a {@link GameCharacter}s skull icon.
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum Skull implements Icon {
		/**
		 * Represents a white skull icon.
		 */
		WHITE_SKULL(0),

		/**
		 * Represents a red skull icon.
		 */
		RED_SKULL(1);

		/**
		 * Represents the id of this skull icon.
		 */
		private final int id;

		/**
		 * Constructs a new {@link Skull} with the specified id.
		 *
		 * @param id The id of this skull icon.
		 */
		private Skull(int id) {
			this.id = id;
		}

		@Override
		public final int getId() {
			return id;
		}

	}

	/**
	 * Represents a {@link GameCharacter}s prayer icon.
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum Prayer implements Icon {
		/**
		 * Represents the protect melee prayer icon.
		 */
		PROTECT_MELEE(0),

		/**
		 * Represents the protect missiles prayer icon.
		 */
		PROTECT_MISSILES(1),

		/**
		 * Represents the protect magic prayer icon.
		 */
		PROTECTED_MAGIC(2),

		/**
		 * Represents the retribution prayer icon.
		 */
		RETRIBUTION(3),

		/**
		 * Represents the smite prayer icon.
		 */
		SMITE(4),

		/**
		 * Represents the redemption prayer icon.
		 */
		REDEMPTION(5),

		/**
		 * Represents the protect missiles and magic prayer icon.
		 */
		PROTECT_MISSILES_MAGIC(6);

		/**
		 * Represents the id of this prayer icon.
		 */
		private final int id;

		/**
		 * Constructs a new {@link Prayer} with the specified id.
		 *
		 * @param id The id of this prayer icon.
		 */
		private Prayer(int id) {
			this.id = id;
		}

		@Override
		public final int getId() {
			return id;
		}

	}

	/**
	 * Represents the current icon, {@code null} if no icon is set.
	 */
	private E icon;

	/**
	 * Constructs a new {@link HeadIcon} with the initial icon.
	 *
	 * @param icon The initial icon.
	 */
	public HeadIcon(E icon) {
		this.icon = icon;
	}

	/**
	 * Constructs a new {@link HeadIcon} with no initial icon.
	 */
	public HeadIcon() {
		this(null);
	}

	/**
	 * Returns the currently active icon, {@code null} if there is no active
	 * icon.
	 */
	public E getActive() {
		return icon;
	}

	/**
	 * Sets the currently active icon.
	 */
	public void setIcon(E icon) {
		this.icon = icon;
	}

	/**
	 * Clears the currently active icon.
	 */
	public void clear() {
		icon = null;
	}

	/**
	 * Returns this head icons id.
	 */
	public int getId() {
		return icon == null ? -1 : icon.getId();
	}

}