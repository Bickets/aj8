package org.apollo.game.model.skill;

import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.def.LevelUpDefinition;
import org.apollo.game.msg.impl.OpenDialogueInterfaceMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;
import org.apollo.game.msg.impl.UpdateSkillMessage;
import org.apollo.game.sync.block.SynchronizationBlock;
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
	LevelUpDefinition definition = LevelUpDefinition.fromId(id);
	player.send(new SetInterfaceTextMessage(definition.getFirstChildId(), "Congratulations! You've just advanced " + article + " " + name + " level!"));
	player.send(new SetInterfaceTextMessage(definition.getSecondChildId(), "You have now reached level " + level + "!"));
	player.send(new OpenDialogueInterfaceMessage(definition.getInterfaceId()));
	player.sendMessage("You've just advanced " + article + " " + name + " level! You have reached level " + level + ".");
	if (level == 99) {
	    player.sendMessage("Well done! You've achieved the highest possible level in this skill.");
	}
	player.playGraphic(new Graphic(199, 0, 100));

	// Only update appearance if we level up a combat skill
	if (id < 7) {
	    player.getBlockSet().add(SynchronizationBlock.createAppearanceBlock(player));
	}
    }

    @Override
    public void skillUpdated(SkillSet set, int id, Skill skill) {
	player.send(new UpdateSkillMessage(id, skill));
    }

    @Override
    public void skillsUpdated(SkillSet set) {
	for (int id = 0; id < set.size(); id++) {
	    player.send(new UpdateSkillMessage(id, set.getSkill(id)));
	}
    }

}