package objects;

import org.apollo.game.interact.ObjectActionEventHandler;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.bank.BankAction;

@SuppressWarnings("all")
public class BankObject extends ObjectActionEventHandler {
  public BankObject() {
    super(2213);
  }
  
  public void handle(final int id, final int option, final Player player, final Position position) {
    switch (option) {
      case 2:
        BankAction _bankAction = new BankAction(player, position);
        player.startAction(_bankAction);
        break;
    }
  }
}
