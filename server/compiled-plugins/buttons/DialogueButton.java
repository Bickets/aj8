package buttons;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.model.InterfaceSet;
import org.apollo.game.model.InterfaceType;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.dialog.DialogueOption;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class DialogueButton extends ButtonClickListener {
  public static ArrayList<Integer> buildIds() {
    final ArrayList<Integer> ids = CollectionLiterals.<Integer>newArrayList();
    final DialogueOption[] vals = DialogueOption.values();
    final Consumer<DialogueOption> _function = new Consumer<DialogueOption>() {
      public void accept(final DialogueOption it) {
        int[] _ids = it.getIds();
        final Procedure1<Integer> _function = new Procedure1<Integer>() {
          public void apply(final Integer it) {
            ids.add(it);
          }
        };
        IterableExtensions.<Integer>forEach(((Iterable<Integer>)Conversions.doWrapArray(_ids)), _function);
      }
    };
    ((List<DialogueOption>)Conversions.doWrapArray(vals)).forEach(_function);
    return ids;
  }
  
  public DialogueButton() {
    super(((int[])Conversions.unwrapArray(DialogueButton.buildIds(), int.class)));
  }
  
  public void handle(final int id, final Player player) {
    InterfaceSet _interfaceSet = player.getInterfaceSet();
    boolean _contains = _interfaceSet.contains(InterfaceType.DIALOGUE);
    if (_contains) {
      final DialogueOption option = DialogueOption.fromId(id);
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(option, null));
      if (!_notEquals) {
        _and = false;
      } else {
        InterfaceSet _interfaceSet_1 = player.getInterfaceSet();
        boolean _optionClicked = _interfaceSet_1.optionClicked(option);
        _and = _optionClicked;
      }
      final boolean success = _and;
      if ((!success)) {
        InterfaceSet _interfaceSet_2 = player.getInterfaceSet();
        _interfaceSet_2.close();
        return;
      }
    }
  }
}
