package mobs;

import org.apollo.game.model.Direction;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
public abstract class MobSpawn {
  private final World _world;
  
  public World getWorld() {
    return this._world;
  }
  
  public boolean spawn(final int id, final Position position, final Direction direction) {
    return this.spawn("n/a", id, position, direction);
  }
  
  public boolean spawn(final int id, final Position position) {
    return this.spawn("n/a", id, position);
  }
  
  public boolean spawn(final String name, final int id, final Position position) {
    return this.spawn(name, id, position, Direction.NORTH);
  }
  
  public boolean spawn(final String name, final int id, final Position position, final Direction direction) {
    boolean _xblockexpression = false;
    {
      final Mob mob = new Mob(id, position, direction);
      World _world = this.getWorld();
      _xblockexpression = _world.register(mob);
    }
    return _xblockexpression;
  }
  
  public abstract void spawn();
  
  public MobSpawn(final World world) {
    super();
    this._world = world;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._world== null) ? 0 : this._world.hashCode());
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
    MobSpawn other = (MobSpawn) obj;
    if (this._world == null) {
      if (other._world != null)
        return false;
    } else if (!this._world.equals(other._world))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
}
