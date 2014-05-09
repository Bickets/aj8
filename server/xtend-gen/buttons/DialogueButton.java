package buttons;

import com.google.common.base.Objects;
import java.util.ArrayList;
import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.model.InterfaceSet;
import org.apollo.game.model.InterfaceType;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.dialog.DialogueOption;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class DialogueButton extends ButtonClickListener {
  public static ArrayList<Integer> buildIds() {
    ArrayList<Integer> ids = CollectionLiterals.<Integer>newArrayList();
    DialogueOption[] _values = DialogueOption.values();
    for (final DialogueOption option : _values) {
      int[] _ids = option.getIds();
      for (final int id : _ids) {
        ids.add(Integer.valueOf(id));
      }
    }
    return ids;
  }
  
  public DialogueButton() {
    super(new Function0<int[]>() {
      public int[] apply() {
        ArrayList<Integer> _buildIds = DialogueButton.buildIds();
        return ((int[])Conversions.unwrapArray(_buildIds, int.class));
      }
    }.apply());
  }
  
  public void handle(final int id, final Player player) {
    InterfaceSet _interfaceSet = player.getInterfaceSet();
    boolean _contains = _interfaceSet.contains(InterfaceType.DIALOGUE);
    if (_contains) {
      DialogueOption option = DialogueOption.fromId(id);
      boolean _or = false;
      boolean _equals = Objects.equal(option, null);
      if (_equals) {
        _or = true;
      } else {
        InterfaceSet _interfaceSet_1 = player.getInterfaceSet();
        boolean _optionClicked = _interfaceSet_1.optionClicked(option);
        boolean _not = (!_optionClicked);
        _or = (_equals || _not);
      }
      if (_or) {
        InterfaceSet _interfaceSet_2 = player.getInterfaceSet();
        _interfaceSet_2.close();
        return;
      }
    }
  }
}
