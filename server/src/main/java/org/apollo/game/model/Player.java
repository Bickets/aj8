package org.apollo.game.model;

import java.util.ArrayDeque;
import java.util.Queue;

import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inv.AppearanceInventoryListener;
import org.apollo.game.model.inv.FullInventoryListener;
import org.apollo.game.model.inv.InventoryListener;
import org.apollo.game.model.inv.SynchronizationInventoryListener;
import org.apollo.game.model.skill.LevelUpSkillListener;
import org.apollo.game.model.skill.SkillListener;
import org.apollo.game.model.skill.SynchronizationSkillListener;
import org.apollo.game.msg.Message;
import org.apollo.game.msg.impl.IdAssignmentMessage;
import org.apollo.game.msg.impl.LogoutMessage;
import org.apollo.game.msg.impl.SwitchTabInterfaceMessage;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.net.session.GameSession;
import org.apollo.security.PlayerCredentials;

/**
 * A {@link Player} is a {@link GameCharacter} that a user is controlling.
 *
 * @author Graham
 */
public final class Player extends GameCharacter {

    /**
     * An enumeration with the different privilege levels a player can have.
     *
     * @author Graham
     */
    public enum PrivilegeLevel {

	/**
	 * A standard (rights 0) account.
	 */
	STANDARD(0),

	/**
	 * A player moderator (rights 1) account.
	 */
	MODERATOR(1),

	/**
	 * An administrator (rights 2) account.
	 */
	ADMINISTRATOR(2);

	/**
	 * Gets the privilege level for the specified numerical level.
	 *
	 * @param numericalLevel The numerical level.
	 * @return The privilege level.
	 * @throws IllegalArgumentException if the numerical level is invalid.
	 */
	public static PrivilegeLevel valueOf(int numericalLevel) {
	    for (PrivilegeLevel level : values()) {
		if (level.numericalLevel == numericalLevel) {
		    return level;
		}
	    }
	    throw new IllegalArgumentException("invalid numerical level");
	}

	/**
	 * The numerical level used in the protocol.
	 */
	private final int numericalLevel;

	/**
	 * Creates a privilege level.
	 *
	 * @param numericalLevel The numerical level.
	 */
	private PrivilegeLevel(int numericalLevel) {
	    this.numericalLevel = numericalLevel;
	}

	/**
	 * Gets the numerical level.
	 *
	 * @return The numerical level used in the protocol.
	 */
	public int toInteger() {
	    return numericalLevel;
	}

    }

    /**
     * A temporary queue of messages sent during the login process.
     */
    private final Queue<Message> queuedMessages = new ArrayDeque<>();

    /**
     * The players credentials.
     */
    private final PlayerCredentials credentials;

    /**
     * This player's privilege level.
     */
    private PrivilegeLevel privilegeLevel = PrivilegeLevel.ADMINISTRATOR;

    /**
     * The membership flag.
     */
    private boolean members = false;

    /**
     * The flagged flag.
     */
    private boolean flagged = false;

    /**
     * A flag indicating if the player has designed their character.
     */
    private boolean designedCharacter = false;

    /**
     * The {@link GameSession} currently attached to this {@link Player}.
     */
    private GameSession session;

    /**
     * The players appearance.
     */
    private Appearance appearance = Appearance.DEFAULT_APPEARANCE;

    /**
     * The current maximum viewing distance of this player.
     */
    private int viewingDistance = 1;

    /**
     * A flag which indicates there are players that couldn't be added.
     */
    private boolean excessivePlayers = false;

    /**
     * A flag which indicates there are mobs that couldn't be added.
     */
    private boolean excessiveMobs = false;

    /**
     * The database entry id, cached during player load to later be used to
     * identify where to save.
     */
    private int databaseId;

    /**
     * This players interface set.
     */
    private final InterfaceSet interfaceSet = new InterfaceSet(this);

    /**
     * Creates the {@link Player}.
     *
     * @param credentials The players credentials.
     * @param position The initial position.
     */
    public Player(PlayerCredentials credentials, Position position) {
	super(position);
	init();
	this.credentials = credentials;
    }

    /**
     * Gets this players interface set.
     *
     * @return The interface set for this player.
     */
    public InterfaceSet getInterfaceSet() {
	return interfaceSet;
    }

    /**
     * Checks if there are excessive players.
     *
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isExcessivePlayersSet() {
	return excessivePlayers;
    }

    /**
     * Checks if there are excessive mobs.
     *
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isExcessivemMobsSet() {
	return excessiveMobs;
    }

    /**
     * Sets the excessive players flag.
     */
    public void flagExcessivePlayers() {
	excessivePlayers = true;
    }

    /**
     * Resets the excessive players flag.
     */
    public void resetExcessivePlayers() {
	excessivePlayers = false;
    }

    /**
     * Sets the excessive mobs flag.
     */
    public void flagExcessiveMobs() {
	excessiveMobs = true;
    }

    /**
     * Resets the excessive mobs flag.
     */
    public void resetExcessiveMobs() {
	excessiveMobs = false;
    }

    /**
     * Resets this players viewing distance.
     */
    public void resetViewingDistance() {
	viewingDistance = 1;
    }

    /**
     * Gets this players viewing distance.
     *
     * @return The viewing distance.
     */
    public int getViewingDistance() {
	return viewingDistance;
    }

    /**
     * Increments this players viewing distance if it is less than the maximum
     * viewing distance.
     */
    public void incrementViewingDistance() {
	if (viewingDistance < Position.MAX_DISTANCE) {
	    viewingDistance++;
	}
    }

    /**
     * Decrements this players viewing distance if it is greater than 1.
     */
    public void decrementViewingDistance() {
	if (viewingDistance > 1) {
	    viewingDistance--;
	}
    }

    /**
     * Gets the privilege level.
     *
     * @return The privilege level.
     */
    public PrivilegeLevel getPrivilegeLevel() {
	return privilegeLevel;
    }

    /**
     * Sets the privilege level.
     *
     * @param privilegeLevel The privilege level.
     */
    public void setPrivilegeLevel(PrivilegeLevel privilegeLevel) {
	this.privilegeLevel = privilegeLevel;
    }

    /**
     * Checks if this player account has membership.
     *
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isMembers() {
	return members;
    }

    /**
     * Changes the membership status of this player.
     *
     * @param members The new membership flag.
     */
    public void setMembers(boolean members) {
	this.members = members;
    }

    /**
     * Sets the players {@link GameSession}.
     *
     * @param session The players {@link GameSession}.
     * @param reconnecting The reconnecting flag.
     */
    public void setSession(GameSession session, boolean reconnecting) {
	this.session = session;
	if (!reconnecting) {
	    sendInitialMessages();
	}
	getBlockSet().add(SynchronizationBlock.createAppearanceBlock(this));
    }

    /**
     * Gets the players credentials.
     *
     * @return The players credentials.
     */
    public PlayerCredentials getCredentials() {
	return credentials;
    }

    @Override
    public void send(Message message) {
	if (!isActive()) {
	    queuedMessages.add(message);
	    return;
	}

	if (!queuedMessages.isEmpty()) {
	    for (Message evt : queuedMessages) {
		session.dispatchMessage(evt);
	    }
	    queuedMessages.clear();
	}

	session.dispatchMessage(message);
    }

    /**
     * Initialises this player.
     */
    private void init() {
	initInventories();
	initSkills();
    }

    /**
     * Initialises the players skills.
     */
    private void initSkills() {
	SkillSet skills = getSkillSet();

	// synchronization listener
	SkillListener syncListener = new SynchronizationSkillListener(this);

	// level up listener
	SkillListener levelUpListener = new LevelUpSkillListener(this);

	// add the listeners
	skills.addListener(syncListener);
	skills.addListener(levelUpListener);
    }

    /**
     * Initialises the players inventories.
     */
    private void initInventories() {
	Inventory inventory = getInventory();
	Inventory bank = getBank();
	Inventory equipment = getEquipment();

	// inventory full listeners
	InventoryListener fullInventoryListener = new FullInventoryListener(this, FullInventoryListener.FULL_INVENTORY_MESSAGE);
	InventoryListener fullBankListener = new FullInventoryListener(this, FullInventoryListener.FULL_BANK_MESSAGE);
	InventoryListener fullEquipmentListener = new FullInventoryListener(this, FullInventoryListener.FULL_EQUIPMENT_MESSAGE);

	// equipment appearance listener
	InventoryListener appearanceListener = new AppearanceInventoryListener(this);

	// synchronization listeners
	InventoryListener syncInventoryListener = new SynchronizationInventoryListener(this, SynchronizationInventoryListener.INVENTORY_ID);
	InventoryListener syncBankListener = new SynchronizationInventoryListener(this, BankConstants.BANK_INVENTORY_ID);
	InventoryListener syncEquipmentListener = new SynchronizationInventoryListener(this, SynchronizationInventoryListener.EQUIPMENT_ID);

	// add the listeners
	inventory.addListener(syncInventoryListener);
	inventory.addListener(fullInventoryListener);
	bank.addListener(syncBankListener);
	bank.addListener(fullBankListener);
	equipment.addListener(syncEquipmentListener);
	equipment.addListener(appearanceListener);
	equipment.addListener(fullEquipmentListener);
    }

    /**
     * Sends the initial messages.
     */
    private void sendInitialMessages() {
	// vital initial stuff
	send(new IdAssignmentMessage(getIndex(), members));
	sendMessage("Welcome to RuneScape.");

	// character design screen
	if (!designedCharacter) {
	    interfaceSet.openWindow(InterfaceConstants.CHARACTER_DESIGN_INTERFACE_ID);
	}

	for (int i = 0; i < InterfaceConstants.TAB_INTERFACE_IDS.length; i++) {
	    send(new SwitchTabInterfaceMessage(i, InterfaceConstants.TAB_INTERFACE_IDS[i]));
	}

	// force inventories to update
	getInventory().forceRefresh();
	getEquipment().forceRefresh();
	getBank().forceRefresh();

	// force skills to update
	getSkillSet().forceRefresh();
    }

    @Override
    public String toString() {
	return getClass().getName() + " [username=" + credentials.getUsername() + ", privilegeLevel=" + privilegeLevel + "]";
    }

    /**
     * Gets the players appearance.
     *
     * @return The appearance.
     */
    public Appearance getAppearance() {
	return appearance;
    }

    /**
     * Sets the players appearance.
     *
     * @param appearance The new appearance.
     */
    public void setAppearance(Appearance appearance) {
	this.appearance = appearance;
	getBlockSet().add(SynchronizationBlock.createAppearanceBlock(this));
    }

    /**
     * Gets the players name.
     *
     * @return The players name.
     */
    public String getName() {
	return credentials.getUsername();
    }

    /**
     * Gets the players password.
     *
     * @return The players password.
     */
    public String getPassword() {
	return credentials.getPassword();
    }

    /**
     * Gets the players name, encoded as a long.
     *
     * @return The encoded player name.
     */
    public long getEncodedName() {
	return credentials.getEncodedUsername();
    }

    /**
     * Logs the player out, if possible.
     */
    public void logout() {
	send(new LogoutMessage());
    }

    /**
     * Gets the game session.
     *
     * @return The game session.
     */
    public GameSession getSession() {
	return session;
    }

    /**
     * Sets the character design flag.
     *
     * @param designedCharacter A flag indicating if the character has been
     *            designed.
     */
    public void setDesignedCharacter(boolean designedCharacter) {
	this.designedCharacter = designedCharacter;
    }

    /**
     * Checks if the player has designed their character.
     *
     * @return A flag indicating if the player has designed their character.
     */
    public boolean hasDesignedCharacter() {
	return designedCharacter;
    }

    /**
     * Returns this players database id.
     *
     * @return The database id.
     */
    public int getDatabaseId() {
	return databaseId;
    }

    /**
     * Sets this players database id.
     *
     * @param databaseId The new database id.
     */
    public void setDatabaseId(int databaseId) {
	this.databaseId = databaseId;
    }

    /**
     * Returns the players flagged state.
     *
     * @return Whether or not this player is flagged.
     */
    public boolean isFlagged() {
	return flagged;
    }

    /**
     * Sets this players flagged state.
     *
     * @param flagged The new flagged state.
     */
    public void setFlagged(boolean flagged) {
	this.flagged = flagged;
    }

    @Override
    public void teleport(Position position) {
	super.teleport(position);
	interfaceSet.close();
    }

    @Override
    public EntityType getType() {
	return EntityType.PLAYER;
    }

}
