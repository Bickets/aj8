
package org.apollo.game.sync.block;

import org.apollo.game.event.impl.ChatEvent;
import org.apollo.game.model.Animation;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.sync.seg.SynchronizationSegment;

/**
 * A synchronization block is part of a {@link SynchronizationSegment}. A
 * segment can have up to one block of each type.
 * <p>
 * This class also has static factory methods for creating {@link SynchronizationBlock}s.
 * @author Graham
 */
public abstract class SynchronizationBlock
{

	/**
	 * Creates an animation block with the specified animation.
	 * @param animation The animation.
	 * @return The animation block.
	 */
	public static SynchronizationBlock createAnimationBlock( Animation animation )
	{
		return new AnimationBlock( animation );
	}


	/**
	 * Creates an appearance block for the specified player.
	 * @param player The player.
	 * @return The appearance block.
	 */
	public static SynchronizationBlock createAppearanceBlock( Player player )
	{
		return new AppearanceBlock( player.getEncodedName(), player.getAppearance(), player.getSkillSet().getCombatLevel(), 0, player.getEquipment() );
	}


	/**
	 * Creates a chat block for the specified player.
	 * @param player The player.
	 * @param chatEvent The chat event.
	 * @return The chat block.
	 */
	public static SynchronizationBlock createChatBlock( Player player, ChatEvent chatEvent )
	{
		return new ChatBlock( player.getPrivilegeLevel(), chatEvent );
	}


	/**
	 * Creates a force chat block.
	 * @param text The text to force.
	 * @return The force chat block.
	 */
	public static SynchronizationBlock createForceChatBlock( String text )
	{
		return new ForceChatBlock( text );
	}


	/**
	 * Creates a force movement block.
	 * @param currentPosition The position we are currently at.
	 * @param position The position to walk too.
	 * @param firstSpeed The speed of the X coordinate.
	 * @param secondSpeed The speed of the Y coordinate.
	 * @param direction The direction.
	 * @return The force movement block.
	 */
	public static SynchronizationBlock createForceMovementBlock( Position currentPosition, Position position, int firstSpeed, int secondSpeed, int direction )
	{
		return new ForceMovementBlock( currentPosition, position, firstSpeed, secondSpeed, direction );
	}


	/**
	 * Creates a graphic block with the specified graphic.
	 * @param graphic The graphic.
	 * @return The graphic block.
	 */
	public static SynchronizationBlock createGraphicBlock( Graphic graphic )
	{
		return new GraphicBlock( graphic );
	}


	/**
	 * Creates a hit update block with the specified damage.
	 * @param damage The damage.
	 * @return The hit update block.
	 */
	public static SynchronizationBlock createHitUpdateBlock( int damage, int type, int currentHealth, int maximumHealth )
	{
		return new HitBlock( damage, type, currentHealth, maximumHealth );
	}


	/**
	 * Creates a hit update block with the specified damage.
	 * @param event The damage.
	 * @return The hit update block.
	 */
	public static SynchronizationBlock createSecondHitUpdateBlock( int damage, int type, int currentHealth, int maximumHealth )
	{
		return new SecondHitBlock( damage, type, currentHealth, maximumHealth );
	}


	/**
	 * Creates a transform npc block.
	 * @param npc The npc to transform into.
	 * @return The transform npc block.
	 */
	public static SynchronizationBlock createTransformUpdateBlock( int npc )
	{
		return new TransformBlock( npc );
	}


	/**
	 * Creates a turn to entity block.
	 * @param id The entity id to turn to.
	 * @return The turn to entity block.
	 */
	public static SynchronizationBlock createTurnToEntityBlock( int id )
	{
		return new InteractingCharacterBlock( id );
	}


	/**
	 * Creates a turn to position block with the specified position.
	 * @param position The position.
	 * @return The turn to position block.
	 */
	public static SynchronizationBlock createTurnToPositionBlock( Position position )
	{
		return new TurnToPositionBlock( position );
	}

}
