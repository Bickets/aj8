package org.apollo.game.model.skill;

import java.util.stream.IntStream;

import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.inter.dialog.SkillLevelUpDialogueListener;
import org.apollo.game.msg.impl.UpdateSkillMessage;
import org.apollo.util.LanguageUtil;

/**
 * A {@link SkillListener} which synchronizes the state of a {@link SkillSet}
 * with a client.
 *
 * @author Graham
 */
public final class SynchronizationSkillListener implements SkillListener {

	/**
	 * The player who we are synchronization their skill set for.
	 */
	private final Player player;

	/**
	 * Creates the skill synchronization listener.
	 *
	 * @param player The player.
	 */
	public SynchronizationSkillListener(Player player) {
		this.player = player;
	}

	@Override
	public void leveledUp(SkillSet set, int id, Skill skill) {
		String name = Skill.getName(id);
		String article = LanguageUtil.getIndefiniteArticle(name);
		int level = skill.getMaximumLevel();

		player.getInterfaceSet().openDialogue(new SkillLevelUpDialogueListener(id, skill));
		player.sendMessage("You've just advanced " + article + " " + name + " level! You have reached level " + level + ".");

		if (level == SkillSet.MAXIMUM_LEVEL) {
			player.sendMessage("Well done! You've achieved the highest possible level in this skill.");
		}

		player.playGraphic(new Graphic(199, 0, 100));

		if (set.isCombatSkill(id)) {
			player.updateApprarance();
		}
	}

	@Override
	public void skillUpdated(SkillSet set, int id, Skill skill) {
		player.send(new UpdateSkillMessage(id, skill));
	}

	@Override
	public void skillsUpdated(SkillSet set) {
		IntStream.range(0, set.size()).forEach(id -> player.send(new UpdateSkillMessage(id, set.getSkill(id))));
	}

}