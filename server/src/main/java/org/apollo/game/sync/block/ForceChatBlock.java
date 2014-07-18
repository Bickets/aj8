package org.apollo.game.sync.block;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * The Force Chat {@link SynchronizationBlock}. This is a block that can be
 * implemented in both {@link Player} Synchronization tasks and {@link Mob}
 * Synchronization tasks, and will cause the {@link Character} to shout the
 * specified text. It is not possible to add colour or effect (e.g. wave or
 * scroll) to this block.
 *
 * @author Major
 */
public class ForceChatBlock extends SynchronizationBlock {

    /**
     * The Force Chat text.
     */
    private final String message;

    /**
     * Creates a new Force Chat [@link SynchronizationBlock}.
     *
     * @param message The message the {@link Character} will say.
     */
    public ForceChatBlock(String message) {
	this.message = message;
    }

    /**
     * Gets the message being sent by this Force Chat block.
     *
     * @return The message.
     */
    public String getMessage() {
	return message;
    }

}
