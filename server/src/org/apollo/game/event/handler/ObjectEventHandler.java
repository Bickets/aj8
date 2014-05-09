package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.ObjectActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * Handles an object action for the {@link ObjectActionEvent}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesEvent(ObjectActionEvent.class)
public final class ObjectEventHandler extends EventHandler<ObjectActionEvent> {

    /**
     * The world
     */
    private final World world;

    /**
     * Constructs a new {@link ButtonEvnetHandler}.
     * 
     * @param world The world.
     */
    public ObjectEventHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ObjectActionEvent event) {
	world.getInteractionHandler().dispatch(event.getId(), event.getOption(), player, event.getPosition());
    }

}
