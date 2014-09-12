package org.apollo.game.model;

/**
 * Creates an {@link Item} that is placed on the ground somewhere in the game
 * world.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
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
	 * The owner of this ground item represented by the name hash of a
	 * {@link Player}, <tt>-1</tt> if this ground item has no owner, global.
	 */
	private final int owner;

	/**
	 * A flag denoting whether or not this ground item is global.
	 */
	private final boolean global;

	/**
	 * Constructs a new {@link GroundItem} with the specified position, item
	 * owner and global flag.
	 *
	 * @param position The position of this ground item.
	 * @param world The world this ground item is in.
	 * @param item The ground item.
	 * @param owner The owner of the ground item, <tt>-1</tt> denotes no owner
	 *            and that the item is global.
	 * @param global The global status of this item.
	 */
	public GroundItem(Position position, World world, Item item, int owner, boolean global) {
		super(position, world);
		this.item = item;
		this.owner = owner;
		this.global = global;
	}

	/**
	 * Constructs a new {@link GroundItem} that is not global, it is assiged to
	 * the specified {@link Player}.
	 *
	 * @param position The position of the ground item.
	 * @param world The world this ground item is in.
	 * @param item The ground item.
	 * @param player The player this ground item is assigned to.
	 */
	public GroundItem(Position position, World world, Item item, Player player) {
		this(position, world, item, player.hashCode(), false);
	}

	/**
	 * Constructs a new {@link GroundItem} that is global.
	 *
	 * @param position The position of the ground item.
	 * @param world The world this ground item is in.
	 * @param item The ground item.
	 */
	public GroundItem(Position position, World world, Item item) {
		this(position, world, item, -1, true);
	}

	/**
	 * Returns the ground item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Returns the owner of this ground item.
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * Returns whether or not this ground item is global.
	 */
	public boolean isGlobal() {
		return global;
	}

	@Override
	public EntityCategory getCategory() {
		return EntityCategory.GROUND_ITEM;
	}

	@Override
	public int getSize() {
		return 1;
	}

}