package location;

import mobs.MobSpawn;
import org.apollo.game.model.Direction;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;

@SuppressWarnings("all")
public class Lumbridge extends MobSpawn {
  public Lumbridge(final World world) {
    super(world);
  }
  
  public void spawn() {
    Position _position = new Position(3222, 3222, 0);
    this.spawn("man", 1, _position, Direction.NORTH);
  }
}
