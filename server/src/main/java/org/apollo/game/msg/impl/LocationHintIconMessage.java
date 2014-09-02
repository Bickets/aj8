package org.apollo.game.msg.impl;

import org.apollo.game.model.HintIconType;
import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;

/**
 * A hint icon displayed over a tile.
 *
 * @author James Monger
 */
public final class LocationHintIconMessage extends Message {

    /**
     * The type.
     */
    private HintIconType type;
    
    /**
     * The target.
     */
    private Position target;
    
    /**
     * The draw height.
     */
    private int drawHeight;
    
    /**
     * Creates the {@link LocationHintIconMessage}.
     *
     * @param type The type.
     * @param target The position to display the icon over.
     * @param drawHeight The icon draw height.
     */
    public LocationHintIconMessage(HintIconType type, Position target, int drawHeight)
    {
	this.type = type;
	this.target = target;
	this.drawHeight = drawHeight;
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
    
    /**
     * Gets the target.
     *
     * @return The target.
     */
    public Position getTarget()
    {
	return target;
    }
    
    /**
     * Gets the draw height.
     *
     * @return The draw height.
     */
    public int getDrawHeight()
    {
	return drawHeight;
    }
}
