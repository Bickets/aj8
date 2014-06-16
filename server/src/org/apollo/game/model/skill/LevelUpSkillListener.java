package org.apollo.game.model.skill;

import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.def.LevelUpDefinition;
import org.apollo.game.msg.impl.OpenDialogueInterfaceMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;
import org.apollo.util.LanguageUtil;

/**
 * A {@link SkillListener} which notifies the player when they have leveled up a
 * skill.
 * 
 * @author Graham
 */
public final class LevelUpSkillListener extends SkillAdapter {

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the level up listener for the specified player.
     * 
     * @param player The player.
     */
    public LevelUpSkillListener(Player player) {
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
    }

}
