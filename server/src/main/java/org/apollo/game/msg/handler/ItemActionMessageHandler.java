package org.apollo.game.msg.handler;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener;
import org.apollo.game.model.inter.bank.BankUtils;
import org.apollo.game.model.inter.bank.BankWithdrawEnterAmountListener;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ItemActionMessage;

/**
 * A message handler which handles item actions.
 * 
 * @author Graham
 */
@HandlesMessage(ItemActionMessage.class)
public final class ItemActionMessageHandler extends MessageHandler<ItemActionMessage> {

    /**
     * The world
     */
    private final World world;

    /**
     * Constructs a new {@link ItemActionMessageHandler}.
     * 
     * @param world The world.
     */
    public ItemActionMessageHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ItemActionMessage message) {
	world.getInteractionHandler().dispatch(message.getId(), message.getSlot(), message.getOption(), message.getInterfaceId(), player);

	if (message.getInterfaceId() == BankConstants.SIDEBAR_INVENTORY_ID) {
	    deposit(player, message);
	} else if (message.getInterfaceId() == BankConstants.BANK_INVENTORY_ID) {
	    withdraw(player, message);
	}
    }

    /**
     * Handles a withdraw action.
     * 
     * @param player The player.
     * @param message The message.
     */
    private void withdraw(Player player, ItemActionMessage message) {
	int amount = optionToAmount(message.getOption());
	if (amount == -1) {
	    player.getInterfaceSet().openEnterAmountDialog(new BankWithdrawEnterAmountListener(player, message.getSlot(), message.getId()));
	} else {
	    if (!BankUtils.withdraw(player, message.getSlot(), message.getId(), amount)) {
		return;
	    }
	}
    }

    /**
     * Handles a deposit action.
     * 
     * @param player The player.
     * @param message The message.
     */
    private void deposit(Player player, ItemActionMessage message) {
	int amount = optionToAmount(message.getOption());
	if (amount == -1) {
	    player.getInterfaceSet().openEnterAmountDialog(new BankDepositEnterAmountListener(player, message.getSlot(), message.getId()));
	} else {
	    if (!BankUtils.deposit(player, message.getSlot(), message.getId(), amount)) {
		return;
	    }
	}
    }

    /**
     * Converts an option to an amount.
     * 
     * @param option The option.
     * @return The amount.
     * @throws IllegalArgumentException if the option is not legal.
     */
    private static int optionToAmount(InterfaceOption option) {
	switch (option) {
	case OPTION_ONE:
	    return 1;
	case OPTION_TWO:
	    return 5;
	case OPTION_THREE:
	    return 10;
	case OPTION_FOUR:
	    return Integer.MAX_VALUE;
	case OPTION_FIVE:
	    return -1;
	}
	throw new IllegalArgumentException();
    }

}
