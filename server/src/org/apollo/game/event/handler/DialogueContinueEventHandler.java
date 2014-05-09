package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.DialogueContinueEvent;
import org.apollo.game.model.InterfaceType;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link DialogueContinueEvent}.
 * 
 * @author Chris Fletcher
 */
@HandlesEvent(DialogueContinueEvent.class)
public final class DialogueContinueEventHandler extends EventHandler<DialogueContinueEvent> {

    @Override
    public void handle(Player player, DialogueContinueEvent event) {
	if (player.getInterfaceSet().contains(InterfaceType.DIALOGUE)) {
	    player.getInterfaceSet().continueRequested();
	}
    }

}