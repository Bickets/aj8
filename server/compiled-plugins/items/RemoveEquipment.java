package items;

import com.google.common.base.Objects;
import org.apollo.game.common.Interfaces;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

@SuppressWarnings("all")
public class RemoveEquipment implements EventSubscriber<ItemActionEvent> {
  public void remove(final Player player, final int id, final int slot) {
    final Inventory inventory = player.getInventory();
    final Inventory equipment = player.getEquipment();
    boolean _and = false;
    boolean _contains = inventory.contains(id);
    if (!_contains) {
      _and = false;
    } else {
      Item _get = inventory.get(slot);
      ItemDefinition _definition = _get.getDefinition();
      boolean _isStackable = _definition.isStackable();
      _and = _isStackable;
    }
    boolean hasRoomForStackable = _and;
    boolean _and_1 = false;
    int _freeSlots = inventory.freeSlots();
    boolean _lessThan = (_freeSlots < 1);
    if (!_lessThan) {
      _and_1 = false;
    } else {
      _and_1 = (!hasRoomForStackable);
    }
    if (_and_1) {
      inventory.forceCapacityExceeded();
      return;
    }
    boolean _or = false;
    if ((slot < 0)) {
      _or = true;
    } else {
      int _capacity = equipment.capacity();
      boolean _greaterEqualsThan = (slot >= _capacity);
      _or = _greaterEqualsThan;
    }
    if (_or) {
      return;
    }
    final Item item = equipment.get(slot);
    boolean _or_1 = false;
    boolean _equals = Objects.equal(item, null);
    if (_equals) {
      _or_1 = true;
    } else {
      int _id = item.getId();
      boolean _notEquals = (_id != id);
      _or_1 = _notEquals;
    }
    if (_or_1) {
      return;
    }
    boolean removed = true;
    inventory.stopFiringEvents();
    equipment.stopFiringEvents();
    try {
      equipment.set(slot, null);
      Item copy = item;
      int _id_1 = item.getId();
      int _amount = item.getAmount();
      inventory.add(_id_1, _amount);
      boolean _notEquals_1 = (!Objects.equal(copy, null));
      if (_notEquals_1) {
        removed = false;
        equipment.set(slot, copy);
      }
    } finally {
      inventory.startFiringEvents();
      equipment.startFiringEvents();
    }
    if (removed) {
      inventory.forceRefresh(slot);
      equipment.forceRefresh(slot);
    } else {
      inventory.forceCapacityExceeded();
    }
  }
  
  public void subscribe(final ItemActionEvent event) {
    Player _player = event.getPlayer();
    int _id = event.getId();
    int _slot = event.getSlot();
    this.remove(_player, _id, _slot);
  }
  
  public boolean test(final ItemActionEvent event) {
    boolean _and = false;
    int _interfaceId = event.getInterfaceId();
    boolean _equals = (_interfaceId == SynchronizationInventoryListener.EQUIPMENT_ID);
    if (!_equals) {
      _and = false;
    } else {
      Interfaces.InterfaceOption _option = event.getOption();
      boolean _equals_1 = Objects.equal(_option, Interfaces.InterfaceOption.OPTION_ONE);
      _and = _equals_1;
    }
    return _and;
  }
}
