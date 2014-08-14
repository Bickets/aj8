package org.apollo.game.sync.block;

/**
 * Represents the transform {@link SynchronizationBlock}. This synchronization
 * block transforms a mob into that of another mob.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TransformBlock extends SynchronizationBlock {

    /**
     * The id of the mob to transform into.
     */
    private final int id;

    /**
     * Constructs a new {@link TransformBlock}.
     *
     * @param id The id of the mob to transform into.
     */
    protected TransformBlock(int id) {
	this.id = id;
    }

    /**
     * Returns the transform id.
     *
     * @return The transform id.
     */
    public int getId() {
	return id;
    }

}