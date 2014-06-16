package org.apollo.game.attribute;

import java.util.Objects;

/**
 * Represents a key for an {@link Attribute<T>}
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <T> The attributes values type reference.
 */
public final class AttributeKey<T> {

    /**
     * Represents the name of this attribute key. An attributes key is an
     * identifier that encapsulates this attributes name. This key is used for
     * representing an attribute through some collection.
     */
    private final String name;

    /**
     * Constructs a new {@link AttributeKey<T>} with the specified name.
     * 
     * @param name The name of this attribute, may not be {@code null}.
     * @throws NullPointerException If the specified name is {@code null}.
     * 
     *             <p>
     *             This constructor should not be invoked directly, use
     *             {@link #valueOf(String)} instead!
     *             </p>
     */
    private AttributeKey(String name) {
	this.name = Objects.requireNonNull(name);
    }

    /**
     * Returns this attribute keys name.
     */
    protected String getName() {
	return name;
    }

    /**
     * Constructs a new {@link AttributeKey<T>} with the specified name.
     * 
     * @param name The name of this attribute, may not be {@code null}.
     * @throws NullPointerException If the specified name is {@code null}.
     * @see {@link AttributeKey<T>}
     */
    public static <T> AttributeKey<T> valueOf(String name) {
	return new AttributeKey<T>(name);
    }

}