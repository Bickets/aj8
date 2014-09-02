package org.apollo.game.model;

/**
 * Creates an {@link Item} that is placed on the ground somewhere in the game
 * world.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public class GroundItem extends Entity {

    /**
     * The owner of the ground item.
     */
    private final Player owner;

    /**
     * The actual item on the ground.
     */
    private final Item item;

    /**
     * Constructs a new ground item.
     *
     * @param owner The owner of the ground item.
     * @param item The item on the ground.
     * @param position The position of the item.
     */
    public GroundItem(Player owner, Item item, Position position) {
	super(position);
	this.owner = owner;
	this.item = item;
    }

    /**
     * Returns the owner of the ground item.
     *
     * @return The owner of the item.
     */
    public Player getOwner() {
	return owner;
    }

    /**
     * Returns the item on the ground.
     *
     * @return The item.
     */
    public Item getItem() {
	return item;
    }

    @Override
    public EntityType type() {
	return EntityType.GROUND_ITEM;
    }

    @Override
    public int size() {
	return 1;
    }

}