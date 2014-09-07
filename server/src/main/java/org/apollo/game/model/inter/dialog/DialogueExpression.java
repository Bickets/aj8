package org.apollo.game.model.inter.dialog;

import org.apollo.game.model.Animation;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * Represents an expression animation performed by a {@link Player} or
 * {@link Mob} during a player or mob statement dialogue.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum DialogueExpression {
	/**
	 * The default expression, none.
	 */
	NO_EXPRESSION(591),

	/**
	 * A variation of showing sadness.
	 */
	SAD(599),

	/**
	 * A variation of showing sadness.
	 */
	SAD_TWO(611),

	/**
	 * A variation of showing frustration or anger.
	 */
	MIDLY_ANGRY(614),

	/**
	 * A variation of showing frustration or anger.
	 */
	ANGRY(615),

	/**
	 * A variation of showing frustration or anger.
	 */
	VERY_ANGRY(616),

	/**
	 * A variation of showing frustration or anger.
	 */
	ANGRY_TWO(617);

	/**
	 * Constructs a new {@link DialogueExpression} with the specified animtaion
	 * id.
	 *
	 * @param animationId The animation id.
	 */
	private DialogueExpression(int animationId) {
		animation = new Animation(animationId);
	}

	/**
	 * The animation object constructed with the specified animation id.
	 */
	private final Animation animation;

	/**
	 * Returns the animation.
	 */
	public final Animation getAnimation() {
		return animation;
	}

}