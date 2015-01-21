package org.apollo.game.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apollo.game.action.Action;
import org.apollo.game.attribute.AttributeMap;
import org.apollo.game.model.skill.SkillSet;
import org.apollo.game.msg.Message;
import org.apollo.game.msg.impl.ServerMessageMessage;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;

/**
 * A {@link GameCharacter} is a living creature in the world, such as a player
 * or Mob.
 *
 * @author Graham
 */
public abstract class GameCharacter extends Entity {

	/**
	 * This characters walking queue.
	 */
	private final WalkingQueue walkingQueue = new WalkingQueue(this);

	/**
	 * A character fields class used to store, modify and get character
	 * attributes.
	 */
	private final GameCharacterAttributes attributes = new GameCharacterAttributes(new AttributeMap());

	/**
	 * A set of local players.
	 */
	private final Set<Player> localPlayers = new LinkedHashSet<>();

	/**
	 * A set of local mobs.
	 */
	private final Set<Mob> localMobs = new LinkedHashSet<>();

	/**
	 * The character's skill set.
	 */
	private final SkillSet skillSet = new SkillSet();

	/**
	 * This characters first direction.
	 */
	private Direction firstDirection = Direction.NONE;

	/**
	 * This characters second direction.
	 */
	private Direction secondDirection = Direction.NONE;

	/**
	 * A set of {@link SynchronizationBlock}s.
	 */
	private SynchronizationBlockSet blockSet = new SynchronizationBlockSet();

	/**
	 * The character's current action.
	 */
	private Action<?> currentAction;

	/**
	 * The index of this entity in the {@link GameCharacterRepository} it
	 * belongs to.
	 */
	private int index = -1;

	/**
	 * Creates a new character with the specified initial position.
	 *
	 * @param position The initial position of this character.
	 * @param world The world this character is in.
	 */
	protected GameCharacter(Position position, World world) {
		super(position, world);
	}

	/**
	 * Checks if this entity is active.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public final boolean isActive() {
		return index != -1;
	}

	/**
	 * Gets the index of this entity.
	 *
	 * @return The index of this entity.
	 */
	public final int getIndex() {
		synchronized (this) {
			return index;
		}
	}

	/**
	 * Sets the index of this entity.
	 *
	 * @param index The index of this entity.
	 */
	public final void setIndex(int index) {
		synchronized (this) {
			this.index = index;
		}
	}

	/**
	 * Resets the index of this entity, freeing it within its
	 * {@link GameCharacterRepository}.
	 */
	public final void resetIndex() {
		synchronized (this) {
			index = -1;
		}
	}

	/**
	 * Gets the local player set.
	 *
	 * @return The local player set.
	 */
	public final Set<Player> getLocalPlayers() {
		return localPlayers;
	}

	/**
	 * Gets the local mobs set.
	 *
	 * @return The local mobs set.
	 */
	public final Set<Mob> getLocalMobs() {
		return localMobs;
	}

	/**
	 * Gets the walking queue.
	 *
	 * @return The walking queue.
	 */
	public final WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	/**
	 * Sets the next directions for this character.
	 *
	 * @param first The first direction.
	 * @param second The second direction.
	 */
	public final void setDirections(Direction first, Direction second) {
		firstDirection = first;
		secondDirection = second;
	}

	/**
	 * Gets the first direction.
	 *
	 * @return The first direction.
	 */
	public final Direction getFirstDirection() {
		return firstDirection;
	}

	/**
	 * Gets the second direction.
	 *
	 * @return The second direction.
	 */
	public final Direction getSecondDirection() {
		return secondDirection;
	}

	/**
	 * Gets the directions as an array.
	 *
	 * @return A zero, one or two element array containing the directions (in
	 *         order).
	 */
	public final Direction[] getDirections() {
		if (firstDirection != Direction.NONE) {
			if (secondDirection != Direction.NONE) {
				return new Direction[] { firstDirection, secondDirection };
			} else {
				return new Direction[] { firstDirection };
			}
		} else {
			return Direction.EMPTY_DIRECTION_ARRAY;
		}
	}

	/**
	 * Gets the {@link SynchronizationBlockSet}.
	 *
	 * @return The block set.
	 */
	public final SynchronizationBlockSet getBlockSet() {
		return blockSet;
	}

	/**
	 * Resets the block set.
	 */
	public final void resetBlockSet() {
		blockSet = new SynchronizationBlockSet();
	}

	/**
	 * Sends an {@link Message} to either:
	 * <ul>
	 * <li>The client if this {@link GameCharacter} is a {@link Player}.</li>
	 * <li>The AI routines if this {@link GameCharacter} is a {@link Mob}</li>
	 * </ul>
	 *
	 * @param message The message.
	 */
	public abstract void send(Message message);

	/**
	 * Teleports this character to the specified position, setting the
	 * appropriate flags and clearing the walking queue.
	 *
	 * @param position The position.
	 */
	public void teleport(Position position) {
		attributes.setTeleporting(true);
		setPosition(position);

		walkingQueue.clear();
		stopAction();
	}

	/**
	 * Sets the position of this entity.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Returns the attributes for this {@link GameCharacter}.
	 */
	public final GameCharacterAttributes getAttributes() {
		return attributes;
	}

	/**
	 * Forces a game character to chat.
	 *
	 * @param text The text to chat.
	 */
	public final void forceChat(String text) {
		blockSet.add(SynchronizationBlock.createForceChatBlock(text));
	}

	/**
	 * Plays the specified animation.
	 *
	 * @param animation The animation.
	 */
	public final void playAnimation(Animation animation) {
		blockSet.add(SynchronizationBlock.createAnimationBlock(animation));
	}

	/**
	 * Stops the current animation.
	 */
	public final void stopAnimation() {
		playAnimation(Animation.STOP_ANIMATION);
	}

	/**
	 * Plays the specified graphic.
	 *
	 * @param graphic The graphic.
	 */
	public final void playGraphic(Graphic graphic) {
		blockSet.add(SynchronizationBlock.createGraphicBlock(graphic));
	}

	/**
	 * Stops the current graphic.
	 */
	public final void stopGraphic() {
		playGraphic(Graphic.STOP_GRAPHIC);
	}

	/**
	 * Gets the character's skill set.
	 *
	 * @return The character's skill set.
	 */
	public final SkillSet getSkillSet() {
		return skillSet;
	}

	/**
	 * Starts a new action, stopping the current one if it exists.
	 *
	 * @param action The new action.
	 * @return A flag indicating if the action was started.
	 */
	public final boolean startAction(Action<?> action) {
		if (currentAction != null) {
			if (currentAction.equals(action)) {
				return false;
			}
			stopAction();
		}
		currentAction = action;
		world.submit(action);
		return true;
	}

	/**
	 * Stops the current action.
	 */
	public final void stopAction() {
		if (currentAction != null) {
			currentAction.stop();
			currentAction = null;
		}
	}

	/**
	 * Turns the character to face the specified position.
	 *
	 * @param position The position to face.
	 */
	public final void turnTo(Position position) {
		blockSet.add(SynchronizationBlock.createTurnToPositionBlock(position));
	}

	/**
	 * Sends some type of message to this {@link GameCharacter} as specified by
	 * {@link T}
	 *
	 * @param message The type of message to send.
	 */
	public final <T> void sendMessage(T message) {
		send(new ServerMessageMessage(message.toString()));
	}

}