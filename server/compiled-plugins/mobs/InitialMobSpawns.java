package mobs;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.apollo.game.common.Direction;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
public class InitialMobSpawns {
  private final World _world;
  
  public World getWorld() {
    return this._world;
  }
  
  private final ArrayList<Mob> _mobs = new ArrayList<Mob>();
  
  public ArrayList<Mob> getMobs() {
    return this._mobs;
  }
  
  private final Logger _logger = Logger.getGlobal();
  
  public Logger getLogger() {
    return this._logger;
  }
  
  public void init() {
    this.addAll();
    ArrayList<Mob> _mobs = this.getMobs();
    final Function1<Mob, Boolean> _function = new Function1<Mob, Boolean>() {
      public Boolean apply(final Mob it) {
        World _world = InitialMobSpawns.this.getWorld();
        return Boolean.valueOf(_world.register(it));
      }
    };
    /* ListExtensions.<Mob, Boolean>map(_mobs, _function); */
    Logger _logger = this.getLogger();
    ArrayList<Mob> _mobs_1 = this.getMobs();
    int _size = _mobs_1.size();
    String _plus = ("Loaded " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " mob spawns.");
    _logger.info(_plus_1);
  }
  
  public boolean addAll() {
    Position _position = new Position(3222, 3222);
    return this.add(1, _position, Direction.EAST);
  }
  
  public boolean add(final int id, final Position position, final Direction direction) {
    ArrayList<Mob> _mobs = this.getMobs();
    Mob _mob = new Mob(id, position, direction);
    return _mobs.add(_mob);
  }
  
  public InitialMobSpawns(final World world) {
    super();
    this._world = world;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_world== null) ? 0 : _world.hashCode());
    result = prime * result + ((_mobs== null) ? 0 : _mobs.hashCode());
    result = prime * result + ((_logger== null) ? 0 : _logger.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InitialMobSpawns other = (InitialMobSpawns) obj;
    if (_world == null) {
      if (other._world != null)
        return false;
    } else if (!_world.equals(other._world))
      return false;
    if (_mobs == null) {
      if (other._mobs != null)
        return false;
    } else if (!_mobs.equals(other._mobs))
      return false;
    if (_logger == null) {
      if (other._logger != null)
        return false;
    } else if (!_logger.equals(other._logger))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
}
