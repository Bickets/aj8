
package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.SkillSet;

public final class LevelCommandListener implements CommandListener
{

	@Override
	public void execute( Player player, Command command )
	{
		String[] args = command.getArguments();
		if( ! digit( args[ 0 ] ) || ! digit( args[ 1 ] ) ) {
			player.sendMessage( "Only numeric digit characters are allowed." );
			return;
		}

		int id = Integer.valueOf( args[ 0 ] );
		int level = Integer.valueOf( args[ 1 ] );

		player.getSkillSet().addExperience( id, SkillSet.getExperienceForLevel( level ) );
	}


	@Override
	public String getName()
	{
		return "level";
	}


	private boolean digit( String str )
	{
		return str.matches( "\\d+" );
	}

}
