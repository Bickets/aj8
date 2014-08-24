package org.apollo.game.action;

import org.apollo.game.model.GameCharacter;

/**
 * Represents a staged action implementing the state machine design pattern.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <T> The game character performing this action.
 * @param <E> The {@code enum} representing the state for this action.
 */
public abstract class StagedAction<T extends GameCharacter, E extends Enum<E>> extends Action<T> {

    /**
     * Represents the current stage which IS mutable, can be changed.
     */
    private E stage;

    /**
     * Constructs a new {@link StagedAction} with the specified delay, character
     * and initial stage.
     *
     * @param delay The delay, in ticks, this action will recursively fire at
     *            until stopped.
     * @param character The character performing this action.
     * @param stage The initial stage of this action.
     */
    public StagedAction(int delay, T character, E stage) {
	super(delay, true, character);
	this.stage = stage;
    }

    @Override
    public final void execute() {
	execute(stage);
    }

    /**
     * Executes this action using the current stage.
     *
     * @param stage The current stage to execute.
     */
    protected abstract void execute(E stage);

    /**
     * Always returns the current stage.
     */
    protected final E current() {
	return stage;
    }

    /**
     * Should be called when the stage has moved on, reached its checkpoint.
     * This method sets a new current stage.
     */
    protected final void checkpoint(E stage) {
	this.stage = stage;
    }

}