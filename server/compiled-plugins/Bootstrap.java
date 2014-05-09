import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;
import mobs.InitialMobSpawns;
import org.apollo.game.command.CommandDispatcher;
import org.apollo.game.command.CommandListener;
import org.apollo.game.interact.ButtonClickListener;
import org.apollo.game.interact.InteractionHandler;
import org.apollo.game.interact.ItemActionListener;
import org.apollo.game.interact.ObjectActionListener;
import org.apollo.game.model.World;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class Bootstrap {
  private final Logger logger = new Function0<Logger>() {
    public Logger apply() {
      String _name = Bootstrap.class.getName();
      Logger _logger = Logger.getLogger(_name);
      return _logger;
    }
  }.apply();
  
  public void initObjects(final World world) {
    final ArrayList<Class<? extends Object>> classes = this.getClasses("objects");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final ObjectActionListener handler = ((ObjectActionListener) _newInstance);
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          _interactionHandler.bind(handler);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    classes.forEach(_function);
    int _size = classes.size();
    String _plus = ("Loaded " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " object plugins.");
    this.logger.info(_plus_1);
  }
  
  public void initButtons(final World world) {
    final ArrayList<Class<? extends Object>> classes = this.getClasses("buttons");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final ButtonClickListener handler = ((ButtonClickListener) _newInstance);
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          _interactionHandler.bind(handler);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    classes.forEach(_function);
    int _size = classes.size();
    String _plus = ("Loaded " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " button plugins.");
    this.logger.info(_plus_1);
  }
  
  public void initItems(final World world) {
    final ArrayList<Class<? extends Object>> classes = this.getClasses("items");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final ItemActionListener handler = ((ItemActionListener) _newInstance);
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          _interactionHandler.bind(handler);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    classes.forEach(_function);
    int _size = classes.size();
    String _plus = ("Loaded " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " item plugins.");
    this.logger.info(_plus_1);
  }
  
  public void initSpawns(final World world) {
    InitialMobSpawns _initialMobSpawns = new InitialMobSpawns(world);
    final InitialMobSpawns spawns = _initialMobSpawns;
    spawns.init();
  }
  
  public void initCommands() {
    final ArrayList<Class<? extends Object>> classes = this.getClasses("commands");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final CommandListener listener = ((CommandListener) _newInstance);
          CommandDispatcher _instance = CommandDispatcher.getInstance();
          _instance.bind(listener);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    classes.forEach(_function);
    int _size = classes.size();
    String _plus = ("Loaded " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " command plugins.");
    this.logger.info(_plus_1);
  }
  
  public ArrayList<Class<? extends Object>> getClasses(final String dir) {
    String _plus = ("bin/" + dir);
    File _file = new File(_plus, "/");
    final String[] files = _file.list();
    final ArrayList<Class<? extends Object>> classes = CollectionLiterals.<Class<? extends Object>>newArrayList();
    if (((List<String>)Conversions.doWrapArray(files))!=null) {
      final Consumer<String> _function = new Consumer<String>() {
        public void accept(final String file) {
          try {
            boolean _and = false;
            boolean _endsWith = file.endsWith(".class");
            if (!_endsWith) {
              _and = false;
            } else {
              boolean _contains = file.contains("$");
              boolean _not = (!_contains);
              _and = (_endsWith && _not);
            }
            if (_and) {
              String _plus = (dir + ".");
              int _lastIndexOf = file.lastIndexOf(".");
              String _substring = file.substring(0, _lastIndexOf);
              String _plus_1 = (_plus + _substring);
              final Class<? extends Object> clazz = Class.forName(_plus_1);
              boolean _and_1 = false;
              int _modifiers = clazz.getModifiers();
              boolean _isAbstract = Modifier.isAbstract(_modifiers);
              boolean _not_1 = (!_isAbstract);
              if (!_not_1) {
                _and_1 = false;
              } else {
                int _modifiers_1 = clazz.getModifiers();
                boolean _isInterface = Modifier.isInterface(_modifiers_1);
                boolean _not_2 = (!_isInterface);
                _and_1 = (_not_1 && _not_2);
              }
              if (_and_1) {
                classes.add(clazz);
              }
            }
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      };
      ((List<String>)Conversions.doWrapArray(files)).forEach(_function);
    }
    return classes;
  }
  
  public Bootstrap(final World world) {
    this.initButtons(world);
    this.initObjects(world);
    this.initSpawns(world);
    this.initItems(world);
    this.initCommands();
  }
}
