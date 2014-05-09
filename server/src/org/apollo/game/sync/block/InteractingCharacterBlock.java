package org.apollo.game.sync.block;

/**
 * The InteractingCharacterBlock {@link SynchronizationBlock}.
 * 
 * @note As all Apollo events should be immutable (for more information as to
 *       why, see http://pastebin.com/MJLWqC5F and http://bit.ly/L5820k), this
 *       uses the index of the character rather than an actual {@link Character}
 *       field. This should not be changed.
 * @author Major
 */
public class InteractingCharacterBlock extends SynchronizationBlock {

    /**
     * The index of the Interacting {@link Character}.
     */
    private final int characterIndex;

    /**
     * Creates the Interacting Character block.
     * 
     * @param characterIndex The index of the current interacting character.
     */
    public InteractingCharacterBlock(int characterIndex) {
	this.characterIndex = characterIndex;
    }

    /**
     * Gets the interacting character's current index.
     * 
     * @return The index of the character.
     */
    public int getInteractingCharacterIndex() {
	return characterIndex;
    }

}
