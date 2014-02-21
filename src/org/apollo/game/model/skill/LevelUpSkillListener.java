
package org.apollo.game.model.skill;

import static java.util.Arrays.asList;

import java.util.List;

import org.apollo.game.event.impl.OpenDialogueInterfaceEvent;
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
	private static final int[] LEVEL_UP_IDS = {
		6247, 6253, 6206, 6216, 4443, 6242, 6211, 6226, 4272, 6231, 6258, 4282,
		6263, 6221, 4416, 6237, 4277, 4261, 12122, 4887, 4267,
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
		int interfaceId = LEVEL_UP_IDS[ id ];
		List<Integer> children = getChildren( interfaceId );
		player.send( new SetInterfaceTextEvent( children.get( 0 ), "Congratulations! You've just advanced " + article + " " + name + " level!" ) );
		player.send( new SetInterfaceTextEvent( children.get( 1 ), "You have now reached level " + level + "!" ) );
		player.send( new OpenDialogueInterfaceEvent( interfaceId ) );
		player.sendMessage( "You've just advanced " + article + " " + name + " level! You have reached level " + level + "." );
		if( level == 99 ) {
			player.sendMessage( "Well done! You've achieved the highest possible level in this skill." );
		}
	}


	/**
	 * Returns the children of the specified interface id as a {@link List}.
	 * @param id The interface id.
	 *            TODO: This is a REALLY shitty method of doing this, but I really can't be assed
	 *            right now to do this a better way. I'm really frustrated atm.
	 */
	private List<Integer> getChildren( int id )
	{
		switch( id ) {
			case 4443:
				return asList( 5453, 6114 );
			case 4416:
				return asList( 4417, 4438 );
			case 4261:
				return asList( 4263, 4264 );
			case 4887:
				return asList( 4890, 4891 );
		}
		return asList( id + 1, id + 2 );
	}

}
