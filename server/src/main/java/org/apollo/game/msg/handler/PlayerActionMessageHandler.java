package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.PlayerActionEvent;
import org.apollo.game.model.GameCharacterRepository;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.PlayerActionMessage;

/**
 * Handles the {@link PlayerActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(PlayerActionMessage.class)
public final class PlayerActionMessageHandler implements MessageHandler<PlayerActionMessage> {

	@Override
	public void handle(Player player, PlayerActionMessage msg) {
		World world = player.getWorld();

		if (player.getInterfaceSet().isOpen()) {
			player.getInterfaceSet().close();
		}

		if (!player.getAttributes().isClientWindowFocused()) {
			return;
		}

		GameCharacterRepository<Player> repository = world.getPlayerRepository();

		int index = msg.getIndex();
		if (index < 1 || index >= repository.capacity()) {
			player.getWalkingQueue().clear();
			return;
		}

		Player other = repository.get(index);
		if (other == null || index != other.getIndex()) {
			player.getWalkingQueue().clear();
			return;
		}

		if (other.getInterfaceSet().isOpen()) {
			player.sendMessage("Other player is busy at the moment.");
			return;
		}

		if (!player.getPosition().isWithinDistance(other.getPosition(), player.getViewingDistance() + 1)) {
			player.getWalkingQueue().clear();
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, other.getPosition(), other.getSize()) {
			@Override
			public void executeAction() {
				player.turnTo(other.getPosition());
				player.post(new PlayerActionEvent(other, msg.getAction()));
				stop();
			}
		});
	}

}