package commands;

import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.SkillSet;

@SuppressWarnings("all")
public class LevelCommand extends PrivilegedCommandListener {
  public LevelCommand() {
    super(PrivilegeLevel.ADMINISTRATOR);
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
      _or = (_not || _not_1);
    }
    if (_or) {
      String _name = this.getName();
      String _plus = ("Syntax is ::" + _name);
      String _plus_1 = (_plus + " [skill_id, level]");
      player.sendMessage(_plus_1);
      return;
    }
    String _get_2 = args[0];
    final Integer id = Integer.valueOf(_get_2);
    String _get_3 = args[1];
    final Integer level = Integer.valueOf(_get_3);
    boolean _or_1 = false;
    boolean _or_2 = false;
    boolean _or_3 = false;
    boolean _greaterThan = ((id).intValue() > SkillSet.SKILL_COUNT);
    if (_greaterThan) {
      _or_3 = true;
    } else {
      boolean _lessThan = ((id).intValue() < 0);
      _or_3 = (_greaterThan || _lessThan);
    }
    if (_or_3) {
      _or_2 = true;
    } else {
      boolean _lessThan_1 = ((level).intValue() < 0);
      _or_2 = (_or_3 || _lessThan_1);
    }
    if (_or_2) {
      _or_1 = true;
    } else {
      boolean _greaterThan_1 = ((level).intValue() > 99);
      _or_1 = (_or_2 || _greaterThan_1);
    }
    if (_or_1) {
      String _name_1 = this.getName();
      String _plus_2 = ("Syntax is ::" + _name_1);
      String _plus_3 = (_plus_2 + " [skill_id, level]");
      player.sendMessage(_plus_3);
      return;
    }
    SkillSet _skillSet = player.getSkillSet();
    double _experienceForLevel = SkillSet.getExperienceForLevel((level).intValue());
    _skillSet.addExperience((id).intValue(), _experienceForLevel);
  }
  
  public String getName() {
    return "level";
  }
  
  public boolean isDigit(final String string) {
    boolean _matches = string.matches("\\d+");
    return _matches;
  }
}
