package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.WalkMessage;

/**
 * A handler for the {@link WalkMessage}.
 *
 * @author Graham
 */
@HandlesMessage(WalkMessage.class)
public final class WalkMessageHandler extends MessageHandler<WalkMessage> {

    @Override
    public void handle(Player player, WalkMessage message) {
	WalkingQueue queue = player.getWalkingQueue();

	Position[] steps = message.getSteps();
	for (int index = 0; index < steps.length; index++) {
	    Position step = steps[index];
	    if (index == 0) {
		if (!queue.addFirstStep(step)) {
		    return; /* ignore packet */
		}
	    } else {
		queue.addStep(step);
	    }
	}

	queue.setRunningQueue(message.isRunning());

	if (queue.size() > 0) {
	    player.stopAction();
	}

	player.getInterfaceSet().close();
    }

}
