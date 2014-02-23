
package org.apollo.game.interact;

import org.apollo.game.model.Player;

public abstract class ButtonEventHandler
{

	private final int id;


	public ButtonEventHandler( int id )
	{
		this.id = id;
	}


	public final int getId()
	{
		return id;
	}


	public abstract void handle( Player player );

}
