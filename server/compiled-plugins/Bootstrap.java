import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.Consumer;
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
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Bootstrap {
  public void initObjects(final World world) {
    ArrayList<Class<? extends Object>> _classes = this.classes("objects");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> it) {
        try {
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          Object _newInstance = it.newInstance();
          _interactionHandler.bind(((ObjectActionListener) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public void initButtons(final World world) {
    ArrayList<Class<? extends Object>> _classes = this.classes("buttons");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> it) {
        try {
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          Object _newInstance = it.newInstance();
          _interactionHandler.bind(((ButtonClickListener) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public void initItems(final World world) {
    ArrayList<Class<? extends Object>> _classes = this.classes("items");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> it) {
        try {
          InteractionHandler _interactionHandler = world.getInteractionHandler();
          Object _newInstance = it.newInstance();
          _interactionHandler.bind(((ItemActionListener) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public void initSpawns(final World world) {
    InitialMobSpawns _initialMobSpawns = new InitialMobSpawns(world);
    _initialMobSpawns.init();
  }
  
  public void initCommands() {
    ArrayList<Class<? extends Object>> _classes = this.classes("commands");
    final Consumer<Class<? extends Object>> _function = new Consumer<Class<? extends Object>>() {
      public void accept(final Class<? extends Object> it) {
        try {
          CommandDispatcher _instance = CommandDispatcher.getInstance();
          Object _newInstance = it.newInstance();
          _instance.bind(((CommandListener) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public ArrayList<Class<? extends Object>> classes(final String dir) {
    String _plus = ("bin/" + dir);
    File _file = new File(_plus, "/");
    final String[] files = _file.list();
    final ArrayList<Class<? extends Object>> classes = CollectionLiterals.<Class<? extends Object>>newArrayList();
    Iterable<String> _filter = null;
    if (((Iterable<String>)Conversions.doWrapArray(files))!=null) {
      final Function1<String,Boolean> _function = new Function1<String,Boolean>() {
        public Boolean apply(final String it) {
          boolean _and = false;
          boolean _endsWith = it.endsWith(".class");
          if (!_endsWith) {
            _and = false;
          } else {
            boolean _contains = it.contains("$");
            boolean _not = (!_contains);
            _and = (_endsWith && _not);
          }
          return Boolean.valueOf(_and);
        }
      };
      _filter=IterableExtensions.<String>filter(((Iterable<String>)Conversions.doWrapArray(files)), _function);
    }
    final Iterable<String> filtered = _filter;
    final Consumer<String> _function_1 = new Consumer<String>() {
      public void accept(final String it) {
        try {
          int _indexOf = it.indexOf(".");
          final String name = it.substring(0, _indexOf);
          String _plus = (dir + ".");
          String _plus_1 = (_plus + name);
          final Class<? extends Object> clazz = Class.forName(_plus_1);
          boolean _and = false;
          int _modifiers = clazz.getModifiers();
          boolean _isAbstract = Modifier.isAbstract(_modifiers);
          boolean _not = (!_isAbstract);
          if (!_not) {
            _and = false;
          } else {
            int _modifiers_1 = clazz.getModifiers();
            boolean _isInterface = Modifier.isInterface(_modifiers_1);
            boolean _not_1 = (!_isInterface);
            _and = (_not && _not_1);
          }
          if (_and) {
            classes.add(clazz);
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    filtered.forEach(_function_1);
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
