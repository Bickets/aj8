package org.apollo.game.msg.impl;

import org.apollo.game.model.GroundItem;
import org.apollo.game.msg.Message;

/**
 * Defines a message related to a {@link GroundItem} for either the removal or
 * addition.
 * 
 * @author Tyler Buchanan (https://www.github.com/TylerBuchanan97)
 */
public final class GroundItemMessage extends Message {

    private final GroundItem groundItem;
    
    public GroundItemMessage(GroundItem groundItem) {
	this.groundItem = groundItem;
    }
    
    public GroundItem getGroundItem() {
	return groundItem;
    }
    
}
