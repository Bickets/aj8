package org.apollo.game.msg.impl;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.model.HintIconType;
import org.apollo.game.model.Entity.EntityType;
import org.apollo.game.msg.Message;

/**
 * A hint icon displayed over a player or NPC.
 *
 * @author James Monger
 */
public final class CharacterHintIconMessage extends Message {

    /**
     * The target.
     */
    private GameCharacter target;
    
    /**
     * The type.
     */
    private HintIconType type;
    
    /**
     * Creates the {@link CharacterHintIconMessage}.
     *
     * @param target The target.
     */
    public CharacterHintIconMessage(GameCharacter target)
    {
	this.target = target;
	
	if (target.type() == EntityType.MOB)
	    this.type = HintIconType.MOB;
	
	if (target.type() == EntityType.PLAYER)
	    this.type = HintIconType.PLAYER;
    }
    
    /**
     * Gets the target.
     *
     * @return The target.
     */
    public GameCharacter getTarget()
    {
	return target;
    }
    
    /**
     * Gets the type.
     * 
     * @return The type.
     */
    public HintIconType getType()
    {
	return type;
    }
}