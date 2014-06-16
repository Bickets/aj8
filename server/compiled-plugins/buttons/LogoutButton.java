package buttons;

import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.model.Player;

@SuppressWarnings("all")
public class LogoutButton extends ButtonClickListener {
    public LogoutButton() {
	super(2458);
    }

    public void handle(final int id, final Player player) {
	player.logout();
    }
}
