package org.apollo.game.model.inter.dialog;

/**
 * Contains dialogue-related information.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
final class DialogueConstants {

    /**
     * This array contains the child id where the dialogue statement starts for
     * mob and item dialogues.
     */
    protected static final int[] MOB_DIALOGUE_ID = { 4885, 4890, 4896, 4903 };

    /**
     * This array contains the child id where the dialogue statement starts for
     * player dialogues.
     */
    protected static final int[] PLAYER_DIALOGUE_ID = { 971, 976, 982, 989 };

    /**
     * This array contains the child id's for dialogue statement.
     */
    protected static final int[] STATEMENT_DIALOGUE_ID = { 12788, 12790, 12793,
	    12797, 12802 };

    /**
     * This 2d array contains the child id's for dialogue continue statement.
     */
    protected static final int[][] CONTINUE_STATEMENT_DIALOGUE_ID = {
	    { 11859, 11861 }, { 11864, 11867, 11866 },
	    { 11870, 11873, 11872, 11875 } };

    /**
     * This array contains the child id where the dialogue statement starts for
     * option dialogues.
     */
    protected static final int[] OPTION_DIALOGUE_ID = { 13760, 2461, 2471,
	    2482, 2494, };

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     * 
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private DialogueConstants() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}