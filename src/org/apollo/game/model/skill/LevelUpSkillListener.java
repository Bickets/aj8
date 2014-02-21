
package org.apollo.game.model.skill;

import org.apollo.game.event.impl.OpenChatboxOverlayEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.util.LanguageUtil;

/**
 * A {@link SkillListener} which notifies the player when they have leveled up
 * a skill.
 * @author Graham
 */
public final class LevelUpSkillListener extends SkillAdapter
{

	/**
	 * The interface id's for a normal level up in a skill
	 */
	/* XXX: It's too bad these ids aren't in order by skill id or else this would be easier. */
	private static final int[] LEVEL_UP_IDS = {
		157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
		171, 172, 173, 174, 175, 176, 177
	};

	/**
	 * The player.
	 */
	private final Player player;


	/**
	 * Creates the level up listener for the specified player.
	 * @param player The player.
	 */
	public LevelUpSkillListener( Player player )
	{
		this.player = player;
	}


	@Override
	public void levelledUp( SkillSet set, int id, Skill skill )
	{
		String name = Skill.getName( id );
		String article = LanguageUtil.getIndefiniteArticle( name );
		int level = skill.getMaximumLevel();
		player.send( new OpenChatboxOverlayEvent( LEVEL_UP_IDS[ id ] ) );
		player.send( new SetInterfaceTextEvent( 4268, "Congratulations! You've just advanced " + article + " " + name + " level!" ) );
		player.send( new SetInterfaceTextEvent( 4269, "You have now reached level " + level + "!" ) );
		player.sendMessage( "You've just advanced " + article + " " + name + " level! You have reached level " + level + "." );
		if( level == 99 ) {
			player.sendMessage( "@dre@Well done! You've achieved the highest possible level in this skill." );
		}
	}

}
