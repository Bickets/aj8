
package org.apollo.game.combat;

public enum DamageType
{

	MELEE_DAMAGE( 0 ),

	MAGIC_DAMAGE( 0 ),

	RANGED_DAMAGE( 0 ),

	BLOCKED_DAMAGE( 1 ),

	POISON_DAMAGE( 2 ),

	DISEASE_DAMAGE( 3 );

	private final int index;


	private DamageType( int index )
	{
		this.index = index;
	}


	public final int getIndex()
	{
		return index;
	}

}
