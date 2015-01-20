package org.apollo.game.model;

import io.netty.util.internal.StringUtil;

import org.apollo.game.model.area.Area;
import org.apollo.game.model.def.MobDefinition;
import org.apollo.game.msg.Message;

/**
 * A {@link GameCharacter} that has AI and is not controlled by a {@link Player}
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Mob extends GameCharacter {

	/**
	 * This mobs definition.
	 */
	private final MobDefinition definition;

	/**
	 * Represents this mobs spawn position.
	 */
	private Position spawnPosition;

	/**
	 * Represents this mobs movement area.
	 */
	private Area movementArea;

	/**
	 * Represents the movement distance this mob is allowed to move.
	 */
	private int movementDistance = 1;

	/**
	 * Constructs a new {@link Mob}.
	 *
	 * @param id The id of the mob.
	 * @param position The position of the mob.
	 * @param world The world this mob is in.
	 */
	public Mob(int id, Position position, World world) {
		this(MobDefinition.forId(id), position, world);
	}

	/**
	 * Constructs a new {@link Mob}.
	 *
	 * @param definition The mobs definitions.
	 * @param position The position of the mob.
	 * @param world The world this mob is in.
	 */
	public Mob(MobDefinition definition, Position position, World world) {
		super(position, world);
		this.definition = definition;
		spawnPosition = position;
	}

	/**
	 * Returns this mobs definitions.
	 */
	public MobDefinition getDefinition() {
		return definition;
	}

	/**
	 * Returns this mobs spawn position.
	 */
	public Position getSpawnPosition() {
		return spawnPosition;
	}

	/**
	 * Returns the current movement area.
	 */
	public Area getMovementArea() {
		return movementArea;
	}

	/**
	 * Sets this mobs movement area.
	 */
	public void setMovementArea(Area movementArea) {
		this.movementArea = movementArea;
	}

	/**
	 * Returns the current movement distance.
	 */
	public int getMovementDistance() {
		return movementDistance;
	}

	/**
	 * Sets this mobs movement distance.
	 */
	public void setMovementDistance(int movementDistance) {
		this.movementDistance = movementDistance;
	}

	@Override
	public void setPosition(Position position) {
		spawnPosition = position;
		super.setPosition(position);
	}

	@Override
	public void send(Message message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EntityCategory getCategory() {
		return EntityCategory.MOB;
	}

	@Override
	public int getSize() {
		return definition.getSize();
	}

	@Override
	public String toString() {
		return StringUtil.simpleClassName(this) + " [spawnPosition=" + spawnPosition + ", movementArea=" + movementArea + ", movementDistance=" + movementDistance + "]";
	}

}