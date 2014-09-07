package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ObjectActionMessage;

/**
 * Handles an object action for the {@link ObjectActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(ObjectActionMessage.class)
public final class ObjectMessageHandler implements MessageHandler<ObjectActionMessage> {

	/**
	 * The world used to post object action events to this worlds event
	 * provider.
	 */
	private final World world;

	/**
	 * Constructs a new {@link ButtonEvnetHandler}.
	 *
	 * @param world The world.
	 */
	public ObjectMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, ObjectActionMessage message) {
		GameObject obj = new GameObject(message.getId(), message.getPosition());
		GameObjectDefinition def = obj.getDefinition();

		if (!obj.exists()) {
			return;
		}

		if (!def.isInteractable()) {
			return;
		}

		if (def.getId() != message.getId()) {
			return;
		}

		if (def.getActions()[message.getOption().getId()] == null) {
			return;
		}

		if (!player.getPosition().isWithinDistance(message.getPosition(), 32)) {
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, message.getPosition(), obj.getTileOffset(player.getPosition())) {
			@Override
			public void executeAction() {
				player.turnTo(obj.getTurnToPosition(player.getPosition()));
				world.post(new ObjectActionEvent(player, message.getId(), message.getOption(), message.getPosition()));
				stop();
			}
		});
	}

}