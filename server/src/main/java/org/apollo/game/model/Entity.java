package org.apollo.game.model;

import org.apollo.util.EntityRepository;

/**
 * Represents an entity within the game world.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Entity {

    /**
     * Represents the type of this entity.
     * 
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     */
    public enum EntityType {
	PLAYER, MOB, GROUND_ITEM, GAME_OBJECT, PROJECTILE
    }

    /**
     * The index of this entity in the {@link EntityRepository} it belongs to.
     */
    private int index = -1;

    /**
     * Checks if this entity is active.
     *
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isActive() {
	return index != -1;
    }

    /**
     * Gets the index of this entity.
     *
     * @return The index of this entity.
     */
    public int getIndex() {
	synchronized (this) {
	    return index;
	}
    }

    /**
     * Sets the index of this entity.
     *
     * @param index The index of this entity.
     */
    public void setIndex(int index) {
	synchronized (this) {
	    this.index = index;
	}
    }

    /**
     * Resets the index of this entity, freeing it within its
     * {@link EntityRepository}.
     */
    public void resetIndex() {
	synchronized (this) {
	    index = -1;
	}
    }

    /**
     * Returns the type of this entity.
     */
    public abstract EntityType getType();

}