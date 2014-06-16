package objects;

import org.apollo.game.interact.ObjectActionListener;
import org.apollo.game.model.InterfaceConstants;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.bank.BankAction;

@SuppressWarnings("all")
public class BankObject extends ObjectActionListener {
    public BankObject() {
	super(2213);
    }

    public void handle(final int id, final InterfaceConstants.InterfaceOption option, final Player player, final Position position) {
	if (option != null) {
	    switch (option) {
	    case OPTION_ONE:
		BankAction _bankAction = new BankAction(player, position);
		player.startAction(_bankAction);
		break;
	    default:
		throw new UnsupportedOperationException(("Unhandled bank option: " + option));
	    }
	} else {
	    throw new UnsupportedOperationException(("Unhandled bank option: " + option));
	}
    }
}
