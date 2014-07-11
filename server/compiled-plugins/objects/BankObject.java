package objects;

import com.google.common.base.Objects;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.InterfaceConstants;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.bank.BankAction;

@SuppressWarnings("all")
public class BankObject implements EventSubscriber<ObjectActionEvent> {
  public void subscribe(final ObjectActionEvent event) {
    int _id = event.getId();
    boolean _notEquals = (_id != 2213);
    if (_notEquals) {
      return;
    }
    InterfaceConstants.InterfaceOption _option = event.getOption();
    boolean _equals = Objects.equal(_option, InterfaceConstants.InterfaceOption.OPTION_ONE);
    if (_equals) {
      Player _player = event.getPlayer();
      Player _player_1 = event.getPlayer();
      Position _position = event.getPosition();
      BankAction _bankAction = new BankAction(_player_1, _position);
      _player.startAction(_bankAction);
    }
  }
}
