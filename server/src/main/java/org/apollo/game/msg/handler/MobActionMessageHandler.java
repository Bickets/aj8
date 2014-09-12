package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.MobActionEvent;
import org.apollo.game.model.GameCharacterRepository;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.MobActionMessage;

/**
 * Handles the {@link MobActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(MobActionMessage.class)
public final class MobActionMessageHandler implements MessageHandler<MobActionMessage> {

	@Override
	public void handle(Player player, MobActionMessage msg) {
		World world = player.getWorld();

		if (player.getInterfaceSet().isOpen()) {
			return;
		}

		GameCharacterRepository<Mob> repository = world.getMobRepository();

		int index = msg.getIndex();
		if (index < 1 || index >= repository.capacity()) {
			return;
		}

		Mob mob = repository.get(index);
		if (mob == null || index != mob.getIndex()) {
			return;
		}

		if (!player.getPosition().isWithinDistance(mob.getPosition(), player.getViewingDistance() + 1)) {
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, mob.getPosition(), mob.getSize()) {
			@Override
			public void executeAction() {
				player.turnTo(mob.getPosition());
				world.post(new MobActionEvent(player, mob, msg.getOption()));
				stop();
			}
		});
	}

}