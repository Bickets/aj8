package org.apollo.game.sync.block;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * Represents the hit {@link SynchronizationBlock}. Both {@link Mob}'s and
 * {@link Player}'s can implement this block.
 *
 * @author Major
 */
public final class HitBlock extends SynchronizationBlock {

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
	 * The {{@link GameCharacter}s maximum health.
	 */
	private final int maximumHealth;

	/**
	 * Creates a new Hit Update block.
	 *
	 * @param damage The damage dealt by the hit.
	 * @param type The type of hit.
	 * @param currentHealth The current health of the {@link GameCharacter}.
	 * @param maximumHealth The maximum health of the {@link GameCharacter}.
	 */
	protected HitBlock(int damage, int type, int currentHealth, int maximumHealth) {
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