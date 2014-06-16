package org.apollo.game.model;

import org.apollo.util.EntityRepository;

public abstract class Entity {

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

    public abstract EntityType getType();

}