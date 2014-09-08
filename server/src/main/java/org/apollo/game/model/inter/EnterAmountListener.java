package org.apollo.game.model.inter;

/**
 * A listener for the enter amount dialog.
 *
 * <p>
 * This is a functional interface whose functional method is
 * {@link #amountEntered(int)}
 *
 * @author Graham
 */
@FunctionalInterface
public interface EnterAmountListener {

	/**
	 * Called when the player enters the specified amount.
	 *
	 * @param amount The amount.
	 */
	void amountEntered(int amount);

}