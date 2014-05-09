package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.ButtonEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} which responds to {@link ButtonEvent}'s
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesEvent(ButtonEvent.class)
public final class ButtonEventHandler extends EventHandler<ButtonEvent> {

    /**
     * The world
     */
    private final World world;

    /**
     * Constructs a new {@link ButtonEvnetHandler}.
     * 
     * @param world The world.
     */
    public ButtonEventHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ButtonEvent event) {
	System.out.println(event.getInterfaceId());
	world.getInteractionHandler().dispatch(event.getInterfaceId(), player);
    }

}
