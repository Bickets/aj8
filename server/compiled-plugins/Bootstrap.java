import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.Consumer;
import mobs.InitialMobSpawns;
import org.apollo.game.command.CommandEvent;
import org.apollo.game.event.EventSubscriber;
import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.interact.ObjectActionEvent;
import org.apollo.game.model.World;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Bootstrap {
  public void initObjects(final World world) {
    ArrayList<Class<?>> _classes = this.classes("objects");
    final Consumer<Class<?>> _function = new Consumer<Class<?>>() {
      public void accept(final Class<?> it) {
        try {
          Object _newInstance = it.newInstance();
          world.<ObjectActionEvent>provideSubscriber(((EventSubscriber<ObjectActionEvent>) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public void initButtons(final World world) {
    ArrayList<Class<?>> _classes = this.classes("buttons");
    final Consumer<Class<?>> _function = new Consumer<Class<?>>() {
      public void accept(final Class<?> it) {
        try {
          Object _newInstance = it.newInstance();
          world.<ButtonActionEvent>provideSubscriber(((EventSubscriber<ButtonActionEvent>) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public void initItems(final World world) {
    ArrayList<Class<?>> _classes = this.classes("items");
    final Consumer<Class<?>> _function = new Consumer<Class<?>>() {
      public void accept(final Class<?> it) {
        try {
          Object _newInstance = it.newInstance();
          world.<ItemActionEvent>provideSubscriber(((EventSubscriber<ItemActionEvent>) _newInstance));
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
  
  public void initCommands(final World world) {
    ArrayList<Class<?>> _classes = this.classes("commands");
    final Consumer<Class<?>> _function = new Consumer<Class<?>>() {
      public void accept(final Class<?> it) {
        try {
          Object _newInstance = it.newInstance();
          world.<CommandEvent>provideSubscriber(((EventSubscriber<CommandEvent>) _newInstance));
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    _classes.forEach(_function);
  }
  
  public ArrayList<Class<?>> classes(final String dir) {
    File _file = new File(("bin/" + dir), "/");
    final String[] files = _file.list();
    final ArrayList<Class<?>> classes = CollectionLiterals.<Class<?>>newArrayList();
    Iterable<String> _filter = null;
    if (((Iterable<String>)Conversions.doWrapArray(files))!=null) {
      final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
        public Boolean apply(final String it) {
          boolean _and = false;
          boolean _endsWith = it.endsWith(".class");
          if (!_endsWith) {
            _and = false;
          } else {
            boolean _contains = it.contains("$");
            boolean _not = (!_contains);
            _and = _not;
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
          final Class<?> clazz = Class.forName(((dir + ".") + name));
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
            _and = _not_1;
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
    this.initSpawns(world);
    this.initItems(world);
    this.initObjects(world);
    this.initCommands(world);
  }
}
