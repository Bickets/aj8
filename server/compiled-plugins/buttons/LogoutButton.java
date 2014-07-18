package buttons;

import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.Player;

@SuppressWarnings("all")
public class LogoutButton implements EventSubscriber<ButtonActionEvent> {
  public void subscribe(final ButtonActionEvent event) {
    Player _player = event.getPlayer();
    _player.logout();
  }
  
  public boolean test(final ButtonActionEvent event) {
    int _id = event.getId();
    return (_id == 2458);
  }
}
