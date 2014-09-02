package org.apollo.game.model;

/**
 * The different types for hint icons.
 * 
 * @author James Monger
 *
 */
public enum HintIconType {
    /**
     * The different types of hint icon and their numeric values.
     */
    MOB(1), CENTRAL(2), WEST(3), EAST(4), SOUTH(5), NORTH(6), PLAYER(10);
    
    /**
     * Store the numeric value of the hint icon so that it can
     * be sent to the client.
     */
    private int numericType;
    
    /**
     * Construct a new hint icon.
     * 
     * @param numericType The numeric value to to be sent to the
     * client.
     */
    HintIconType(int numericType)
    {
	this.numericType = numericType;
    }
    
    /**
     * Get the numeric value of a hint icon.
     * @return The numeric value.
     */
    public int getNumericType()
    {
	return numericType;
    }
}
