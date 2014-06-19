package org.apollo.game.model.inter.dialog;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents an option within a dialogue.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum DialogueOption {
    /**
     * Represents the first option within a dialogue.
     */
    FIRST_OPTION(2461, 2471, 2482, 2494),

    /**
     * Represents the second option within a dialogue.
     */
    SECOND_OPTION(2462, 2472, 2483, 2495),

    /**
     * Represents the third option within a dialogue.
     */
    THIRD_OPTION(2473, 2484, 2496),

    /**
     * Represents the fourth option within a dialogue.
     */
    FOURTH_OPTION(2497, 2485),

    /**
     * Represents the fifth option within a dialogue.
     */
    FIFTH_OPTION(2498),

    /**
     * Represents the make 1 option within a dialogue.
     */
    MAKE_1(8874, 8878, 2799, 8889, 8893, 8897, 8909, 8913, 8917, 8921, 8949, 8953, 8957, 8961, 8965),

    /**
     * Represents the make 5 option within a dialogue.
     */
    MAKE_5(8873, 8877, 2798, 8888, 8892, 8896, 8908, 8912, 8916, 8920, 8948, 8952, 8956, 8960, 8964),

    /**
     * Represents the make 10 option within a dialogue.
     */
    MAKE_10(8872, 8876, 8887, 8891, 8895, 8907, 8911, 8915, 8919, 8947, 8951, 8955, 8959, 8963),

    /**
     * Represents the make x option within a dialogue.
     */
    MAKE_X(8871, 8875, 1748, 8886, 8890, 8894, 8906, 8910, 8914, 8918, 8946, 8950, 8954, 8958, 8962),

    /**
     * Represents the make all option within a dialogue.
     */
    MAKE_ALL(1747);

    /**
     * The button click identifiers for each dialogue option.
     */
    private final int[] ids;

    /**
     * Constructs a new {@link DialogueOption} with the specified button click
     * identifiers.
     *
     * @param ids The dialogue option button identifiers.
     */
    private DialogueOption(int... ids) {
	this.ids = ids;
    }

    /**
     * Returns the dialogue option button identifiers.
     */
    public final int[] getIds() {
	return ids;
    }

    /**
     * A {@link Set} of every {@code enum} constant within
     * {@link DialogueOption}.
     */
    private static final Set<DialogueOption> ALL_OPTIONS = EnumSet.allOf(DialogueOption.class);

    /**
     * Returns an instance of {@link DialogueOption} from the specified id if
     * the id exists within some dialogue option's {@link #ids}.
     *
     * @param _id The id.
     * @return The dialogue option.
     * @throws IllegalArgumentException If the specified id does not have a
     *             parent dialogue option.
     */
    public static DialogueOption fromId(int _id) {
	for (DialogueOption option : ALL_OPTIONS) {
	    for (int id : option.getIds()) {
		if (_id == id) {
		    return option;
		}
	    }
	}
	throw new IllegalArgumentException("No enum constant exists for specified id: " + _id);
    }

}