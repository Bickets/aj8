
package org.apollo.game.sync;

import org.apollo.game.GameService;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.sync.task.MobSynchronizationTask;
import org.apollo.game.sync.task.PlayerSynchronizationTask;
import org.apollo.game.sync.task.PostMobSynchronizationTask;
import org.apollo.game.sync.task.PostPlayerSynchronizationTask;
import org.apollo.game.sync.task.PreMobSynchronizationTask;
import org.apollo.game.sync.task.PrePlayerSynchronizationTask;
import org.apollo.game.sync.task.SynchronizationTask;
import org.apollo.util.CharacterRepository;

/**
 * An implementation of {@link ClientSynchronizer} which runs in a single
 * thread (the {@link GameService} thread from which this is called). Each
 * client is processed sequentially. Therefore this class will work well on
 * machines with a single core/processor. The {@link ParallelClientSynchronizer} will work better on
 * machines with multiple cores/processors, however, both
 * classes will work.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class SequentialClientSynchronizer extends ClientSynchronizer
{

	@Override
	public void synchronize()
	{
		CharacterRepository<Player> players = World.getWorld().getPlayerRepository();
		CharacterRepository<Mob> mobs = World.getWorld().getMobRepository();

		for( Player player: players ) {
			SynchronizationTask task = new PrePlayerSynchronizationTask( player );
			task.run();
		}

		for( Mob mob: mobs ) {
			SynchronizationTask task = new PreMobSynchronizationTask( mob );
			task.run();
		}

		for( Player player: players ) {
			SynchronizationTask task = new PlayerSynchronizationTask( player );
			task.run();
		}

		for( Player player: players ) {
			SynchronizationTask task = new MobSynchronizationTask( player );
			task.run();
		}

		for( Player player: players ) {
			SynchronizationTask task = new PostPlayerSynchronizationTask( player );
			task.run();
		}

		for( Mob mob: mobs ) {
			SynchronizationTask task = new PostMobSynchronizationTask( mob );
			task.run();
		}
	}

}
