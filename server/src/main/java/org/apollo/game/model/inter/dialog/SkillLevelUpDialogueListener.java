package org.apollo.game.model.inter.dialog;

import org.apollo.game.model.Player;
import org.apollo.game.model.def.LevelUpDefinition;
import org.apollo.game.model.skill.Skill;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;
import org.apollo.util.LanguageUtil;

/**
 * A dialogue listener which display skill level-up dialogues.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class SkillLevelUpDialogueListener implements DialogueListener {

	/**
	 * The id of the skill that was leveled up.
	 */
	private final int id;

	/**
	 * The skill that was leveled up.
	 */
	private final Skill skill;

	/**
	 * Constructs a new {@link SkillLevelUpDialogueListener} with the specified
	 * id and skill that was leveled up.
	 * 
	 * @param id The id of the skill that was leveled up.
	 * @param skill The skill that was leveled up.
	 */
	public SkillLevelUpDialogueListener(int id, Skill skill) {
		this.id = id;
		this.skill = skill;
	}

	@Override
	public String[] getLines() {
		String name = Skill.getName(id);
		String article = LanguageUtil.getIndefiniteArticle(name);

		return new String[] {
				"Congratulations! You've just advanced " + article + " " + name + " level!",
				"You have now reached level " + skill.getMaximumLevel() + "!" };
	}

	@Override
	public int send(Player player) {
		LevelUpDefinition definition = LevelUpDefinition.fromId(id);

		player.send(new SetInterfaceTextMessage(definition.getFirstChildId(), getLines()[0]));
		player.send(new SetInterfaceTextMessage(definition.getSecondChildId(), getLines()[1]));

		return definition.getInterfaceId();
	}

	@Override
	public int getMaximumEntries() {
		return 2;
	}

}