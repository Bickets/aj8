package org.apollo.game.msg.impl;

import org.apollo.game.model.Animation;
import org.apollo.game.msg.Message;

/**
 * Represents an animation performed by a model on some interface.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class InterfaceModelAnimationMessage implements Message {

    /**
     * The animation to perform.
     */
    private final Animation animation;

    /**
     * The interface to perform the animation on.
     */
    private final int interfaceId;

    /**
     * Constructs a new {@link InterfaceModelAnimationMessage}.
     *
     * @param animation The animation to perform.
     * @param interfaceId The interface to perform the animation on.
     */
    public InterfaceModelAnimationMessage(Animation animation, int interfaceId) {
	this.animation = animation;
	this.interfaceId = interfaceId;
    }

    /**
     * Returns the animation.
     */
    public Animation getAnimation() {
	return animation;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

}