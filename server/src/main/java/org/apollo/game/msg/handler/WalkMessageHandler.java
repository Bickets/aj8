package org.apollo.game.msg.handler;

import java.util.stream.IntStream;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.WalkMessage;

/**
 * A handler for the {@link WalkMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham
 */
@HandlesMessage(WalkMessage.class)
public final class WalkMessageHandler implements MessageHandler<WalkMessage> {

	@Override
	public void handle(Player player, WalkMessage message) {
		WalkingQueue queue = player.getWalkingQueue();
		Position[] steps = message.getSteps();

		if (!player.getAttributes().isClientWindowFocused()) {
			return;
		}

		if (!queue.addFirstStep(steps[0])) {
			return;
		}

		queue.setRunningQueue(message.isRunning());

		IntStream.range(1, steps.length).forEach(index -> queue.addStep(steps[index]));

		player.stopAction();
		player.getInterfaceSet().close();
	}

}