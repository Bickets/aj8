
package org.apollo.io.player;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.game.model.Player;
import org.apollo.net.session.GameSession;

/**
 * A class which processes a single save request.
 * @author Graham
 */
public final class PlayerSaverWorker implements Runnable
{

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger( PlayerSaverWorker.class.getName() );

	/**
	 * The player saver.
	 */
	private final PlayerSaver saver;

	/**
	 * The game session.
	 */
	private final GameSession session;

	/**
	 * The player to save.
	 */
	private final Player player;


	/**
	 * Creates the player saver worker.
	 * @param saver The player saver.
	 * @param session The game session.
	 * @param player The player to save.
	 */
	public PlayerSaverWorker( PlayerSaver saver, GameSession session, Player player )
	{
		this.saver = saver;
		this.session = session;
		this.player = player;
	}


	@Override
	public void run()
	{
		try {
			saver.savePlayer( player );
		} catch( Exception e ) {
			logger.log( Level.SEVERE, "Unable to save players game.", e );
		} finally {
			session.handlePlayerSaverResponse();
		}
	}

}
