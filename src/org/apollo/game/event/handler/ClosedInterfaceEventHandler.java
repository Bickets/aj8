package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.ClosedInterfaceEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link ClosedInterfaceEvent}.
 * @author Graham
 */
public final class ClosedInterfaceEventHandler extends EventHandler<ClosedInterfaceEvent> {

    @Override
    public void handle(Player player, ClosedInterfaceEvent event) {
        player.getInterfaceSet().interfaceClosed();
    }

}
