package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.ItemActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener;
import org.apollo.game.model.inter.bank.BankUtils;
import org.apollo.game.model.inter.bank.BankWithdrawEnterAmountListener;

/**
 * An event handler which handles item actions.
 * 
 * @author Graham
 */
@HandlesEvent(ItemActionEvent.class)
public final class ItemActionEventHandler extends EventHandler<ItemActionEvent> {

    /**
     * The world
     */
    private final World world;

    /**
     * Constructs a new {@link ItemActionEventHandler}.
     * 
     * @param world The world.
     */
    public ItemActionEventHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ItemActionEvent event) {
	world.getInteractionHandler().dispatch(event.getId(), event.getSlot(), event.getOption(), event.getInterfaceId(), player);

	if (event.getInterfaceId() == BankConstants.SIDEBAR_INVENTORY_ID) {
	    deposit(player, event);
	} else if (event.getInterfaceId() == BankConstants.BANK_INVENTORY_ID) {
	    withdraw(player, event);
	}
    }

    /**
     * Handles a withdraw action.
     * 
     * @param ctx The event handler context.
     * @param player The player.
     * @param event The event.
     */
    private void withdraw(Player player, ItemActionEvent event) {
	int amount = optionToAmount(event.getOption());
	if (amount == -1) {
	    player.getInterfaceSet().openEnterAmountDialog(new BankWithdrawEnterAmountListener(player, event.getSlot(), event.getId()));
	} else {
	    if (!BankUtils.withdraw(player, event.getSlot(), event.getId(), amount)) {
		return;
	    }
	}
    }

    /**
     * Handles a deposit action.
     * 
     * @param ctx The event handler context.
     * @param player The player.
     * @param event The event.
     */
    private void deposit(Player player, ItemActionEvent event) {
	int amount = optionToAmount(event.getOption());
	if (amount == -1) {
	    player.getInterfaceSet().openEnterAmountDialog(new BankDepositEnterAmountListener(player, event.getSlot(), event.getId()));
	} else {
	    if (!BankUtils.deposit(player, event.getSlot(), event.getId(), amount)) {
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
    private static int optionToAmount(int option) {
	switch (option) {
	case 1:
	    return 1;
	case 2:
	    return 5;
	case 3:
	    return 10;
	case 4:
	    return Integer.MAX_VALUE;
	case 5:
	    return -1;
	}
	throw new IllegalArgumentException();
    }

}
