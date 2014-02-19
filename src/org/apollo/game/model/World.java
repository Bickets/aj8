package org.apollo.game.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apollo.Service;
import org.apollo.fs.IndexedFileSystem;
import org.apollo.fs.parser.ItemDefinitionParser;
import org.apollo.fs.parser.MobDefinitionParser;
import org.apollo.game.model.def.EquipmentDefinition;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.def.MobDefinition;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.scheduling.Scheduler;
import org.apollo.io.EquipmentDefinitionParser;
import org.apollo.util.CharacterRepository;

/**
 * The world class is a singleton which contains objects like the
 * {@link CharacterRepository} for players and mobs. It should only contain
 * things relevant to the in-game world and not classes which deal with I/O and
 * such (these may be better off inside some custom {@link Service} or other
 * code, however, the circumstances are rare).
 * @author Graham
 */
public final class World {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(World.class.getName());

	/**
	 * The world.
	 */
	private static final World world = new World();

	/**
	 * Represents the different status codes for registering a player.
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
	 * Gets the world.
	 * @return The world.
	 */
	public static World getWorld() {
		return world;
	}

	/**
	 * The {@link CharacterRepository} of {@link Mob}s.
	 */
	private final CharacterRepository<Mob> mobRepository = new CharacterRepository<Mob>(WorldConstants.MAXIMUM_MOBS);

	/**
	 * The {@link CharacterRepository} of {@link Player}s.
	 */
	private final CharacterRepository<Player> playerRepository = new CharacterRepository<Player>(WorldConstants.MAXIMUM_PLAYERS);

	/**
	 * A {@link Map} of active players.
	 */
	private final Map<Long, Player> activePlayers = new HashMap<>(WorldConstants.MAXIMUM_PLAYERS);

	/**
	 * A {@link Map} of active mobs.
	 */
	private final Map<Integer, Mob> activeMobs = new HashMap<>(WorldConstants.MAXIMUM_MOBS);

	/**
	 * Creates the world.
	 */
	private World() {

	}

	/**
	 * Initialises the world by loading definitions from the specified file
	 * system.
	 * @param fs The file system.
	 * @throws IOException if an I/O error occurs.
	 */
	public void init(IndexedFileSystem fs) throws IOException {
		logger.info("Loading item definitions...");
		ItemDefinitionParser itemParser = new ItemDefinitionParser(fs);
		ItemDefinition[] itemDefs = itemParser.parse();
		ItemDefinition.init(itemDefs);
		logger.info("Done (loaded " + itemDefs.length + " item definitions).");

		logger.info("Loading equipment definitions...");
		int nonNull = 0;
		InputStream is = new BufferedInputStream(new FileInputStream("data/equipment.dat"));
		try {
			EquipmentDefinitionParser equipParser = new EquipmentDefinitionParser(is);
			EquipmentDefinition[] equipDefs = equipParser.parse();
			for (EquipmentDefinition def : equipDefs) {
				if (def != null) {
					nonNull++;
				}
			}
			EquipmentDefinition.init(equipDefs);
		} finally {
			is.close();
		}
		logger.info("Done (loaded " + nonNull + " equipment definitions).");

		logger.info("Loading mob definitions...");
		MobDefinitionParser mobParser = new MobDefinitionParser(fs);
		MobDefinition[] mobDefs = mobParser.parse();
		MobDefinition.init(mobDefs);
		logger.info("Done (loaded " + mobDefs.length + " mob definitions).");
	}

	/**
	 * Gets the character repository. NOTE:
	 * {@link CharacterRepository#add(GameCharacter)} and
	 * {@link CharacterRepository#remove(GameCharacter)} should not be called
	 * directly! These mutation methods are not guaranteed to work in future
	 * releases!
	 * <p>
	 * Instead, use the {@link World#register(Player)} and
	 * {@link World#unregister(Player)} methods which do the same thing and
	 * will continue to work as normal in future releases.
	 * @return The character repository.
	 */
	public CharacterRepository<Player> getPlayerRepository() {
		return playerRepository;
	}

	/**
	 * Gets the mob repository.
	 * @return The mob repository.
	 */
	public CharacterRepository<Mob> getMobRepository() {
		return mobRepository;
	}

	/**
	 * Registers the specified player.
	 * @param player The player.
	 * @return A {@link RegistrationStatus}.
	 */
	public RegistrationStatus register(Player player) {
		if (isPlayerOnline(player.getEncodedName())) {
			return RegistrationStatus.ALREADY_ONLINE;
		}

		boolean success = playerRepository.add(player);
		if (success) {
			activePlayers.put(player.getEncodedName(), player);
			logger.info("Registered player: " + player + " [online=" + playerRepository.size() + "]");
			return RegistrationStatus.OK;
		} else {
			logger.warning("Failed to register player (server full): " + player + " [online=" + playerRepository.size() + "]");
			return RegistrationStatus.WORLD_FULL;
		}
	}

	/**
	 * Registers the specified mob.
	 * @param mob The mob.
	 * @return {@code true} if the mob registered successfully, otherwise {@code false}.
	 */
	public boolean register(Mob mob) {
		boolean success = mobRepository.add(mob);
		if (success) {
			activeMobs.put( mob.getIndex(), mob );
			logger.info("Registered mob: " + mob + " [online=" + mobRepository.size() + "]");
		} else {
			logger.warning("Failed to register mob, repository capacity reached: [online=" + mobRepository.size() + "]");
		}
		return success;
	}

	/**
	 * Checks if the specified player is online.
	 * @param name The players name, as a long
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isPlayerOnline(long name) {
		return activePlayers.containsKey(name);
	}

	/**
	 * Unregisters the specified player.
	 * @param player The player.
	 */
	public void unregister(Player player) {
		if (playerRepository.remove(player)) {
			activePlayers.remove(player.getEncodedName());
			logger.info("Unregistered player: " + player + " [online=" + playerRepository.size() + "]");
		} else {
			logger.warning("Could not find player to unregister: " + player + "!");
		}
	}

	/**
	 * Unregisters the specified {@link Mob}.
	 * @param mob The mob.
	 */
	public void unregister(Mob mob) {
		if (mobRepository.remove(mob)) {
			activeMobs.remove(mob.getIndex());
			logger.info("Unregistered mob: " + mob + " [online=" + mobRepository.size() + "]");
		} else {
			logger.warning("Could not find mob " + mob + " to unregister!");
		}
	}

	/**
	 * Returns a {@link Player} object by their encoded username.
	 * @param name	The name to lookup.
	 * @return	The player object, if found. Otherwise null will be returned.
	 */
	public Player getPlayerByName(long name) {
		Player player = activePlayers.get(name);
		return player;
	}

	/**
	 * Schedules a new task.
	 * @param task The {@link ScheduledTask}.
	 */
	public void schedule(ScheduledTask task) {
		Scheduler.getInstance().schedule(task);
	}

	/**
	 * Calls the {@link Scheduler#pulse()} method.
	 */
	public void pulse() {
		Scheduler.getInstance().pulse();
	}

}