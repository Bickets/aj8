package commands;

import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;

@SuppressWarnings("all")
public class ItemCommand extends PrivilegedCommandListener {
    public ItemCommand() {
	super(Player.PrivilegeLevel.ADMINISTRATOR);
    }

    public void executePrivileged(final Player player, final Command command) {
	final String[] args = command.getArguments();
	boolean _or = false;
	String _get = args[0];
	boolean _isDigit = this.isDigit(_get);
	boolean _not = (!_isDigit);
	if (_not) {
	    _or = true;
	} else {
	    String _get_1 = args[1];
	    boolean _isDigit_1 = this.isDigit(_get_1);
	    boolean _not_1 = (!_isDigit_1);
	    _or = _not_1;
	}
	if (_or) {
	    String _name = this.getName();
	    String _plus = ("Syntax is ::" + _name);
	    String _plus_1 = (_plus + " [id, amount]");
	    player.sendMessage(_plus_1);
	    return;
	}
	String _get_2 = args[0];
	final Integer id = Integer.valueOf(_get_2);
	String _get_3 = args[1];
	final Integer amount = Integer.valueOf(_get_3);
	boolean _or_1 = false;
	boolean _or_2 = false;
	boolean _or_3 = false;
	int _count = ItemDefinition.count();
	boolean _greaterThan = ((id).intValue() > _count);
	if (_greaterThan) {
	    _or_3 = true;
	} else {
	    _or_3 = ((id).intValue() <= 0);
	}
	if (_or_3) {
	    _or_2 = true;
	} else {
	    _or_2 = ((amount).intValue() <= 0);
	}
	if (_or_2) {
	    _or_1 = true;
	} else {
	    _or_1 = ((amount).intValue() > Integer.MAX_VALUE);
	}
	if (_or_1) {
	    String _name_1 = this.getName();
	    String _plus_2 = ("Syntax is ::" + _name_1);
	    String _plus_3 = (_plus_2 + " [id, amount]");
	    player.sendMessage(_plus_3);
	    return;
	}
	Inventory _inventory = player.getInventory();
	_inventory.add((id).intValue(), (amount).intValue());
    }

    public String getName() {
	return "item";
    }

    public boolean isDigit(final String string) {
	return string.matches("\\d+");
    }
}
