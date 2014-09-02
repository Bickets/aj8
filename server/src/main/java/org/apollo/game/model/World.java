package org.apollo.game.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import org.apollo.game.model.def.StaticObjectDefinition;
import org.apollo.game.model.obj.GameObject;
import org.apollo.io.EquipmentDefinitionParser;
import org.apollo.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;

/**
 * The world class is a singleton which contains objects like the
 * {@link CharacterRepository} for players and mobs. It should only contain
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
     * The {@link EntityRepository} of {@link Mob}s.
     */
    private final EntityRepository<Mob> mobRepository = new EntityRepository<>(WorldConstants.MAXIMUM_MOBS);

    /**
     * The {@link EntityRepository} of {@link Player}s.
     */
    private final EntityRepository<Player> playerRepository = new EntityRepository<>(WorldConstants.MAXIMUM_PLAYERS);

    /**
     * The {@link EntityRepository} of {@link GameObject}s.
     */
    private final EntityRepository<GameObject> objectRepository = new EntityRepository<>(WorldConstants.MAXIMUM_GAME_OBJECTS);

    /**
     * The {@link EntityRepository} of {@link GroundItem}s.
     */
    private final EntityRepository<GroundItem> groundItemRepository = new EntityRepository<>(WorldConstants.MAXIMUM_GROUND_ITEMS);

    /**
     * This worlds event provider.
     */
    private final EventProvider eventProvider = new UniversalEventProvider();

    /**
     * Creates the world.
     */
    public World() {

    }

    /**
     * Initializes the world by loading definitions from the specified file
     * system.
     *
     * @param fileSystem The file system.
     * @throws IOException if an I/O error occurs.
     */
    public void init(FileSystem fileSystem) throws IOException {
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
	Multimap<Integer, StaticObjectDefinition> objDefs = StaticObjectDefinitionParser.parse(fileSystem);
	StaticObjectDefinition.init(objDefs);
	logger.info("Done (loaded {} static object definitions).", objDefs.size());

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
     * Attempts to register some entity to a specified entity repository.
     *
     * @param entity The entity to register.
     * @param repo The entity repository to register the entity to.
     * @return A flag denoting whether or not the entity was successfully added
     *         to the repository.
     */
    private <T extends Entity> boolean register(T entity, EntityRepository<T> repo) {
	boolean success = repo.add(entity);
	if (success) {
	    logger.info("Registered entity: {} [online={}]", entity, repo.size());
	} else {
	    logger.error("Failed to register entity (server full): {} [online={}]", entity, repo.size());
	}

	return success;
    }

    /**
     * Attempts to unregister some entity from a specified entity repository.
     *
     * @param entity The entity to unregister.
     * @param repo The entity repository to unregister the entity to.
     * @return A flag denoting whether or not the entity was successfully
     *         unregistered to the repository.
     */
    private <T extends Entity> boolean unregister(T entity, EntityRepository<T> repo) {
	boolean success = repo.remove(entity);
	if (success) {
	    logger.info("Unregistered entity: {} [online={}]", entity, repo.size());
	} else {
	    logger.error("Could not find entity to unregister: {}", entity);
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
     * Registers the specified game object.
     *
     * @param object The object.
     * @return {@code true} if the game object registered successfully,
     *         otherwise {@code false}.
     */
    public boolean register(GameObject object) {
	return register(object, objectRepository);
    }

    /**
     * Registers a ground item.
     *
     * @param item The item to add to the {@link EntityRepository}.
     * @return {@code true} if the ground item registered successfully,
     *         otherwise {@code false}.
     */
    public boolean register(GroundItem item) {
	return register(item, groundItemRepository);
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
     * Unregisters the specified {@link GameObject}.
     *
     * @param object The object.
     */
    public void unregister(GameObject object) {
	unregister(object, objectRepository);
    }

    /**
     * Unregisters a ground item.
     *
     * @param item The item to remove from the {@link EntityRepository}.
     */
    public void unregister(GroundItem item) {
	unregister(item, groundItemRepository);
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
    public EntityRepository<Player> getPlayerRepository() {
	return playerRepository;
    }

    /**
     * Returns this worlds mob repository.
     */
    public EntityRepository<Mob> getMobRepository() {
	return mobRepository;
    }

}