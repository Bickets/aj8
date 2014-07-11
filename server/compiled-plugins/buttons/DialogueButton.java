package buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.InterfaceSet;
import org.apollo.game.model.InterfaceType;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.dialog.DialogueOption;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class DialogueButton implements EventSubscriber<ButtonActionEvent> {
  private final static ArrayList<Integer> ids = DialogueButton.buildIds();
  
  public void subscribe(final ButtonActionEvent event) {
    final Consumer<Integer> _function = new Consumer<Integer>() {
      public void accept(final Integer it) {
        int _id = event.getId();
        boolean _equals = ((it).intValue() == _id);
        if (_equals) {
          Player player = event.getPlayer();
          InterfaceSet _interfaceSet = player.getInterfaceSet();
          boolean _contains = _interfaceSet.contains(InterfaceType.DIALOGUE);
          if (_contains) {
            DialogueOption _fromId = DialogueOption.fromId((it).intValue());
            final DialogueOption option = Objects.<DialogueOption>requireNonNull(_fromId);
            InterfaceSet _interfaceSet_1 = player.getInterfaceSet();
            final boolean success = _interfaceSet_1.optionClicked(option);
            if (success) {
              InterfaceSet _interfaceSet_2 = player.getInterfaceSet();
              _interfaceSet_2.continueRequested();
            }
          }
        }
      }
    };
    DialogueButton.ids.forEach(_function);
  }
  
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
}
