package buttons;

import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.Player;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class LogoutButton implements EventSubscriber<ButtonActionEvent> {
  public void subscribe(final ButtonActionEvent event) {
    int _id = event.getId();
    InputOutput.<Integer>println(Integer.valueOf(_id));
    int _id_1 = event.getId();
    boolean _equals = (_id_1 == 2458);
    if (_equals) {
      Player _player = event.getPlayer();
      _player.logout();
    }
  }
}
