package org.apollo.game.sync.block;

/**
 * The InteractingCharacterBlock {@link SynchronizationBlock}.
 * 
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
