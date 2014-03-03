
package org.apollo.io.player;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.game.GameConstants;
import org.apollo.game.model.Player;
import org.apollo.io.player.impl.BinaryPlayerLoader;
import org.apollo.io.player.impl.BinaryPlayerSaver;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.session.GameSession;
import org.apollo.net.session.LoginSession;
import org.apollo.util.NamedThreadFactory;

/**
 * Handles the serialization of {@link Player}'s, submits save and load requests and verifies
 * loads.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerSerializer
{

	/**
	 * The default player loader.
	 */
	private static final PlayerLoader DEFAULT_LOADER = new BinaryPlayerLoader();

	/**
	 * The default player saver.
	 */
	private static final PlayerSaver DEFAULT_SAVER = new BinaryPlayerSaver();

	/**
	 * The player loader.
	 */
	private final PlayerLoader loader;

	/**
	 * The player saver.
	 */
	private final PlayerSaver saver;


	/**
	 * Constructs a new {@link PlayerSerializer} with the default serializers.
	 * @see {@link #DEFAULT_LOADER}.
	 * @see {@link #DEFAULT_SAVER}.
	 */
	public PlayerSerializer()
	{
		this( DEFAULT_LOADER, DEFAULT_SAVER );
	}


	/**
	 * Constructs a new {@link PlayerSerializer} with specified serializers.
	 * @param loader The player loader.
	 * @param saver The player saver.
	 */
	public PlayerSerializer( PlayerLoader loader, PlayerSaver saver )
	{
		this.loader = loader;
		this.saver = saver;
	}

	/**
	 * The {@link ExecutorService} to which workers are submitted.
	 */
	private final ExecutorService executor = Executors.newCachedThreadPool( new NamedThreadFactory( "LoginService" ) );


	/**
	 * Submits a login request.
	 * @param session The session submitting this request.
	 * @param request The login request.
	 * @param fileSystem The file system
	 * @throws IOException If some I/O exception occurs.
	 */
	public void submitLoadRequest( LoginSession session, LoginRequest request, IndexedFileSystem fileSystem ) throws IOException
	{
		if( requiresUpdate( request, fileSystem ) ) {
			session.handlePlayerLoaderResponse( request, new PlayerLoaderResponse( LoginConstants.STATUS_GAME_UPDATED ) );
		} else {
			executor.submit( new PlayerLoaderWorker( loader, session, request ) );
		}
	}


	/**
	 * Checks if an update is required whenever a {@link Player} submits a login request.
	 * @param request The login request.
	 * @param fileSystem The file system.
	 * @return {@code true} if an update is required, otherwise return {@code false}.
	 * @throws IOException If some I/O exception occurs.
	 */
	private boolean requiresUpdate( LoginRequest request, IndexedFileSystem fileSystem ) throws IOException
	{
		if( GameConstants.VERSION != request.getCurrentVersion() ) {
			return true;
		}
		ByteBuffer buffer = fileSystem.getCrcTable();
		int[] crcs = request.getArchiveCrcs();
		if( buffer.remaining() < crcs.length ) {
			return true;
		}
		for( int crc: crcs ) {
			if( crc != buffer.getInt() ) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Submits a save request.
	 * @param session The session submitting this request.
	 * @param player The player to save.
	 */
	public void submitSaveRequest( GameSession session, Player player )
	{
		executor.submit( new PlayerSaverWorker( saver, session, player ) );
	}

}
