package org.apollo.game.model.inter;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.dialog.DialogueListener;
import org.apollo.game.model.inter.dialog.DialogueOption;
import org.apollo.game.msg.impl.CloseInterfaceMessage;
import org.apollo.game.msg.impl.EnterAmountMessage;
import org.apollo.game.msg.impl.OpenDialogueInterfaceMessage;
import org.apollo.game.msg.impl.OpenInterfaceMessage;
import org.apollo.game.msg.impl.OpenInterfaceSidebarMessage;

/**
 * Represents the set of interfaces the player has open.
 * <p>
 * This class manages all six distinct types of interface (the last two are not
 * present on 317 servers).
 * <p>
 * <ul>
 * <li><strong>Windows:</strong> the ones people mostly associate with the word
 * interfaces. Things like your bank, the wildy warning screen, the trade
 * screen, etc.</li>
 * <li><strong>Overlays:</strong> display in the same place as windows, but
 * don't prevent you from moving. For example, the wilderness level indicator.</li>
 * <li><strong>Dialogues:</strong> interfaces which are displayed over the chat
 * box.</li>
 * <li><strong>Sidebars:</strong> an interface which displays over the inventory
 * area.</li>
 * <li><strong>Fullscreen windows:</strong> a window which displays over the
 * whole screen e.g. the 377 welcome screen.</li>
 * <li><strong>Fullscreen background:</strong> an interface displayed behind the
 * fullscreen window, typically a blank, black screen.</li>
 * </ul>
 *
 * @author Graham
 */
public final class InterfaceSet {

	/**
	 * The player whose interfaces are being managed.
	 */
	private final Player player; // TODO: maybe switch to a listener system like
	// the inventory?

	/**
	 * A map of open interfaces.
	 */
	private final Map<InterfaceType, Integer> interfaces = new HashMap<InterfaceType, Integer>();

	/**
	 * The current listener.
	 */
	private InterfaceListener listener;

	/**
	 * The current enter amount listener.
	 */
	private EnterAmountListener amountListener;

	/**
	 * The current chat box dialogue listener.
	 */
	private DialogueListener dialogueListener;

	/**
	 * Creates an interface set.
	 *
	 * @param player The player.
	 */
	public InterfaceSet(Player player) {
		this.player = player;
	}

	/**
	 * Closes the current open interface(s).
	 */
	public void close() {
		closeAndNotify();

		player.send(new CloseInterfaceMessage());
	}

	/**
	 * Sent by the client when it has closed an interface.
	 */
	public void interfaceClosed() {
		closeAndNotify();
	}

	/**
	 * Opens a dialogue listener.
	 *
	 * @param listener The dialogue listener to open.
	 */
	public void openDialogue(DialogueListener listener) {
		closeAndNotify();

		dialogueListener = listener;
		this.listener = listener;

		int dialogueId = listener.execute(player);

		interfaces.put(InterfaceType.DIALOGUE, dialogueId);
		player.send(new OpenDialogueInterfaceMessage(dialogueId));
	}

	/**
	 * Fires the dialogue listener option clicked event.
	 *
	 * @param option The dialogue option clicked.
	 * @return {@code true} if and only if the event fired successfully,
	 *         otherwise {@code false}.
	 */
	public boolean optionClicked(DialogueOption option) {
		if (dialogueListener != null) {
			return dialogueListener.optionClicked(option);
		}
		return false;
	}

	/**
	 * Called when the player has clicked the "Click here to continue" button on
	 * a dialogue.
	 */
	public void continueRequested() {
		if (dialogueListener == null) {
			close();
			return;
		}

		// It's safe to execute the continue event.
		dialogueListener.continued();

		DialogueListener next = dialogueListener.next();
		if (next == null) {
			close();
			return;
		}

		// It's safe to open up the next dialogue.
		openDialogue(next);
	}

	/**
	 * Opens the enter amount dialog.
	 *
	 * @param listener The enter amount listener.
	 */
	public void openEnterAmountDialog(EnterAmountListener listener) {
		amountListener = listener;

		player.send(new EnterAmountMessage());
	}

	/**
	 * Opens a window and inventory sidebar.
	 *
	 * @param windowId The window's id.
	 * @param sidebarId The sidebar's id.
	 */
	public void openWindowWithSidebar(int windowId, int sidebarId) {
		openWindowWithSidebar(null, windowId, sidebarId);
	}

	/**
	 * Opens a window and inventory sidebar with the specified listener.
	 *
	 * @param listener The listener for this interface.
	 * @param windowId The window's id.
	 * @param sidebarId The sidebar's id.
	 */
	public void openWindowWithSidebar(InterfaceListener listener, int windowId, int sidebarId) {
		closeAndNotify();
		this.listener = listener;

		interfaces.put(InterfaceType.WINDOW, windowId);
		interfaces.put(InterfaceType.SIDEBAR, sidebarId);

		player.send(new OpenInterfaceSidebarMessage(windowId, sidebarId));
	}

	/**
	 * Opens a window.
	 *
	 * @param windowId The window's id.
	 */
	public void openWindow(int windowId) {
		openWindow(null, windowId);
	}

	/**
	 * Opens a window with the specified listener.
	 *
	 * @param listener The listener for this interface.
	 * @param windowId The window's id.
	 */
	public void openWindow(InterfaceListener listener, int windowId) {
		closeAndNotify();
		this.listener = listener;

		interfaces.put(InterfaceType.WINDOW, windowId);

		player.send(new OpenInterfaceMessage(windowId));
	}

	/**
	 * Checks if this interface sets contains the specified interface.
	 *
	 * @param id The interface's id.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean contains(int id) {
		return interfaces.containsValue(id);
	}

	/**
	 * Returns whether whether or not this interface set contains a window and a
	 * sidebar interface open at the same time.
	 *
	 * @param windowId The window's interface id.
	 * @param sidebarId The sidebar's interface id.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean contains(int windowId, int sidebarId) {
		return contains(windowId) && contains(sidebarId);
	}

	/**
	 * Checks if this interface set contains the specified interface type.
	 *
	 * @param type The interface's type.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean contains(InterfaceType type) {
		return interfaces.containsKey(type);
	}

	/**
	 * Removes this interface sets {@code #listener}.
	 */
	public void removeListener() {
		listener = null;
	}

	/**
	 * An internal method for closing the interface, notifying the listener if
	 * appropriate, but not sending any events.
	 */
	private void closeAndNotify() {
		amountListener = null;
		dialogueListener = null;

		interfaces.clear();
		if (listener != null) {
			listener.close();
			listener = null;
		}
	}

	/**
	 * Called when the client has entered the specified amount. Notifies the
	 * current listener.
	 *
	 * @param amount The amount.
	 */
	public void enteredAmount(int amount) {
		if (amountListener != null) {
			amountListener.amountEntered(amount);
			amountListener = null;
		}
	}

	/**
	 * Returns {@code true} if and only if 1 or more of any interface is open
	 * otherwise {@code false}.
	 */
	public boolean isOpen() {
		return interfaces.size() > 0;
	}

}