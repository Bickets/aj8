
package org.apollo.game.sync.block;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Inventory;

/**
 * The appearance {@link SynchronizationBlock}.
 * @author Graham
 */
public final class AppearanceBlock extends SynchronizationBlock
{

	// TODO head icons support

	/**
	 * The players name.
	 */
	private final long name;

	/**
	 * The players appearance.
	 */
	private final Appearance appearance;

	/**
	 * The players combat level.
	 */
	private final int combat;

	/**
	 * The players total skill level (or 0).
	 */
	private final int skill;

	/**
	 * The players equipment.
	 */
	private final Inventory equipment;


	/**
	 * Creates the appearance block.
	 * @param name The players name.
	 * @param appearance The appearance.
	 * @param combat The players combat.
	 * @param skill The players skill, or 0 if showing the combat level.
	 * @param equipment The players equipment.
	 */
	AppearanceBlock( long name, Appearance appearance, int combat, int skill, Inventory equipment )
	{
		this.name = name;
		this.appearance = appearance;
		this.combat = combat;
		this.skill = skill;
		this.equipment = equipment.clone();
	}


	/**
	 * Gets the players name.
	 * @return The players name.
	 */
	public long getName()
	{
		return name;
	}


	/**
	 * Gets the players appearance.
	 * @return The players appearance.
	 */
	public Appearance getAppearance()
	{
		return appearance;
	}


	/**
	 * Gets the players combat level.
	 * @return The players combat level.
	 */
	public int getCombatLevel()
	{
		return combat;
	}


	/**
	 * Gets the players skill level.
	 * @return The players skill level.
	 */
	public int getSkillLevel()
	{
		return skill;
	}


	/**
	 * Gets the players equipment.
	 * @return The players equipment.
	 */
	public Inventory getEquipment()
	{
		return equipment;
	}

}
