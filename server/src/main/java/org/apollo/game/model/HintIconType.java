package org.apollo.game.model;

public enum HintIconType {
    MOB(1), CENTRAL(2), WEST(3), EAST(4), SOUTH(5), NORTH(6), PLAYER(10);
    
    private int numericType;
    
    HintIconType(int numericType)
    {
	this.numericType = numericType;
    }
    
    public int getNumericType()
    {
	return numericType;
    }
}
