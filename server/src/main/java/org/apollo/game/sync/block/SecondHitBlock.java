package org.apollo.game.sync.block;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * The Second Hit Update {@link SynchronizationBlock}. This is believed to be
 * used for when multiple attacks happen at once (for example, the DDS-special
 * attack). This block can be implemented by both {@link Player}'s and
 * {@link Mob}'s.
 *
 * @author Major
 */
public final class SecondHitBlock extends SynchronizationBlock {

    /**
     * The amount of damage the hit will do.
     */
    private final int damage;

    /**
     * The type of hit (e.g. normal, poison).
     */
    private final int type;

    /**
     * The {@link GameCharacter}s current health.
     */
    private final int currentHealth;

    /**
     * The {@link GameCharacter}s maximum health.
     */
    private final int maximumHealth;

    /**
     * Creates a new Second Hit Update block.
     *
     * @param damage The damage dealt by the hit.
     * @param type The type of hit.
     * @param currentHealth The current health of the {@link GameCharacter}.
     * @param maximumHealth The maximum health of the {@link GameCharacter}.
     */
    protected SecondHitBlock(int damage, int type, int currentHealth, int maximumHealth) {
	this.damage = damage;
	this.type = type;
	this.currentHealth = currentHealth;
	this.maximumHealth = maximumHealth;
    }

    /**
     * Gets the damage done by the hit.
     *
     * @return The damage.
     */
    public int getDamage() {
	return damage;
    }

    /**
     * Gets the hit type.
     *
     * @return The type.
     */
    public int getType() {
	return type;
    }

    /**
     * Gets the current health of the {@link GameCharacter}.
     *
     * @return The current health;
     */
    public int getCurrentHealth() {
	return currentHealth;
    }

    /**
     * Gets the maximum health of the {@link GameCharacter}.
     *
     * @return The maximum health.
     */
    public int getMaximumHealth() {
	return maximumHealth;
    }

}