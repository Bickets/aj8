
package org.apollo.game.model.skill;

import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.util.LanguageUtil;

/**
 * A {@link SkillListener} which notifies the player when they have levelled up
 * a skill.
 * @author Graham
 */
public final class LevelUpSkillListener extends SkillAdapter
{

	/**
	 * The interface id's for a normal level up in a skill
	 */
	private static final int[] LEVEL_UP_IDS = {

	};

	/**
	 * The interface id's for when you get the maximum level in a skill
	 */
	private static final int[] MAXIMUM_LEVEL_IDS = {

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
		player.send( new OpenInterfaceEvent( level == 99 ? MAXIMUM_LEVEL_IDS[ id ]: LEVEL_UP_IDS[ id ] ) );
		player.sendMessage( "You've just advanced " + article + " " + name + " level! You have reached level " + level + "." );
	}

}
