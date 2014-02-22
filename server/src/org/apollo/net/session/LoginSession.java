
package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.apollo.ServerContext;
import org.apollo.fs.IndexedFileSystem;
import org.apollo.game.GameService;
import org.apollo.game.event.EventTranslator;
import org.apollo.game.model.Player;
import org.apollo.game.model.World.RegistrationStatus;
import org.apollo.io.player.PlayerLoaderResponse;
import org.apollo.login.LoginService;
import org.apollo.net.NetworkConstants;
import org.apollo.net.codec.game.GameEventDecoder;
import org.apollo.net.codec.game.GameEventEncoder;
import org.apollo.net.codec.game.GamePacketDecoder;
import org.apollo.net.codec.game.GamePacketEncoder;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.codec.login.LoginResponse;
import org.apollo.security.IsaacRandomPair;

/**
 * A login session.
 * @author Graham
 */
public final class LoginSession extends Session
{

	/**
	 * The server context.
	 */
	private final ServerContext serverContext;

	/**
	 * The event translator.
	 */
	private final EventTranslator eventTranslator;

	/**
	 * The file system.
	 */
	private final IndexedFileSystem fileSystem;


	/**
	 * Creates a login session for the specified channel.
	 * @param ctx The channels context.
	 * @param serverContext The server context.
	 * @param eventTranslator The event translator.
	 * @param fileSystem The file system
	 */
	public LoginSession( ChannelHandlerContext ctx, ServerContext serverContext, EventTranslator eventTranslator, IndexedFileSystem fileSystem )
	{
		super( ctx );
		this.serverContext = serverContext;
		this.eventTranslator = eventTranslator;
		this.fileSystem = fileSystem;
	}


	@Override
	public void messageReceived( Object message ) throws Exception
	{
		if( message.getClass() == LoginRequest.class ) {
			handleLoginRequest( ( LoginRequest )message );
		}
	}


	/**
	 * Handles a login request.
	 * @param request The login request.
	 * @throws IOException If some I/O error occurs.
	 */
	private void handleLoginRequest( LoginRequest request ) throws IOException
	{
		LoginService loginService = serverContext.getService( LoginService.class );
		loginService.submitLoadRequest( this, request, fileSystem );
	}


	/**
	 * Handles a response from the login service.
	 * @param request The request this response corresponds to.
	 * @param response The response.
	 */
	public void handlePlayerLoaderResponse( LoginRequest request, PlayerLoaderResponse response )
	{
		GameService gameService = serverContext.getService( GameService.class );

		int status = response.getStatus();
		Player player = response.getPlayer();
		int rights = player == null ? 0: player.getPrivilegeLevel().toInteger();
		boolean log = false;

		if( player != null ) {
			GameSession session = new GameSession( ctx(), serverContext, eventTranslator, player );
			player.setSession( session, request.isReconnecting() );

			RegistrationStatus registrationStatus = gameService.registerPlayer( player );

			if( registrationStatus != RegistrationStatus.OK ) {
				player = null;
				if( registrationStatus == RegistrationStatus.ALREADY_ONLINE ) {
					status = LoginConstants.STATUS_ACCOUNT_ONLINE;
				} else {
					status = LoginConstants.STATUS_SERVER_FULL;
				}
				rights = 0;
			}
		}

		Channel channel = ctx().channel();
		ChannelFuture future = channel.writeAndFlush( new LoginResponse( status, rights, log ) );

		if( player != null ) {
			IsaacRandomPair randomPair = request.getRandomPair();

			channel.pipeline().addFirst( "eventEncoder", new GameEventEncoder( eventTranslator ) );
			channel.pipeline().addBefore( "eventEncoder", "gameEncoder", new GamePacketEncoder( randomPair.getEncodingRandom() ) );

			channel.pipeline().addBefore( "handler", "gameDecoder", new GamePacketDecoder( randomPair.getDecodingRandom(), eventTranslator ) );
			channel.pipeline().addAfter( "gameDecoder", "eventDecoder", new GameEventDecoder( eventTranslator ) );

			channel.pipeline().remove( "loginDecoder" );
			channel.pipeline().remove( "loginEncoder" );

			ctx().attr( NetworkConstants.NETWORK_SESSION ).set( player.getSession() );
		} else {
			future.addListener( ChannelFutureListener.CLOSE );
		}
	}

}
