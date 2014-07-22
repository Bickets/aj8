package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.obj.Objects;
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

	/* Ensure the object we want to interact with actually exists. */
	if (!Objects.objectExists(obj, message.getPosition())) {
	    return;
	}

	/* Ensure the object is interactable. */
	if (!def.isInteractable()) {
	    return;
	}

	/*
	 * Ensure the object we want to interact with is really what the client
	 * claims.
	 */
	if (def.getId() != message.getId()) {
	    return;
	}

	/* Check the actions id to the objects action and be sure it exists. */
	if (def.getActions()[message.getOption().getId()] == null) {
	    return;
	}

	/* We can't interact with the object if it is out of sight. */
	if (!player.getPosition().isWithinDistance(message.getPosition(), 32)) {
	    return;
	}

	/*
	 * Start an action which moves to the object before executing an action
	 * event.
	 */
	player.startAction(new DistancedAction<Player>(0, true, player, message.getPosition(), Objects.getTileOffset(player.getPosition(), obj)) {
	    @Override
	    public void executeAction() {
		player.turnTo(message.getPosition());
		world.post(new ObjectActionEvent(player, message.getId(), message.getOption(), message.getPosition()));
		stop();
	    }
	});
    }

}