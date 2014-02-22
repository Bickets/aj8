
package org.apollo.game.model;

import org.apollo.game.event.Event;
import org.apollo.game.model.def.MobDefinition;

/**
 * A {@link GameCharacter} that has AI and is not controlled by a {@link Player}.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Mob extends GameCharacter
{

	/**
	 * This mobs definition.
	 */
	private final MobDefinition definition;


	/**
	 * Constructs a new {@link Mob}
	 * @param id The id of the mob.
	 * @param position The position of the mob.
	 */
	public Mob( int id, Position position )
	{
		this( MobDefinition.forId( id ), position );
	}


	/**
	 * Constructs a new {@link Mob}.
	 * @param definition The mobs definitions.
	 * @param position The position of the mob.
	 */
	public Mob( MobDefinition definition, Position position )
	{
		super( position );
		this.definition = definition;
	}


	/**
	 * Returns the mobs definitions.
	 * @return The definition of this mob.
	 */
	public MobDefinition getDefinition()
	{
		return definition;
	}


	@Override
	public int getSize()
	{
		return definition.getSize();
	}


	@Override
	public boolean isMob()
	{
		return true;
	}


	@Override
	public void send( Event event )
	{
		throw new UnsupportedOperationException();
	}

}
