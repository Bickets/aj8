package items;

import com.google.common.base.Objects;
import common.Plugin;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

@SubscribesTo(ItemActionEvent.class)
@SuppressWarnings("all")
public class RemoveEquipment extends Plugin implements EventSubscriber<ItemActionEvent> {
  public void remove(final Player player, final int id, final int slot) {
    final Inventory inventory = player.getInventory();
    final Inventory equipment = player.getEquipment();
    final Item item = equipment.get(slot);
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
    boolean _and = false;
    boolean _contains = inventory.contains(id);
    if (!_contains) {
      _and = false;
    } else {
      ItemDefinition _definition = item.getDefinition();
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
    inventory.stopFiringEvents();
    equipment.stopFiringEvents();
    try {
      equipment.set(slot, null);
      int _id_1 = item.getId();
      int _amount = item.getAmount();
      inventory.add(_id_1, _amount);
    } finally {
      inventory.startFiringEvents();
      equipment.startFiringEvents();
    }
    inventory.forceRefresh(slot);
    equipment.forceRefresh(slot);
  }
  
  public void subscribe(final ItemActionEvent event) {
    Player _player = event.getPlayer();
    int _id = event.getId();
    int _slot = event.getSlot();
    this.remove(_player, _id, _slot);
  }
  
  public boolean test(final ItemActionEvent event) {
    boolean _xblockexpression = false;
    {
      Player _player = event.getPlayer();
      this.closeInterfaces(_player);
      int _interfaceId = event.getInterfaceId();
      _xblockexpression = (_interfaceId == SynchronizationInventoryListener.EQUIPMENT_ID);
    }
    return _xblockexpression;
  }
}
