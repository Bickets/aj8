package items;

import com.google.common.base.Objects;
import common.Plugin;
import java.util.List;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.def.EquipmentDefinition;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inv.SynchronizationInventoryListener;
import org.eclipse.xtext.xbase.lib.Conversions;

@SubscribesTo(ItemActionEvent.class)
@SuppressWarnings("all")
public class WearEquipment extends Plugin implements EventSubscriber<ItemActionEvent> {
  public void wear(final Player player, final int id, final int slot) {
    Inventory _inventory = player.getInventory();
    final Item item = _inventory.get(slot);
    boolean _or = false;
    boolean _equals = Objects.equal(item, null);
    if (_equals) {
      _or = true;
    } else {
      int _id = item.getId();
      boolean _notEquals = (_id != id);
      _or = _notEquals;
    }
    if (_or) {
      return;
    }
    final ItemDefinition itemDef = item.getDefinition();
    int _id_1 = item.getId();
    final EquipmentDefinition equipDef = EquipmentDefinition.forId(_id_1);
    boolean _equals_1 = Objects.equal(equipDef, null);
    if (_equals_1) {
      return;
    }
    final SkillSet skillSet = player.getSkillSet();
    Skill _skill = skillSet.getSkill(Skill.ATTACK);
    int _maximumLevel = _skill.getMaximumLevel();
    int _attackLevel = equipDef.getAttackLevel();
    boolean _lessThan = (_maximumLevel < _attackLevel);
    if (_lessThan) {
      int _attackLevel_1 = equipDef.getAttackLevel();
      String _plus = ("You need an Attack level of " + Integer.valueOf(_attackLevel_1));
      String _plus_1 = (_plus + " to equip this item.");
      player.sendMessage(_plus_1);
      return;
    }
    Skill _skill_1 = skillSet.getSkill(Skill.STRENGTH);
    int _maximumLevel_1 = _skill_1.getMaximumLevel();
    int _strengthLevel = equipDef.getStrengthLevel();
    boolean _lessThan_1 = (_maximumLevel_1 < _strengthLevel);
    if (_lessThan_1) {
      int _strengthLevel_1 = equipDef.getStrengthLevel();
      String _plus_2 = ("You need a Strength level of " + Integer.valueOf(_strengthLevel_1));
      String _plus_3 = (_plus_2 + " to equip this item.");
      player.sendMessage(_plus_3);
      return;
    }
    Skill _skill_2 = skillSet.getSkill(Skill.DEFENCE);
    int _maximumLevel_2 = _skill_2.getMaximumLevel();
    int _defenceLevel = equipDef.getDefenceLevel();
    boolean _lessThan_2 = (_maximumLevel_2 < _defenceLevel);
    if (_lessThan_2) {
      int _defenceLevel_1 = equipDef.getDefenceLevel();
      String _plus_4 = ("You need a Defence level of " + Integer.valueOf(_defenceLevel_1));
      String _plus_5 = (_plus_4 + " to equip this item.");
      player.sendMessage(_plus_5);
      return;
    }
    Skill _skill_3 = skillSet.getSkill(Skill.RANGED);
    int _maximumLevel_3 = _skill_3.getMaximumLevel();
    int _rangedLevel = equipDef.getRangedLevel();
    boolean _lessThan_3 = (_maximumLevel_3 < _rangedLevel);
    if (_lessThan_3) {
      int _rangedLevel_1 = equipDef.getRangedLevel();
      String _plus_6 = ("You need a Ranged level of " + Integer.valueOf(_rangedLevel_1));
      String _plus_7 = (_plus_6 + " to equip this item.");
      player.sendMessage(_plus_7);
      return;
    }
    Skill _skill_4 = skillSet.getSkill(Skill.MAGIC);
    int _maximumLevel_4 = _skill_4.getMaximumLevel();
    int _magicLevel = equipDef.getMagicLevel();
    boolean _lessThan_4 = (_maximumLevel_4 < _magicLevel);
    if (_lessThan_4) {
      int _magicLevel_1 = equipDef.getMagicLevel();
      String _plus_8 = ("You need a Magic level of " + Integer.valueOf(_magicLevel_1));
      String _plus_9 = (_plus_8 + " to equip this item.");
      player.sendMessage(_plus_9);
      return;
    }
    final Inventory inventory = player.getInventory();
    final Inventory equipment = player.getEquipment();
    final int equipmentSlot = equipDef.getSlot();
    boolean _isTwoHanded = equipDef.isTwoHanded();
    if (_isTwoHanded) {
      Item _get = equipment.get(EquipmentConstants.SHIELD);
      boolean _notEquals_1 = (!Objects.equal(_get, null));
      if (_notEquals_1) {
        final Item shield = equipment.reset(EquipmentConstants.SHIELD);
        inventory.add(shield);
      }
    }
    boolean removeWeapon = false;
    if ((equipmentSlot == EquipmentConstants.SHIELD)) {
      final Item currentWeapon = equipment.get(EquipmentConstants.WEAPON);
      boolean _notEquals_2 = (!Objects.equal(currentWeapon, null));
      if (_notEquals_2) {
        int _id_2 = currentWeapon.getId();
        final EquipmentDefinition weaponDef = EquipmentDefinition.forId(_id_2);
        boolean _isTwoHanded_1 = weaponDef.isTwoHanded();
        if (_isTwoHanded_1) {
          int _freeSlots = inventory.freeSlots();
          boolean _lessThan_5 = (_freeSlots < 1);
          if (_lessThan_5) {
            inventory.forceCapacityExceeded();
            return;
          }
          removeWeapon = true;
        }
      }
    }
    final Item previous = equipment.get(equipmentSlot);
    boolean _and = false;
    boolean _and_1 = false;
    boolean _isStackable = itemDef.isStackable();
    if (!_isStackable) {
      _and_1 = false;
    } else {
      boolean _notEquals_3 = (!Objects.equal(previous, null));
      _and_1 = _notEquals_3;
    }
    if (!_and_1) {
      _and = false;
    } else {
      int _id_3 = previous.getId();
      int _id_4 = item.getId();
      boolean _equals_2 = (_id_3 == _id_4);
      _and = _equals_2;
    }
    if (_and) {
      inventory.remove(item);
      final Item tmp = equipment.add(item);
      boolean _notEquals_4 = (!Objects.equal(tmp, null));
      if (_notEquals_4) {
        inventory.add(tmp);
      }
    } else {
      final Item tmp_1 = equipment.reset(equipmentSlot);
      equipment.set(equipmentSlot, item);
      inventory.reset(slot);
      boolean _notEquals_5 = (!Objects.equal(tmp_1, null));
      if (_notEquals_5) {
        inventory.add(tmp_1);
      }
    }
    boolean _isTwoHanded_2 = equipDef.isTwoHanded();
    if (_isTwoHanded_2) {
      final Item tmp_2 = equipment.reset(EquipmentConstants.SHIELD);
      inventory.add(tmp_2);
    }
    if (removeWeapon) {
      final Item tmp_3 = equipment.reset(EquipmentConstants.WEAPON);
      inventory.add(tmp_3);
    }
  }
  
  public void subscribe(final ItemActionEvent event) {
    Player _player = event.getPlayer();
    int _id = event.getId();
    int _slot = event.getSlot();
    this.wear(_player, _id, _slot);
  }
  
  public boolean test(final ItemActionEvent event) {
    boolean _xblockexpression = false;
    {
      int _id = event.getId();
      final ItemDefinition definition = ItemDefinition.forId(_id);
      boolean _and = false;
      String[] _inventoryActions = definition.getInventoryActions();
      boolean _contains = ((List<String>)Conversions.doWrapArray(_inventoryActions)).contains("Wield");
      boolean _not = (!_contains);
      if (!_not) {
        _and = false;
      } else {
        String[] _inventoryActions_1 = definition.getInventoryActions();
        boolean _contains_1 = ((List<String>)Conversions.doWrapArray(_inventoryActions_1)).contains("Wear");
        boolean _not_1 = (!_contains_1);
        _and = _not_1;
      }
      if (_and) {
        return false;
      }
      Player _player = event.getPlayer();
      this.closeInterfaces(_player);
      int _interfaceId = event.getInterfaceId();
      _xblockexpression = (_interfaceId == SynchronizationInventoryListener.INVENTORY_ID);
    }
    return _xblockexpression;
  }
}
