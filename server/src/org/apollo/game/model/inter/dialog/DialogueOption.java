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
    FIFTH_OPTION(2498);

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
     * Returns the dialogue option button identifiers
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
     * the id exists within some dialogue option's {@link #ids}
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