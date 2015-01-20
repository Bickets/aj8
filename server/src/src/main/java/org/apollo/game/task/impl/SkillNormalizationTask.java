package org.apollo.game.task.impl;

import org.apollo.game.model.Player;
import org.apollo.game.task.Task;

/**
 * A {@link Task} which normalizes the skills of a player: gradually brings them
 * back to their normal value as specified by the experience.
 *
 * @author Graham
 */
public final class SkillNormalizationTask extends Task {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the skill normalization task.
	 *
	 * @param player The player.
	 */
	public SkillNormalizationTask(Player player) {
		super(100, false);
		this.player = player;
	}

	@Override
	public void execute() {
		if (!player.isActive()) {
			stop();
		} else {
			player.getSkillSet().normalize();
		}
	}

}