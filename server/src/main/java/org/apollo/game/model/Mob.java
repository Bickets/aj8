package org.apollo.game.model;

import org.apollo.game.model.def.MobDefinition;
import org.apollo.game.msg.Message;

/**
 * A {@link GameCharacter} that has AI and is not controlled by a {@link Player}
 * .
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Mob extends GameCharacter {

    /**
     * This mobs definition.
     */
    private final MobDefinition definition;

    /**
     * The initial facing direction of this mob.
     */
    private final Direction initialFaceDirection;

    /**
     * Constructs a new {@link Mob}.
     *
     * @param id The id of the mob.
     * @param position The position of the mob.
     */
    public Mob(int id, Position position) {
	this(MobDefinition.forId(id), position, Direction.NONE);
    }

    /**
     * Constructs a new {@link Mob}.
     *
     * @param id The id of the mob.
     * @param position The position of the mob.
     * @param direction The initial facing direction of this mob.
     */
    public Mob(int id, Position position, Direction direction) {
	this(MobDefinition.forId(id), position, direction);
    }

    /**
     * Constructs a new {@link Mob}.
     *
     * @param definition The mobs definitions.
     * @param position The position of the mob.
     * @param initialFaceDirection The initial facing direction of this mob.
     */
    public Mob(MobDefinition definition, Position position, Direction initialFaceDirection) {
	super(position);
	this.definition = definition;
	this.initialFaceDirection = initialFaceDirection;
    }

    /**
     * Returns the mobs definitions.
     *
     * @return The definition of this mob.
     */
    public MobDefinition getDefinition() {
	return definition;
    }

    /**
     * Returns the initial face direction of this mob.
     */
    public Direction getInitialFaceDirection() {
	return initialFaceDirection;
    }

    @Override
    public void send(Message message) {
	throw new UnsupportedOperationException();
    }

    @Override
    public EntityType getType() {
	return EntityType.MOB;
    }

}
