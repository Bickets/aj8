package org.apollo.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.apollo.game.model.skill.SkillListener;
import org.apollo.util.MathUtil;

/**
 * Represents the set of the players skills.
 *
 * @author Graham
 */
public final class SkillSet {

	/**
	 * The number of skills.
	 */
	public static final int SKILL_COUNT = 21;

	/**
	 * The maximum level.
	 */
	public static final int MAXIMUM_LEVEL = 99;

	/**
	 * The maximum allowed experience.
	 */
	public static final double MAXIMUM_EXP = 200_000_000;

	/**
	 * A list of skill listeners.
	 */
	private final Set<SkillListener> listeners = new HashSet<>();

	/**
	 * A array of {@code Skill[]}s which represents each skill.
	 */
	private final Skill[] skills = new Skill[SKILL_COUNT];

	/**
	 * The cached experiences for every level.
	 */
	private static final int[] experiences = new int[MAXIMUM_LEVEL];

	/**
	 * A flag indicating if events are being fired.
	 */
	private boolean firingEvents = true;

	/**
	 * Creates the skill set.
	 */
	public SkillSet() {
		init();
	}

	/**
	 * Gets the number of skills.
	 *
	 * @return The number of skills.
	 */
	public int size() {
		return skills.length;
	}

	/**
	 * Initializes the skill set.
	 */
	private void init() {
		Arrays.setAll(skills, skill -> skill == Skill.HITPOINTS ? new Skill(1154, 10, 10) : new Skill(0, 1, 1));
	}

	/**
	 * Gets a skill by its id.
	 *
	 * @param id The id.
	 * @return The skill.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	public Skill getSkill(int id) {
		checkBounds(id);
		return skills[id];
	}

	/**
	 * Adds experience to the specified skill.
	 *
	 * @param id The skill id.
	 * @param experience The amount of experience.
	 */
	public void addExperience(int id, double experience) {
		checkBounds(id);

		Skill old = skills[id];

		double newExperience = Math.min(old.getExperience() + experience, MAXIMUM_EXP);

		int newCurrentLevel = old.getCurrentLevel();
		int newMaximumLevel = getLevelForExperience(newExperience);

		int delta = newMaximumLevel - old.getMaximumLevel();
		if (delta > 0) {
			newCurrentLevel += delta;
		}

		setSkill(id, new Skill(newExperience, newCurrentLevel, newMaximumLevel));

		if (delta > 0) {
			notifyLevelledUp(id);
		}
	}

	/**
	 * Checks whether or not the specified skill is a combat related skill or
	 * not.
	 *
	 * @param id The id of the skill to check.
	 * @return {@code true} if and only if the specified skill is a combat
	 *         related skill, otherwise {@code false}.
	 */
	public boolean isCombatSkill(int id) {
		return id < 7;
	}

	/**
	 * Gets the total level for this skill set.
	 *
	 * @return The total level.
	 */
	public int getTotalLevel() {
		return Stream.of(skills).mapToInt(skill -> skill.getMaximumLevel()).sum();
	}

	/**
	 * Gets the combat level for this skill set.
	 *
	 * @return The combat level.
	 */
	public int getCombatLevel() {
		int attack = skills[Skill.ATTACK].getMaximumLevel();
		int defence = skills[Skill.DEFENCE].getMaximumLevel();
		int strength = skills[Skill.STRENGTH].getMaximumLevel();
		int hitpoints = skills[Skill.HITPOINTS].getMaximumLevel();
		int prayer = skills[Skill.PRAYER].getMaximumLevel();
		int ranged = skills[Skill.RANGED].getMaximumLevel();
		int magic = skills[Skill.MAGIC].getMaximumLevel();

		int baseLevel = MathUtil.max(strength + attack, magic * 2, ranged * 2);
		int combatLevel = (int) (baseLevel * 1.3 + defence + hitpoints + prayer / 2) / 4;

		return combatLevel;
	}

	/**
	 * Gets the minimum experience required for the specified level.
	 *
	 * @param level The level.
	 * @return The minimum experience.
	 */
	public static double getExperienceForLevel(int level) {
		return experiences[--level > 98 ? 98 : level];
	}

	/**
	 * Gets the minimum level to get the specified experience.
	 *
	 * @param experience The experience.
	 * @return The minimum level.
	 */
	public static int getLevelForExperience(double experience) {
		for (int level = 98; level != -1; level--) {
			if (experiences[level] <= experience) {
				return level + 1;
			}
		}
		return 0;
	}

	/**
	 * Normalizes the skills in this set.
	 */
	public void normalize() {
		for (int id = 0; id < skills.length; id++) {
			int current = skills[id].getCurrentLevel();
			int max = skills[id].getMaximumLevel();

			if (current == max || id == Skill.PRAYER) {
				continue;
			}

			current += current < max ? 1 : -1;

			setSkill(id, new Skill(skills[id].getExperience(), current, max));
		}
	}

	/**
	 * Sets a skill.
	 *
	 * @param id The id.
	 * @param skill The skill.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	public void setSkill(int id, Skill skill) {
		checkBounds(id);
		skills[id] = skill;
		notifySkillUpdated(id);
	}

	/**
	 * Checks the bounds of the id.
	 *
	 * @param id The id.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	private void checkBounds(int id) {
		if (id < 0 || id >= skills.length) {
			throw new IndexOutOfBoundsException("Skill id: " + id + " is out of bounds");
		}
	}

	/**
	 * Notifies listeners that a skill has been levelled up.
	 *
	 * @param id The skill's id.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	private void notifyLevelledUp(int id) {
		checkBounds(id);
		if (firingEvents) {
			listeners.forEach(listener -> listener.leveledUp(this, id, skills[id]));
		}
	}

	/**
	 * Notifies listeners that a skill has been updated.
	 *
	 * @param id The skill's id.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	private void notifySkillUpdated(int id) {
		checkBounds(id);
		if (firingEvents) {
			listeners.forEach(listener -> listener.skillUpdated(this, id, skills[id]));
		}
	}

	/**
	 * Notifies listeners that the skills in this listener have been updated.
	 */
	private void notifySkillsUpdated() {
		if (firingEvents) {
			listeners.forEach(listener -> listener.skillsUpdated(this));
		}
	}

	/**
	 * Stops events from being fired.
	 */
	public void stopFiringEvents() {
		firingEvents = false;
	}

	/**
	 * Re-enables the firing of events.
	 */
	public void startFiringEvents() {
		firingEvents = true;
	}

	/**
	 * Forces this skill set to refresh.
	 */
	public void forceRefresh() {
		notifySkillsUpdated();
	}

	/**
	 * Adds a listener.
	 *
	 * @param listener The listener to add.
	 */
	public void addListener(SkillListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener.
	 *
	 * @param listener The listener to remove.
	 */
	public void removeListener(SkillListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Removes all the listeners.
	 */
	public void removeAllListeners() {
		listeners.clear();
	}

	/**
	 * Lazily initializes the {@link experiences} array and caches the values
	 * for use later in the {@link getLevelForExperience(double)} and {@link
	 * getExperienceForLevel(int)} methods. This is to speed up calculation of
	 * the specified operations which may be costly if calculated on-demand.
	 */
	static {
		int points = 0;
		for (int index = 0; index < experiences.length; index++) {
			int level = index + 1;
			points += Math.round(level + 300.0 * Math.pow(2.0, level / 7.0));
			experiences[index] = points / 4;
		}
	}

}