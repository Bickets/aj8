package buttons;

import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.GameCharacterFields;
import org.apollo.game.model.Player;

@SubscribesTo(ButtonActionEvent.class)
@SuppressWarnings("all")
public class WithdrawFromBankButton implements EventSubscriber<ButtonActionEvent> {
  public void subscribe(final ButtonActionEvent event) {
    Player _player = event.getPlayer();
    GameCharacterFields _fields = _player.getFields();
    _fields.setWithdrawingNotes(true);
  }
  
  public boolean test(final ButtonActionEvent event) {
    int _id = event.getId();
    return (_id == 5386);
  }
}
