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

	/**
	 * The world used to post player action events to this worlds event
	 * provider.
	 */
	private final World world;

	/**
	 * Constructs a new {@link PlayerActionMessageHandler}.
	 *
	 * @param world The world.
	 */
	public PlayerActionMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, PlayerActionMessage msg) {
		if (player.getInterfaceSet().isOpen()) {
			return;
		}

		GameCharacterRepository<Player> repository = world.getPlayerRepository();

		int index = msg.getIndex();
		if (index < 1 || index >= repository.capacity()) {
			return;
		}

		Player other = repository.get(index);
		if (other == null || index != other.getIndex()) {
			return;
		}

		if (other.getInterfaceSet().isOpen()) {
			player.sendMessage("Other player is busy at the moment.");
			return;
		}

		if (!player.getPosition().isWithinDistance(other.getPosition(), player.getViewingDistance() + 1)) {
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, other.getPosition(), other.getSize()) {
			@Override
			public void executeAction() {
				player.turnTo(other.getPosition());
				world.post(new PlayerActionEvent(player, other, msg.getOption()));
				stop();
			}
		});
	}

}