package buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceSet;
import org.apollo.game.model.inter.InterfaceType;
import org.apollo.game.model.inter.dialog.DialogueOption;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SubscribesTo(ButtonActionEvent.class)
@SuppressWarnings("all")
public class DialogueButton implements EventSubscriber<ButtonActionEvent> {
  private final static ArrayList<Integer> ids = DialogueButton.buildIds();
  
  public void subscribe(final ButtonActionEvent event) {
    final Player player = event.getPlayer();
    int _id = event.getId();
    final DialogueOption option = DialogueOption.fromId(_id);
    InterfaceSet _interfaceSet = player.getInterfaceSet();
    final boolean success = _interfaceSet.optionClicked(option);
    if (success) {
      InterfaceSet _interfaceSet_1 = player.getInterfaceSet();
      _interfaceSet_1.continueRequested();
    }
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
  
  public boolean test(final ButtonActionEvent event) {
    boolean _xblockexpression = false;
    {
      final Player player = event.getPlayer();
      boolean hasId = false;
      for (final Integer id : DialogueButton.ids) {
        int _id = event.getId();
        boolean _equals = ((id).intValue() == _id);
        if (_equals) {
          hasId = true;
        }
      }
      boolean _and = false;
      InterfaceSet _interfaceSet = player.getInterfaceSet();
      boolean _contains = _interfaceSet.contains(InterfaceType.DIALOGUE);
      if (!_contains) {
        _and = false;
      } else {
        _and = hasId;
      }
      _xblockexpression = _and;
    }
    return _xblockexpression;
  }
}
