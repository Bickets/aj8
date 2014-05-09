package buttons;

import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.model.GameCharacterFields;
import org.apollo.game.model.Player;

@SuppressWarnings("all")
public class WithdrawFromBankButton extends ButtonClickListener {
  public WithdrawFromBankButton() {
    super(5386);
  }
  
  public void handle(final int id, final Player player) {
    GameCharacterFields _fields = player.getFields();
    _fields.setWithdrawingNotes(true);
  }
}
