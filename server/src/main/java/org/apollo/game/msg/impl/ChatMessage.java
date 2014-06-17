package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent by the client to send a public chat message to other players.
 *
 * @author Graham
 */
public final class ChatMessage extends Message {

    /**
     * The message.
     */
    private final String message;

    /**
     * The compressed message.
     */
    private final byte[] compressedMessage;

    /**
     * The text color.
     */
    private final int color;

    /**
     * The text effects.
     */
    private final int effects;

    /**
     * Creates a new chat message.
     *
     * @param message The message.
     * @param compressedMessage The compressed message.
     * @param color The text color.
     * @param effects The text effects.
     */
    public ChatMessage(String message, byte[] compressedMessage, int color, int effects) {
	this.message = message;
	this.compressedMessage = compressedMessage;
	this.color = color;
	this.effects = effects;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
	return message;
    }

    /**
     * Gets the text color.
     *
     * @return The text color.
     */
    public int getTextColor() {
	return color;
    }

    /**
     * Gets the text effects.
     *
     * @return The text effects.
     */
    public int getTextEffects() {
	return effects;
    }

    /**
     * Gets the compressed message.
     *
     * @return The compressed message.
     */
    public byte[] getCompressedMessage() {
	return compressedMessage;
    }

}
