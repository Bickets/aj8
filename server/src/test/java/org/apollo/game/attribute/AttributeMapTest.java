package org.apollo.game.attribute;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class AttributeMapTest {

    /**
     * @see <a href=https://github.com/atomicint/aj8/issues/1>Issue 1</a>
     */
    @Test
    public void testGet() {
	AttributeMap map = new AttributeMap();
	AttributeKey<Integer> integerKey = AttributeKey.valueOf("key", 0);

	int value = map.get(integerKey);
	assertEquals(0, value);
    }

    @Test
    public void testSetAndGet() {
	AttributeMap map = new AttributeMap();
	AttributeKey<Integer> integerKey = AttributeKey.valueOf("key", 0);

	int value = map.setAndGet(integerKey, 1);
	assertEquals(1, value);

	value = map.get(integerKey);
	assertEquals(1, value);
    }

}