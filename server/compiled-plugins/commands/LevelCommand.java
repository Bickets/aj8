
package commands;

import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.SkillSet;

@SuppressWarnings( "all" )
public class LevelCommand extends PrivilegedCommandListener
{

	public LevelCommand()
	{
		super( Player.PrivilegeLevel.ADMINISTRATOR );
	}


	public void executePrivileged( final Player player, final Command command )
	{
		final String[] args = command.getArguments();
		boolean _and = false;
		String _get = args[ 0 ];
		boolean _isDigit = this.isDigit( _get );
		if( ! _isDigit ) {
			_and = false;
		} else {
			String _get_1 = args[ 1 ];
			boolean _isDigit_1 = this.isDigit( _get_1 );
			_and = _isDigit_1;
		}
		if( _and ) {
			String _get_2 = args[ 0 ];
			final Integer id = Integer.valueOf( _get_2 );
			String _get_3 = args[ 1 ];
			final Integer level = Integer.valueOf( _get_3 );
			SkillSet _skillSet = player.getSkillSet();
			int _size = _skillSet.size();
			boolean _lessEqualsThan = ( ( id ).intValue() <= _size );
			if( _lessEqualsThan ) {
				if( ( ( ( level ).intValue() > 0 ) && ( ( level ).intValue() < 99 ) ) ) {
					SkillSet _skillSet_1 = player.getSkillSet();
					double _experienceForLevel = SkillSet.getExperienceForLevel( ( level ).intValue() );
					_skillSet_1.addExperience( ( id ).intValue(), _experienceForLevel );
				}
			}
		}
	}


	public String getName()
	{
		return "level";
	}


	public boolean isDigit( final String string )
	{
		return string.matches( "\\d+" );
	}
}
