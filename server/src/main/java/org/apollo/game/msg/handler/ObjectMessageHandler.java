package org.apollo.game.msg.handler;

import java.util.Set;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.Entity.EntityCategory;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.region.Region;
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

	@Override
	public void handle(Player player, ObjectActionMessage message) {
		World world = player.getWorld();

		if (player.getInterfaceSet().isOpen()) {
			return;
		}

		GameObject object = new GameObject(message.getId(), message.getPosition(), world);
		GameObjectDefinition definition = object.getDefinition();

		Position position = message.getPosition();
		Region region = world.getRegionRepository().getRegion(position);
		Set<GameObject> objects = region.getEntities(position, EntityCategory.GAME_OBJECT);

		if (!containsObject(message.getId(), objects)) {
			return;
		}

		if (!definition.isInteractable()) {
			return;
		}

		if (definition.getActions()[message.getOption().getId()] == null) {
			return;
		}

		if (!player.getPosition().isWithinDistance(position, player.getViewingDistance() + 1)) {
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, position, object.getTileOffset(player.getPosition())) {
			@Override
			public void executeAction() {
				player.turnTo(object.getTurnToPosition(player.getPosition()));
				world.post(new ObjectActionEvent(player, message.getId(), message.getOption(), position));
				stop();
			}
		});
	}

	/**
	 * Indicates whether or not the {@link Set} of {@link GameObject}s contains
	 * the object with the specified id.
	 *
	 * @param id The id of the object.
	 * @param objects The set of objects.
	 * @return {@code true} if the set does contain the object with the
	 *         specified id, otherwise {@code false}.
	 */
	private boolean containsObject(int id, Set<GameObject> objects) {
		return objects.stream().filter(o -> o.getId() == id).count() > 0;
	}

}