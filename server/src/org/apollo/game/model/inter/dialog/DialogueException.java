package org.apollo.game.model.inter.dialog;

/**
 * An exception which is thrown during the execution of a
 * {@link DialogueListener}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class DialogueException extends RuntimeException {

    /**
     * The serial version UID which is used during deserialization to verify
     * that the sender and receiver of a serialized object have loaded classes
     * for that object that are compatible with respect to serialization.
     */
    private static final long serialVersionUID = 2365306561417993250L;

    /**
     * Constructs a new {@link DialogueException}.
     * 
     * @param msg The detail message, which is a format <code>String</code> as
     *            specified by {@link String#format(String, Object...)} The
     *            detail message is cached and can be retrieved by
     *            {@link #getMessage()}.
     * @param params Arguments referenced by the format <code>String</code>'s
     *            specifiers in the format.
     * @see {@link String#format(String, Object...)}
     */
    public DialogueException(String msg, Object... params) {
	super(String.format(msg, params));
    }

}