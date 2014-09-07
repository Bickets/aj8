package org.apollo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.SQLException;

import org.apollo.fs.FileSystem;
import org.apollo.game.GameService;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.io.player.bin.BinaryPlayerSerializer;
import org.apollo.net.ApolloHandler;
import org.apollo.net.HttpChannelHandler;
import org.apollo.net.JagGrabChannelHandler;
import org.apollo.net.NetworkConstants;
import org.apollo.net.ServiceChannelHandler;
import org.apollo.plugin.PluginService;
import org.apollo.service.ServiceManager;
import org.apollo.update.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The core class of the Apollo server.
 *
 * @author Graham
 */
final class Server {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(Server.class);

	/**
	 * The entry point of the Apollo server application.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.init();

			SocketAddress service = new InetSocketAddress(NetworkConstants.SERVICE_PORT);
			SocketAddress http = new InetSocketAddress(NetworkConstants.HTTP_PORT);
			SocketAddress jaggrab = new InetSocketAddress(NetworkConstants.JAGGRAB_PORT);

			server.start();
			server.bind(service, http, jaggrab);
		} catch (IOException | SQLException e) {
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
	 * The {@link ServiceManager} for initiating services.
	 */
	private final ServiceManager serviceManager = new ServiceManager();

	/**
	 * The {@link World} for the game service.
	 */
	private final World world = new World();

	/**
	 * The {@link PluginService} used to register all plugins.
	 */
	private final PluginService pluginService = new PluginService(world);

	/**
	 * The {@link UpdateService} for managing file requests.
	 */
	private final UpdateService updateService = new UpdateService();

	/**
	 * The {@link MessageTranslator} for translating messages to their handlers.
	 */
	private final MessageTranslator messageTranslator = new MessageTranslator(world);

	/**
	 * The {@link FileSystem} for building the servers file system.
	 */
	private final FileSystem fileSystem;

	/**
	 * The {@link PlayerSerializerWorker} for managing save/load requests.
	 */
	private final PlayerSerializerWorker serializerWorker;

	/**
	 * The {@link GameService} for managing the games pulse handler.
	 */
	private final GameService gameService;

	/**
	 * Creates the Apollo server.
	 *
	 * @throws IOException If some I/O error occurs.
	 * @throws SQLException If some database access error occurs.
	 */
	private Server() throws IOException, SQLException {
		logger.info("Starting Apollo...");

		fileSystem = FileSystem.create("data/fs");
		serializerWorker = new PlayerSerializerWorker(new BinaryPlayerSerializer());
		gameService = new GameService(world, serializerWorker);
	}

	/**
	 * Initializes the server.
	 */
	public void init() {
		logger.info("Initialized Apollo.");

		ApolloHandler handler = new ApolloHandler(messageTranslator, fileSystem, serializerWorker, gameService, updateService);

		bootstrap(serviceBootstrap, new ServiceChannelHandler(handler));
		bootstrap(httpBootstrap, new HttpChannelHandler(handler));
		bootstrap(jagGrabBootstrap, new JagGrabChannelHandler(handler));

		serviceManager.register(gameService);
		serviceManager.register(updateService);
		serviceManager.register(pluginService);
	}

	/**
	 * "Bootstrap"'s a specified {@link ServerBootstrap} to a
	 * {@link ChannelHandler}.
	 *
	 * @param bootstrap The bootstrap.
	 * @param handler The channel handler.
	 */
	private void bootstrap(ServerBootstrap bootstrap, ChannelHandler handler) {
		bootstrap.childHandler(handler);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.group(new NioEventLoopGroup());
	}

	/**
	 * Binds the server to the specified address.
	 *
	 * @param serviceAddress The service address to bind to.
	 * @param httpAddress The HTTP address to bind to.
	 * @param jagGrabAddress The JAGGRAB address to bind to.
	 */
	public void bind(SocketAddress serviceAddress, SocketAddress httpAddress, SocketAddress jagGrabAddress) {
		logger.info("Binding service listener to address: {}...", serviceAddress);
		serviceBootstrap.bind(serviceAddress);

		logger.info("Binding HTTP listener to address: {}...", httpAddress);
		httpBootstrap.bind(httpAddress);

		logger.info("Binding JAGGRAB listener to address: {}...", jagGrabAddress);
		jagGrabBootstrap.bind(jagGrabAddress);

		logger.info("Ready for connections.");
	}

	/**
	 * Starts the server.
	 *
	 * @throws IOException If some I/O error occurs.
	 */
	public void start() throws IOException {
		world.init(fileSystem);
		serviceManager.startAll();
	}

}