package objects;

import com.google.common.base.Objects;
import org.apollo.game.common.Interfaces;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.bank.BankAction;

@SuppressWarnings("all")
public class BankObject implements EventSubscriber<ObjectActionEvent> {
  public void subscribe(final ObjectActionEvent event) {
    Player _player = event.getPlayer();
    Player _player_1 = event.getPlayer();
    Position _position = event.getPosition();
    BankAction _bankAction = new BankAction(_player_1, _position);
    _player.startAction(_bankAction);
  }
  
  public boolean test(final ObjectActionEvent event) {
    boolean _and = false;
    int _id = event.getId();
    boolean _equals = (_id == 2213);
    if (!_equals) {
      _and = false;
    } else {
      Interfaces.InterfaceOption _option = event.getOption();
      boolean _equals_1 = Objects.equal(_option, Interfaces.InterfaceOption.OPTION_ONE);
      _and = _equals_1;
    }
    return _and;
  }
}
