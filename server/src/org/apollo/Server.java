
package org.apollo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.game.GameService;
import org.apollo.game.event.EventTranslator;
import org.apollo.game.model.World;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.net.ApolloHandler;
import org.apollo.net.HttpChannelHandler;
import org.apollo.net.JagGrabChannelHandler;
import org.apollo.net.NetworkConstants;
import org.apollo.net.ServiceChannelHandler;
import org.apollo.plugin.PluginService;
import org.apollo.service.ServiceManager;
import org.apollo.update.UpdateService;

/**
 * The core class of the Apollo server.
 * @author Graham
 */
final class Server
{

	/**
	 * The logger for this class.
	 */
	private final Logger logger = Logger.getLogger( getClass().getName() );


	/**
	 * The entry point of the Apollo server application.
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main( String[] args )
	{
		try {
			Server server = new Server();
			server.init();

			SocketAddress service = new InetSocketAddress( NetworkConstants.SERVICE_PORT );
			SocketAddress http = new InetSocketAddress( NetworkConstants.HTTP_PORT );
			SocketAddress jaggrab = new InetSocketAddress( NetworkConstants.JAGGRAB_PORT );

			server.start();
			server.bind( service, http, jaggrab );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * The {@link ServerBootstrap} for the service listener.
	 */
	private final ServerBootstrap serviceBootstrap = new ServerBootstrap();

	/**
	 * The {@link ServerBootstrap} for the HTTP listener.
	 */
	private final ServerBootstrap httpBootstrap = new ServerBootstrap();

	/**
	 * The {@link ServerBootstrap} for the JAGGRAB listener.
	 */
	private final ServerBootstrap jagGrabBootstrap = new ServerBootstrap();

	/**
	 * The service manager.
	 */
	private final ServiceManager serviceManager = new ServiceManager();

	/**
	 * The world.
	 */
	private final World world = new World();

	/**
	 * The player serializer.
	 */
	private final PlayerSerializer playerSerializer = new PlayerSerializer();

	/**
	 * The game service.
	 */
	private final GameService gameService = new GameService( world, playerSerializer );

	/**
	 * The plugin service.
	 */
	private final PluginService pluginService = new PluginService( world );

	/**
	 * The update service.
	 */
	private final UpdateService updateService = new UpdateService();

	/**
	 * The event translator.
	 */
	private final EventTranslator eventTranslator = new EventTranslator( world );

	/**
	 * The file system.
	 */
	private IndexedFileSystem fileSystem;


	/**
	 * Creates the Apollo server.
	 * @throws FileNotFoundException If the file system cannot be found.
	 */
	private Server() throws FileNotFoundException
	{
		logger.info( "Starting Apollo..." );
		fileSystem = new IndexedFileSystem( new File( "data/fs/" ), true );
	}


	/**
	 * Initializes the server.
	 */
	public void init()
	{
		logger.info( "Initialized Apollo." );

		ApolloHandler handler = new ApolloHandler( eventTranslator, fileSystem, playerSerializer, gameService, updateService );

		bootstrap( serviceBootstrap, new ServiceChannelHandler( handler ) );
		bootstrap( httpBootstrap, new HttpChannelHandler( handler ) );
		bootstrap( jagGrabBootstrap, new JagGrabChannelHandler( handler ) );

		serviceManager.register( gameService );
		serviceManager.register( updateService );
		serviceManager.register( pluginService );
	}


	/**
	 * "Bootstrap"'s a specified {@link ServerBootstrap} to a {@link ChannelHandler}.
	 * @param bootstrap The bootstrap.
	 * @param handler The channel handler.
	 */
	private void bootstrap( ServerBootstrap bootstrap, ChannelHandler handler )
	{
		bootstrap.childHandler( handler );
		bootstrap.channel( NioServerSocketChannel.class );
		bootstrap.group( new NioEventLoopGroup() );
	}


	/**
	 * Binds the server to the specified address.
	 * @param serviceAddress The service address to bind to.
	 * @param httpAddress The HTTP address to bind to.
	 * @param jagGrabAddress The JAGGRAB address to bind to.
	 */
	public void bind( SocketAddress serviceAddress, SocketAddress httpAddress, SocketAddress jagGrabAddress )
	{
		logger.info( "Binding service listener to address: " + serviceAddress + "..." );
		serviceBootstrap.bind( serviceAddress );

		logger.info( "Binding HTTP listener to address: " + httpAddress + "..." );
		httpBootstrap.bind( httpAddress );

		logger.info( "Binding JAGGRAB listener to address: " + jagGrabAddress + "..." );
		jagGrabBootstrap.bind( jagGrabAddress );

		logger.info( "Ready for connections." );
	}


	/**
	 * Starts the server.
	 * @throws IOException If some I/O error occurs.
	 */
	public void start() throws IOException
	{
		serviceManager.startAll();

		world.init( fileSystem );
	}

}
