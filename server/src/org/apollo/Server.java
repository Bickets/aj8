
package org.apollo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.game.event.EventTranslator;
import org.apollo.game.model.World;
import org.apollo.net.ApolloHandler;
import org.apollo.net.HttpChannelHandler;
import org.apollo.net.JagGrabChannelHandler;
import org.apollo.net.NetworkConstants;
import org.apollo.net.ServiceChannelHandler;

/**
 * The core class of the Apollo server.
 * @author Graham
 */
public final class Server
{

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger( Server.class.getName() );


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
		} catch( ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e ) {
			logger.log( Level.SEVERE, "Exception whilst starting Apollo:", e );
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
	 * An instance of {@link EventLoopGroup} used for multithread selects from the NIO selector
	 * based {@link Channel}'s
	 */
	private final EventLoopGroup loopGroup = new NioEventLoopGroup();

	/**
	 * The service manager.
	 */
	private final ServiceManager serviceManager;

	/**
	 * The server's context.
	 */
	private ServerContext context;

	/**
	 * The event translator.
	 */
	private EventTranslator eventTranslator;

	/**
	 * The file system.
	 */
	private IndexedFileSystem fileSystem;


	/**
	 * Creates the Apollo server.
	 * @throws IOException If some I/O exceptions occurs.
	 * @throws ClassNotFoundException If the specified class is not found.
	 * @throws IllegalAccessException If we cannot access the specified class.
	 * @throws InstantiationException If some instantiation error occurs.
	 */
	public Server() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException
	{
		logger.info( "Starting Apollo..." );
		serviceManager = new ServiceManager();
	}


	/**
	 * Initializes the server.
	 * @throws FileNotFoundException If the file system cannot be found.
	 */
	public void init() throws FileNotFoundException
	{
		logger.info( "Initialized Apollo." );

		context = new ServerContext( serviceManager );
		eventTranslator = new EventTranslator();
		fileSystem = new IndexedFileSystem( new File( "data/fs/" ), true );
		ApolloHandler handler = new ApolloHandler( context, eventTranslator, fileSystem );

		ChannelHandler servicePipelineFactory = new ServiceChannelHandler( handler );
		serviceBootstrap.childHandler( servicePipelineFactory );
		serviceBootstrap.channel( NioServerSocketChannel.class );
		serviceBootstrap.group( loopGroup );

		ChannelHandler httpPipelineFactory = new HttpChannelHandler( handler );
		httpBootstrap.childHandler( httpPipelineFactory );
		httpBootstrap.channel( NioServerSocketChannel.class );
		httpBootstrap.group( loopGroup );

		ChannelHandler jagGrabPipelineFactory = new JagGrabChannelHandler( handler );
		jagGrabBootstrap.childHandler( jagGrabPipelineFactory );
		jagGrabBootstrap.channel( NioServerSocketChannel.class );
		jagGrabBootstrap.group( loopGroup );
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
		try {
			httpBootstrap.bind( httpAddress );
		} catch( Throwable t ) {
			logger.log( Level.WARNING, "Binding to HTTP failed: client will use JAGGRAB as a fallback (not recommended)!", t );
		}

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

		World.getWorld().init( fileSystem );
	}

}
