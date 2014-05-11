package objects;

import com.google.common.base.Objects;
import org.apollo.game.interact.ObjectActionListener;
import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.bank.BankAction;

@SuppressWarnings("all")
public class BankObject extends ObjectActionListener {
  public BankObject() {
    super(2213);
  }
  
  public void handle(final int id, final InterfaceOption option, final Player player, final Position position) {
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(option,InterfaceOption.OPTION_ONE)) {
        _matched=true;
        BankAction _bankAction = new BankAction(player, position);
        player.startAction(_bankAction);
      }
    }
  }
}
