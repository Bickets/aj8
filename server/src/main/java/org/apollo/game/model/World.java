package org.apollo.game.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apollo.fs.FileSystem;
import org.apollo.fs.parser.GameObjectDefinitionParser;
import org.apollo.fs.parser.InterfaceDefinitionParser;
import org.apollo.fs.parser.ItemDefinitionParser;
import org.apollo.fs.parser.MobDefinitionParser;
import org.apollo.fs.parser.StaticObjectDefinitionParser;
import org.apollo.game.event.Event;
import org.apollo.game.event.EventProvider;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.UniversalEventProvider;
import org.apollo.game.model.def.EquipmentDefinition;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.def.GamePacketDefinition;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.def.LevelUpDefinition;
import org.apollo.game.model.def.MobDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.pf.AStarPathFinder;
import org.apollo.game.model.pf.PathFinder;
import org.apollo.game.model.pf.TraversalMap;
import org.apollo.game.model.region.RegionRepository;
import org.apollo.io.EquipmentDefinitionParser;
import org.apollo.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The world class is a singleton which contains objects like the
 * {@link GameCharacterRepository} for players and mobs. It should only contain
 * things relevant to the in-game world and not classes which deal with I/O and
 * such (these may be better off inside some custom {@link Service} or other
 * code, however, the circumstances are rare).
 *
 * @author Graham
 */
public final class World {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(World.class);

	/**
	 * Represents the different status codes for registering a player.
	 *
	 * @author Graham
	 */
	public enum RegistrationStatus {

		/**
		 * Indicates the world is full.
		 */
		WORLD_FULL,

		/**
		 * Indicates that the player is already online.
		 */
		ALREADY_ONLINE,

		/**
		 * Indicates that the player was registered successfully.
		 */
		OK
	}

	/**
	 * The {@link GameCharacterRepository} of {@link Mob}s.
	 */
	private final GameCharacterRepository<Mob> mobRepository = new GameCharacterRepository<>(WorldConstants.MAXIMUM_MOBS);

	/**
	 * The {@link GameCharacterRepository} of {@link Player}s.
	 */
	private final GameCharacterRepository<Player> playerRepository = new GameCharacterRepository<>(WorldConstants.MAXIMUM_PLAYERS);

	/**
	 * This world's {@link RegionRepository}.
	 */
	private final RegionRepository regionRepository = new RegionRepository();

	/**
	 * This world's {@link TraversalMap}.
	 */
	private final TraversalMap traversalMap = new TraversalMap(this);

	/**
	 * This world's {@link PathFinder}.
	 */
	private final PathFinder pathFinder = new AStarPathFinder(traversalMap);

	/**
	 * This worlds event provider.
	 */
	private final EventProvider eventProvider = new UniversalEventProvider();

	/**
	 * Initializes the world by loading definitions from the specified file
	 * system.
	 *
	 * @param fileSystem The file system.
	 * @throws IOException if an I/O error occurs.
	 */
	public void init(FileSystem fileSystem) throws IOException {
		// TODO: Create a cache system to load this neatly!!
		logger.info("Loading item definitions...");
		ItemDefinition[] itemDefs = ItemDefinitionParser.parse(fileSystem);
		ItemDefinition.init(itemDefs);
		logger.info("Done (loaded {} item definitions).", itemDefs.length);

		logger.info("Loading equipment definitions...");
		int nonNull = 0;
		try (InputStream is = Files.newInputStream(Paths.get("data", "equipment.dat"))) {
			EquipmentDefinition[] equipDefs = EquipmentDefinitionParser.parse(is);
			for (EquipmentDefinition def : equipDefs) {
				if (def != null) {
					nonNull++;
				}
			}
			EquipmentDefinition.init(equipDefs);
		}
		logger.info("Done (loaded {} equipment definitions).", nonNull);

		logger.info("Loading mob definitions...");
		MobDefinition[] mobDefs = MobDefinitionParser.parse(fileSystem);
		MobDefinition.init(mobDefs);
		logger.info("Done (loaded {} mob definitions).", mobDefs.length);

		logger.info("Loading game object definitions...");
		GameObjectDefinition[] gameObjDefs = GameObjectDefinitionParser.parse(fileSystem);
		GameObjectDefinition.init(gameObjDefs);
		logger.info("Done (loaded {} game object definitions).", gameObjDefs.length);

		logger.info("Loading static object definitions...");
		StaticObjectDefinitionParser parser = new StaticObjectDefinitionParser(this);
		List<GameObject> gameObjs = parser.parse(fileSystem);
		for (GameObject obj : gameObjs) {
			regionRepository.getRegion(obj.getPosition()).addEntity(obj);
		}
		logger.info("Done (loaded {} static object definitions).", gameObjs.size());

		logger.info("Loading interface definitions...");
		InterfaceDefinition[] interfaceDefs = InterfaceDefinitionParser.parse(fileSystem);
		InterfaceDefinition.init(interfaceDefs);
		logger.info("Done (loaded {} interface definitions).", interfaceDefs.length);

		logger.info("Loading skill level up definitions...");
		LevelUpDefinition.init();

		logger.info("Loading game packet definitions...");
		GamePacketDefinition.init();
	}

	/**
	 * Attempts to register some character to a specified character repository.
	 *
	 * @param character The character to register.
	 * @param repo The character repository to register the character to.
	 * @return A flag denoting whether or not the character was successfully
	 *         added to the repository.
	 */
	private <T extends GameCharacter> boolean register(T character, GameCharacterRepository<T> repo) {
		boolean success = repo.add(character);

		if (!success) {
			logger.error("Failed to register character (server full): {} [online={}]", character, repo.size());
		}

		return success;
	}

	/**
	 * Attempts to unregister some character from a specified character
	 * repository.
	 *
	 * @param character The character to unregister.
	 * @param repo The character repository to unregister the character to.
	 * @return A flag denoting whether or not the character was successfully
	 *         unregistered to the repository.
	 */
	private <T extends GameCharacter> boolean unregister(T character, GameCharacterRepository<T> repo) {
		boolean success = repo.remove(character);

		if (!success) {
			logger.error("Could not find character to unregister: {}", character);
		}

		return success;
	}

	/**
	 * Registers the specified player.
	 *
	 * @param player The player.
	 * @return A {@link RegistrationStatus}.
	 */
	public RegistrationStatus register(Player player) {
		if (isPlayerOnline(player.getEncodedName())) {
			return RegistrationStatus.ALREADY_ONLINE;
		}

		boolean success = register(player, playerRepository);

		return success ? RegistrationStatus.OK : RegistrationStatus.WORLD_FULL;
	}

	/**
	 * Registers the specified mob.
	 *
	 * @param mob The mob.
	 * @return {@code true} if the mob registered successfully, otherwise
	 *         {@code false}.
	 */
	public boolean register(Mob mob) {
		return register(mob, mobRepository);
	}

	/**
	 * Unregisters the specified player.
	 *
	 * @param player The player.
	 */
	public void unregister(Player player) {
		unregister(player, playerRepository);
	}

	/**
	 * Unregisters the specified {@link Mob}.
	 *
	 * @param mob The mob.
	 */
	public void unregister(Mob mob) {
		unregister(mob, mobRepository);
	}

	/**
	 * Posts an event to this worlds event provider.
	 *
	 * @param event The event to post.
	 */
	public <E extends Event> void post(E event) {
		eventProvider.post(event);
	}

	/**
	 * Provides an event subscriber to this worlds event provider.
	 *
	 * @param subscriber The event subscriber.
	 */
	public <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber) {
		eventProvider.provideSubscriber(subscriber);
	}

	/**
	 * Deprives an event subscriber to this worlds event provider.
	 *
	 * @param subscriber The event subscriber.
	 */
	public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
		eventProvider.depriveSubscriber(subscriber);
	}

	/**
	 * Checks if the specified player is online.
	 *
	 * @param name The players name, as a long
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isPlayerOnline(long name) {
		for (Player p : playerRepository) {
			if (p.getEncodedName() == name) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns this worlds player repository.
	 */
	public GameCharacterRepository<Player> getPlayerRepository() {
		return playerRepository;
	}

	/**
	 * Returns this worlds mob repository.
	 */
	public GameCharacterRepository<Mob> getMobRepository() {
		return mobRepository;
	}

	/**
	 * Returns this world's {@link RegionRepository}.
	 */
	public RegionRepository getRegionRepository() {
		return regionRepository;
	}

	/**
	 * Returns this world's {@link TraversalMap}.
	 */
	public TraversalMap getTraversalMap() {
		return traversalMap;
	}

	/**
	 * Returns this world's {@link PathFinder}.
	 */
	public PathFinder getPathFinder() {
		return pathFinder;
	}

}