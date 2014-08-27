package commands;

import com.google.common.base.Objects;
import common.Plugin;
import org.apollo.game.command.CommandEvent;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;
import org.apollo.game.model.def.GameObjectDefinition;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inter.InterfaceSet;
import org.apollo.game.model.inter.bank.BankUtils;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.pf.AStarPathFinder;
import org.apollo.game.model.pf.Path;
import org.apollo.game.msg.impl.GameObjectMessage;

@SubscribesTo(CommandEvent.class)
@SuppressWarnings("all")
public class CommandPlugin extends Plugin implements EventSubscriber<CommandEvent> {
  public void subscribe(final CommandEvent event) {
    final String[] args = event.getArguments();
    final Player plr = event.getPlayer();
    String _name = event.getName();
    String _lowerCase = _name.toLowerCase();
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_lowerCase, "close")) {
        _matched=true;
        InterfaceSet _interfaceSet = plr.getInterfaceSet();
        _interfaceSet.close();
      }
    }
    if (!_matched) {
      if (Objects.equal(_lowerCase, "pickup")) {
        _matched=true;
        int _length = args.length;
        boolean _lessThan = (_length < 1);
        if (_lessThan) {
          plr.sendMessage("Syntax is ::pickup [id] [amount=1]");
          return;
        }
        String _get = args[0];
        final Integer id = this.toInt(_get);
        int amount = 1;
        int _length_1 = args.length;
        boolean _equals = (_length_1 == 2);
        if (_equals) {
          Object _get_1 = args[1];
          boolean _equals_1 = "max".equals(_get_1);
          if (_equals_1) {
            amount = Integer.MAX_VALUE;
          } else {
            String _get_2 = args[1];
            String _lowerCase_1 = _get_2.toLowerCase();
            args[1] = _lowerCase_1;
            String _get_3 = args[1];
            String _replace = _get_3.replace("k", "000");
            args[1] = _replace;
            String _get_4 = args[1];
            String _replace_1 = _get_4.replace("m", "000000");
            args[1] = _replace_1;
            String _get_5 = args[1];
            String _replace_2 = _get_5.replace("b", "000000000");
            args[1] = _replace_2;
            String _get_6 = args[1];
            Integer _int = this.toInt(_get_6);
            amount = (_int).intValue();
          }
        }
        boolean _or = false;
        boolean _or_1 = false;
        if ((((id).intValue() < 0) || (amount < 0))) {
          _or_1 = true;
        } else {
          int _count = ItemDefinition.count();
          boolean _greaterThan = ((id).intValue() > _count);
          _or_1 = _greaterThan;
        }
        if (_or_1) {
          _or = true;
        } else {
          _or = (amount > Integer.MAX_VALUE);
        }
        if (_or) {
          plr.sendMessage((((("Item [id=" + id) + ", amount=") + Integer.valueOf(amount)) + "] "));
          return;
        }
        Inventory _inventory = plr.getInventory();
        _inventory.add((id).intValue(), amount);
      }
    }
    if (!_matched) {
      if (Objects.equal(_lowerCase, "bank")) {
        _matched=true;
        BankUtils.openBank(plr);
      }
    }
    if (!_matched) {
      if (Objects.equal(_lowerCase, "tele")) {
        _matched=true;
        boolean _or_2 = false;
        int _length_2 = args.length;
        boolean _lessThan_1 = (_length_2 < 2);
        if (_lessThan_1) {
          _or_2 = true;
        } else {
          int _length_3 = args.length;
          boolean _greaterThan_1 = (_length_3 > 3);
          _or_2 = _greaterThan_1;
        }
        if (_or_2) {
          plr.sendMessage("Syntax is: ::tele [x] [y] [z=0]");
          return;
        }
        String _get_7 = args[0];
        final Integer x = this.toInt(_get_7);
        String _get_8 = args[1];
        final Integer y = this.toInt(_get_8);
        Integer _xifexpression = null;
        int _length_4 = args.length;
        boolean _equals_2 = (_length_4 == 3);
        if (_equals_2) {
          String _get_9 = args[2];
          _xifexpression = this.toInt(_get_9);
        } else {
          _xifexpression = Integer.valueOf(0);
        }
        final Integer z = _xifexpression;
        if (((((x).intValue() < 1) || ((y).intValue() < 1)) || ((z).intValue() < 0))) {
          plr.sendMessage((((((("Position: [x=" + x) + ", y=") + y) + ", z=") + z) + "] is not valid."));
          return;
        }
        Position _position = new Position((x).intValue(), (y).intValue(), (z).intValue());
        plr.teleport(_position);
      }
    }
    if (!_matched) {
      if (Objects.equal(_lowerCase, "obj")) {
        _matched=true;
        int _length_5 = args.length;
        boolean _notEquals = (_length_5 != 1);
        if (_notEquals) {
          plr.sendMessage("Syntax is: ::obj [id]");
          return;
        }
        String _get_10 = args[0];
        final Integer id_1 = this.toInt(_get_10);
        boolean _or_3 = false;
        if (((id_1).intValue() < 0)) {
          _or_3 = true;
        } else {
          int _count_1 = GameObjectDefinition.count();
          boolean _greaterThan_2 = ((id_1).intValue() > _count_1);
          _or_3 = _greaterThan_2;
        }
        if (_or_3) {
          plr.sendMessage((("Object: [id=" + id_1) + "] is not valid."));
          return;
        }
        Position _position_1 = plr.getPosition();
        final GameObject object = new GameObject((id_1).intValue(), _position_1);
        GameObjectMessage _gameObjectMessage = new GameObjectMessage(object, 0);
        plr.send(_gameObjectMessage);
      }
    }
    if (!_matched) {
      if (Objects.equal(_lowerCase, "walk")) {
        _matched=true;
        int _length_6 = args.length;
        boolean _notEquals_1 = (_length_6 != 2);
        if (_notEquals_1) {
          plr.sendMessage("Syntax is: ::walk [x, y]");
          return;
        }
        String _get_11 = args[0];
        final Integer x_1 = this.toInt(_get_11);
        String _get_12 = args[1];
        final Integer y_1 = this.toInt(_get_12);
        final AStarPathFinder finder = new AStarPathFinder();
        final Path path = finder.find(plr, (x_1).intValue(), (y_1).intValue());
        WalkingQueue _walkingQueue = plr.getWalkingQueue();
        _walkingQueue.clear();
        boolean _notEquals_2 = (!Objects.equal(path, null));
        if (_notEquals_2) {
          WalkingQueue _walkingQueue_1 = plr.getWalkingQueue();
          Position _poll = path.poll();
          _walkingQueue_1.addFirstStep(_poll);
          boolean _isEmpty = path.isEmpty();
          boolean _not = (!_isEmpty);
          boolean _while = _not;
          while (_while) {
            WalkingQueue _walkingQueue_2 = plr.getWalkingQueue();
            Position _poll_1 = path.poll();
            _walkingQueue_2.addStep(_poll_1);
            boolean _isEmpty_1 = path.isEmpty();
            boolean _not_1 = (!_isEmpty_1);
            _while = _not_1;
          }
        }
      }
    }
  }
}
