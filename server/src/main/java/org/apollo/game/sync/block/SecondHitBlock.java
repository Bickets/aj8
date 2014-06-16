package org.apollo.game.sync.block;

import org.apollo.game.model.Player;

/**
 * The Second Hit Update {@link SynchronizationBlock}. This is believed to be
 * used for when multiple attacks happen at once (for example, the DDS-special
 * attack). This block can be implemented by both {@link Player}'s and
 * {@link Npc}'s.
 * 
 * @author Major
 */
public class SecondHitBlock extends SynchronizationBlock {

    /**
     * The amount of damage the hit will do.
     */
    private final int damage;

    /**
     * The type of hit (e.g. normal, poison).
     */
    private final int type;

    /**
     * The {@link org.apollo.game.model.Character}'s current health.
     */
    private final int currentHealth;

    /**
     * The {@link org.apollo.game.model.Character}'s maximum health.
     */
    private final int maximumHealth;

    /**
     * Creates a new Second Hit Update block.
     * 
     * @param hitDamage The damage dealt by the hit.
     * @param hitType The type of hit.
     * @param currentHealth The current health of the
     *            {@link org.apollo.game.model.Character}.
     * @param maximumHealth The maximum health of the
     *            {@link org.apollo.game.model.Character}.
     */
    public SecondHitBlock(int hitDamage, int hitType, int currentHealth, int maximumHealth) {
	this.damage = hitDamage;
	this.type = hitType;
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
     * Gets the current health of the {@link org.apollo.game.model.Character}.
     * 
     * @return The current health;
     */
    public int getCurrentHealth() {
	return currentHealth;
    }

    /**
     * Gets the maximum health of the {@link org.apollo.game.model.Character}.
     * 
     * @return The maximum health.
     */
    public int getMaximumHealth() {
	return maximumHealth;
    }

}
