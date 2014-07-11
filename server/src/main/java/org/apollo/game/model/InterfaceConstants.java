package org.apollo.game.model;

/**
 * Represents constant values specified to interfaces.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class InterfaceConstants {

    /**
     * The character design interface id.
     */
    public static final int CHARACTER_DESIGN_INTERFACE_ID = 3559;

    /**
     * The tab interface ids. 6299 = music tab, music disabled 4445 = settings
     * tab, music disabled 12855 = ancients magic
     */
    public static final int[] TAB_INTERFACE_IDS = { 2423, 3917, 638, 3213,
	    1644, 5608, 1151, -1, 5065, 5715, 2449, 904, 147, 962, };

    /**
     * Represents the option clicked on some drop-down interface menu.
     * 
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     */
    public enum InterfaceOption {
	OPTION_ONE, OPTION_TWO, OPTION_THREE, OPTION_FOUR, OPTION_FIVE
    }

}