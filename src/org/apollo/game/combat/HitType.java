
package org.apollo.game.combat;

public enum HitType {

	MELEE( DamageType.MELEE_DAMAGE ),

	RANGED( DamageType.RANGED_DAMAGE ),

	MAGIC( DamageType.MAGIC_DAMAGE ),

	BLOCK( DamageType.BLOCKED_DAMAGE ),

	POISON( DamageType.POISON_DAMAGE ),

	DISEASE( DamageType.DISEASE_DAMAGE );

	private final DamageType damageType;


	private HitType( DamageType damageType )
	{
		this.damageType = damageType;
	}


	public final DamageType getDamageType()
	{
		return damageType;
	}

}
