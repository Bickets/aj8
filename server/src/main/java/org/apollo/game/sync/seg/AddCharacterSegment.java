package org.apollo.game.sync.seg;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.model.Position;
import org.apollo.game.sync.block.SynchronizationBlockSet;

/**
 * A {@link SynchronizationSegment} which adds a gameCharacter.
 *
 * @author Graham
 */
public final class AddCharacterSegment extends SynchronizationSegment {

	/**
	 * The gameCharacter to add.
	 */
	private final GameCharacter gameCharacter;

	/**
	 * The index.
	 */
	private final int index;

	/**
	 * The id of the character to add.
	 */
	private final int id;

	/**
	 * The position.
	 */
	private final Position position;

	/**
	 * Creates the add gameCharacter segment.
	 *
	 * @param blockSet The block set.
	 * @param gameCharacter The game character we are adding.
	 * @param index The characters' index.
	 * @param id The characters id.
	 * @param position The position.
	 */
	public AddCharacterSegment(SynchronizationBlockSet blockSet, GameCharacter gameCharacter, int index, int id, Position position) {
		super(blockSet);
		this.gameCharacter = gameCharacter;
		this.index = index;
		this.id = id;
		this.position = position;
	}

	/**
	 * Returns the gameCharacter.
	 *
	 * @return The gameCharacter.
	 */
	public GameCharacter getCharacter() {
		return gameCharacter;
	}

	/**
	 * Gets the gameCharacter's index.
	 *
	 * @return The index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the id of this character.
	 *
	 * @return The id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position.
	 *
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
	}

	@Override
	public SegmentType getType() {
		return SegmentType.ADD_CHARACTER;
	}

}