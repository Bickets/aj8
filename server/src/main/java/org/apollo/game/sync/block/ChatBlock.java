package org.apollo.game.sync.block;

import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.msg.impl.ChatMessage;

/**
 * The chat {@link SynchronizationBlock}.
 *
 * @author Graham
 */
public final class ChatBlock extends SynchronizationBlock {

    /**
     * The privilege level.
     */
    private final PrivilegeLevel privilegeLevel;

    /**
     * The chat message.
     */
    private final ChatMessage chatMessage;

    /**
     * Constructs a new {@link ChatBlock}.
     *
     * @param privilegeLevel The privilege level.
     * @param chatMessage The messages content.
     */
    protected ChatBlock(PrivilegeLevel privilegeLevel, ChatMessage chatMessage) {
	this.privilegeLevel = privilegeLevel;
	this.chatMessage = chatMessage;
    }

    /**
     * Gets the privilege level of the player who said the message.
     *
     * @return The privilege level.
     */
    public PrivilegeLevel getPrivilegeLevel() {
	return privilegeLevel;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
	return chatMessage.getMessage();
    }

    /**
     * Gets the text color.
     *
     * @return The text color.
     */
    public int getTextColor() {
	return chatMessage.getTextColor();
    }

    /**
     * Gets the text effects.
     *
     * @return The text effects.
     */
    public int getTextEffects() {
	return chatMessage.getTextEffects();
    }

    /**
     * Gets the compressed message.
     *
     * @return The compressed message.
     */
    public byte[] getCompressedMessage() {
	return chatMessage.getCompressedMessage();
    }

}