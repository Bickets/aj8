package org.apollo.game.sync.block;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.HeadIcon;
import org.apollo.game.model.HeadIcon.Prayer;
import org.apollo.game.model.HeadIcon.Skull;
import org.apollo.game.model.Inventory;

/**
 * The appearance {@link SynchronizationBlock}.
 *
 * @author Graham
 */
public final class AppearanceBlock extends SynchronizationBlock {

	/**
	 * The players name.
	 */
	private final long name;

	/**
	 * The players skull icon.
	 */
	private final HeadIcon<Skull> skullIcon;

	/**
	 * The players prayer icon.
	 */
	private final HeadIcon<Prayer> prayerIcon;

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
	 *
	 * @param name The players name.
	 * @param skullIcon The players skull icon.
	 * @param prayerIcon The players prayer icon.
	 * @param appearance The appearance.
	 * @param combat The players combat.
	 * @param skill The players skill, or 0 if showing the combat level.
	 * @param equipment The players equipment.
	 */
	protected AppearanceBlock(long name, HeadIcon<Skull> skullIcon, HeadIcon<Prayer> prayerIcon, Appearance appearance, int combat, int skill, Inventory equipment) {
		this.name = name;
		this.skullIcon = skullIcon;
		this.prayerIcon = prayerIcon;
		this.appearance = appearance;
		this.combat = combat;
		this.skill = skill;
		this.equipment = equipment.clone();
	}

	/**
	 * Gets the players name.
	 *
	 * @return The players name.
	 */
	public long getName() {
		return name;
	}

	/**
	 * Gets the players skull icon.
	 *
	 * @return The players skull icon.
	 */
	public HeadIcon<Skull> getSkullIcon() {
		return skullIcon;
	}

	/**
	 * Gets the players prayer icon.
	 *
	 * @return The players prayer icon.
	 */
	public HeadIcon<Prayer> getPrayerIcon() {
		return prayerIcon;
	}

	/**
	 * Gets the players appearance.
	 *
	 * @return The players appearance.
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * Gets the players combat level.
	 *
	 * @return The players combat level.
	 */
	public int getCombatLevel() {
		return combat;
	}

	/**
	 * Gets the players skill level.
	 *
	 * @return The players skill level.
	 */
	public int getSkillLevel() {
		return skill;
	}

	/**
	 * Gets the players equipment.
	 *
	 * @return The players equipment.
	 */
	public Inventory getEquipment() {
		return equipment;
	}

}