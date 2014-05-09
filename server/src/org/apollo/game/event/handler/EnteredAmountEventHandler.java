package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.EnteredAmountEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link EnteredAmountEvent}.
 * 
 * @author Graham
 */
@HandlesEvent(EnteredAmountEvent.class)
public final class EnteredAmountEventHandler extends EventHandler<EnteredAmountEvent> {

    @Override
    public void handle(Player player, EnteredAmountEvent event) {
	player.getInterfaceSet().enteredAmount(event.getAmount());
    }

}
