package org.apollo.game.msg.handler;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.CharacterDesignMessage;
import org.apollo.game.msg.impl.CloseInterfaceMessage;

/**
 * A handler which handles {@link CharacterDesignMessage}s.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(CharacterDesignMessage.class)
public final class CharacterDesignMessageHandler implements MessageHandler<CharacterDesignMessage> {

	/**
	 * The maximum amount of colors for each feature.
	 */
	private static final int[] MAXIMUM_COLORS = { 11, 15, 15, 5, 7 };

	/**
	 * The minimum amount of valid style combinations for males.
	 */
	private static final int[] MINIMUM_MALE_STYLES = { 0, 10, 18, 26, 33, 36,
			42 };

	/**
	 * The maximum amount of valid style combinations for males.
	 */
	private static final int[] MAXIMUM_MALE_STYLES = { 8, 17, 25, 31, 34, 40,
			43 };

	/**
	 * The minimum amount of valid style combinations for females.
	 */
	private static final int[] MINIMUM_FEMALE_STYLES = { 45, 255, 56, 61, 67,
			70, 79 };

	/**
	 * The maximum amount of valid style combinations for females.
	 */
	private static final int[] MAXIMUM_FEMALE_STYLES = { 54, 255, 60, 65, 68,
			77, 80 };

	@Override
	public void handle(Player player, CharacterDesignMessage message) {
		if (!valid(message.getAppearance()) || player.hasDesignedCharacter()) {
			return;
		}

		player.setAppearance(message.getAppearance());
		player.setDesignedCharacter(true);
		player.send(new CloseInterfaceMessage());
	}

	/**
	 * Checks if an appearance combination is valid.
	 *
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private static boolean valid(Appearance appearance) {
		int[] colors = appearance.getColors();
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] < 0 || colors[i] > MAXIMUM_COLORS[i]) {
				return false;
			}
		}

		Gender gender = appearance.getGender();
		if (gender == Gender.MALE) {
			return validMaleStyle(appearance);
		} else if (gender == Gender.FEMALE) {
			return validFemaleStyle(appearance);
		} else {
			return false; // maybe null?
		}
	}

	/**
	 * Checks if a {@link Gender#MALE} style combination is valid.
	 *
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private static boolean validMaleStyle(Appearance appearance) {
		int[] styles = appearance.getStyle();
		for (int i = 0; i < styles.length; i++) {
			if (styles[i] < MINIMUM_MALE_STYLES[i] || styles[i] > MAXIMUM_MALE_STYLES[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a {@link Gender#FEMALE} style combination is valid.
	 *
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private static boolean validFemaleStyle(Appearance appearance) {
		int[] styles = appearance.getStyle();
		for (int i = 0; i < styles.length; i++) {
			if (styles[i] < MINIMUM_FEMALE_STYLES[i] || styles[i] > MAXIMUM_FEMALE_STYLES[i]) {
				return false;
			}
		}
		return true;
	}

}