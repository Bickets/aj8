package org.apollo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apollo.fs.FileSystem;
import org.apollo.game.GameService;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.game.sync.ClientSynchronizer;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.io.player.bin.BinaryPlayerSerializer;
import org.apollo.net.ApolloHandler;
import org.apollo.net.HttpChannelHandler;
import org.apollo.net.JagGrabChannelHandler;
import org.apollo.net.ServiceChannelHandler;
import org.apollo.plugin.PluginService;
import org.apollo.service.Service;
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
	 * A {@link Map} of service classes to {@link Services}.
	 */
	private final Map<Class<? extends Service>, Service> services = new HashMap<>();

	/**
	 * The context of this server.
	 */
	private final ServerContext context = new ServerContext(this);

	/**
	 * This servers world.
	 */
	private final World world = new World();

	/**
	 * This servers player serializer.
	 */
	private final PlayerSerializer playerSerializer = new BinaryPlayerSerializer(world);

	/**
	 * This servers player serializer worker.
	 */
	private final PlayerSerializerWorker serializerWorker = new PlayerSerializerWorker(playerSerializer);

	/**
	 * This servers client synchronizer.
	 */
	private final ClientSynchronizer clientSynchronizer = new ClientSynchronizer(world);

	/**
	 * The {@link MessageTranslator} for translating messages to their handlers.
	 */
	private final MessageTranslator messageTranslator = new MessageTranslator();

	/**
	 * The {@link FileSystem} for building the servers file system.
	 */
	private final FileSystem fileSystem;

	/**
	 * Constructs a new {@link Server} with the specified {@link FileSystem}.
	 * 
	 * @param fileSystem This servers file system.
	 */
	protected Server(FileSystem fileSystem) {
		logger.info("Starting Apollo...");
		this.fileSystem = fileSystem;
	}

	/**
	 * Appends the specified {@link Service} to the service map and sets the
	 * services context.
	 * 
	 * @param service The service to append.
	 */
	public void appendService(Service service) {
		service.setContext(context);
		services.put(service.getClass(), service);
	}

	/**
	 * Checks if the specified service class exists.
	 * 
	 * @param clazz The services class.
	 * @return {@code true} if and only if the service exists otherwise
	 *         {@code false}.
	 */
	public <T extends Service> boolean serviceExists(Class<T> clazz) {
		return services.containsKey(clazz);
	}

	/**
	 * Returns the {@link Service} for the specified service class.
	 * 
	 * @param clazz The services class.
	 * @return The {@link Service} object for the specified service class.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Service> T getService(Class<T> clazz) {
		return (T) services.get(clazz);
	}

	/**
	 * Returns a collection of all of the appended {@link Service}s.
	 */
	public Collection<Service> services() {
		return services.values();
	}

	/**
	 * Initializes the server.
	 */
	public void init() {
		logger.info("Initialized Apollo.");

		ApolloHandler handler = new ApolloHandler(context);

		bootstrap(serviceBootstrap, new ServiceChannelHandler(handler));
		bootstrap(httpBootstrap, new HttpChannelHandler(handler));
		bootstrap(jagGrabBootstrap, new JagGrabChannelHandler(handler));

		appendService(new UpdateService());
		appendService(new PluginService());
		appendService(new GameService());
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
	 * @param servicePort The service address to bind to.
	 * @param httpPort The HTTP address to bind to.
	 * @param jagGrabPort The JAGGRAB address to bind to.
	 */
	public void bind(int servicePort, int httpPort, int jagGrabPort) {
		logger.info("Binding service listener to port: {}...", servicePort);
		serviceBootstrap.bind(servicePort);

		logger.info("Binding HTTP listener to port: {}...", httpPort);
		httpBootstrap.bind(httpPort);

		logger.info("Binding JAGGRAB listener to port: {}...", jagGrabPort);
		jagGrabBootstrap.bind(jagGrabPort);

		logger.info("Ready for connections.");
	}

	/**
	 * Starts the server.
	 *
	 * @throws IOException If some I/O error occurs.
	 */
	public void start() throws IOException {
		services().forEach(service -> service.init());
		services().forEach(service -> service.start());

		world.init(fileSystem);
	}

	/**
	 * Returns this servers world.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Returns this servers player serializer worker.
	 */
	public PlayerSerializerWorker getSerializerWorker() {
		return serializerWorker;
	}

	/**
	 * Returns this servers client synchronizer.
	 */
	public ClientSynchronizer getClientSynchronizer() {
		return clientSynchronizer;
	}

	/**
	 * Returns this servers message translator.
	 */
	public MessageTranslator getMessageTranslator() {
		return messageTranslator;
	}

	/**
	 * Returns this servers file system.
	 */
	public FileSystem getFileSystem() {
		return fileSystem;
	}

}