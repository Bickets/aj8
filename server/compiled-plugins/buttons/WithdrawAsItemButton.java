package buttons;

import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.model.GameCharacterFields;
import org.apollo.game.model.Player;

@SuppressWarnings("all")
public class WithdrawAsItemButton extends ButtonClickListener {
  public WithdrawAsItemButton() {
    super(5387);
  }
  
  public void handle(final int id, final Player player) {
    GameCharacterFields _fields = player.getFields();
    _fields.setWithdrawingNotes(false);
  }
}
