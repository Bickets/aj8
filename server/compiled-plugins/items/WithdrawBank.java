package items;

import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.InterfaceSet;
import org.apollo.game.model.Interfaces;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inter.bank.BankUtils;
import org.apollo.game.model.inter.bank.BankWithdrawEnterAmountListener;

@SuppressWarnings("all")
public class WithdrawBank implements EventSubscriber<ItemActionEvent> {
  public void subscribe(final ItemActionEvent event) {
    Interfaces.InterfaceOption _option = event.getOption();
    int amount = Interfaces.InterfaceOption.optionToAmount(_option);
    if ((amount == (-1))) {
      Player _player = event.getPlayer();
      InterfaceSet _interfaceSet = _player.getInterfaceSet();
      Player _player_1 = event.getPlayer();
      int _slot = event.getSlot();
      int _id = event.getId();
      BankWithdrawEnterAmountListener _bankWithdrawEnterAmountListener = new BankWithdrawEnterAmountListener(_player_1, _slot, _id);
      _interfaceSet.openEnterAmountDialog(_bankWithdrawEnterAmountListener);
    } else {
      Player _player_2 = event.getPlayer();
      int _slot_1 = event.getSlot();
      int _id_1 = event.getId();
      boolean _withdraw = BankUtils.withdraw(_player_2, _slot_1, _id_1, amount);
      boolean _not = (!_withdraw);
      if (_not) {
        return;
      }
    }
  }
  
  public boolean test(final ItemActionEvent event) {
    int _interfaceId = event.getInterfaceId();
    return (_interfaceId == BankConstants.SIDEBAR_INVENTORY_ID);
  }
}
