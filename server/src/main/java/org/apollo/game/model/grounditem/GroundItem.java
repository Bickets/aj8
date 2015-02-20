package org.apollo.game.model.grounditem;

import org.apollo.game.model.Entity;
import org.apollo.game.model.Item;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;

/**
 * Creates an {@link Item} that is placed on the ground somewhere in the game
 * world.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GroundItem extends Entity {

	/**
	 * How many pulses until this ground item becomes global, 60 seconds.
	 */
	public static final int PULSES_UNTIL_GLOBAL = 60 * 1000 / 600;

	/**
	 * How many pulses until this ground item becomes invalid, 150 seconds.
	 */
	public static final int PULSES_UNTIL_INVALID = 150 * 1000 / 600;

	/**
	 * The item displayed on the ground.
	 */
	private final Item item;

	/**
	 * Constructs a new {@link GroundItem} with the specified position, item
	 * owner and global flag.
	 *
	 * @param position The position of this ground item.
	 * @param world The world this ground item is in.
	 * @param item The ground item.
	 */
	public GroundItem(Position position, World world, Item item) {
		super(position, world);
		this.item = item;
	}

	/**
	 * Returns the ground item.
	 */
	public Item getItem() {
		return item;
	}

	@Override
	public EntityCategory getCategory() {
		return EntityCategory.GROUND_ITEM;
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroundItem) {
			GroundItem other = (GroundItem) obj;
			return other.item.equals(item);
		}

		return false;
	}

}