
package org.apollo.game.interact;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public abstract class ObjectActionEventHandler
{

	private final int[] ids;


	public ObjectActionEventHandler( int... ids )
	{
		this.ids = ids;
	}


	public final int[] getIds()
	{
		return ids;
	}


	public abstract void handle( int id, int option, Player player, Position position );

}
