import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.apollo.game.command.CommandDispatcher;
import org.apollo.game.command.CommandListener;
import org.apollo.game.interact.ButtonEventHandler;
import org.apollo.game.interact.InteractionHandler;
import org.apollo.game.interact.ObjectActionEventHandler;
import org.apollo.game.model.World;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * TODO: Should I rewrite this in Java?
 */
@SuppressWarnings("all")
public class Bootstrap {
  public void initObjects() {
    final ArrayList<Class<?>> classes = this.getClasses("objects");
    final Procedure1<Class<?>> _function = new Procedure1<Class<?>>() {
      public void apply(final Class<?> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final ObjectActionEventHandler handler = ((ObjectActionEventHandler) _newInstance);
          World _instance = World.getInstance();
          InteractionHandler _interactionHandler = _instance.getInteractionHandler();
          _interactionHandler.bind(handler);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    IterableExtensions.<Class<?>>forEach(classes, _function);
  }
  
  public void initButtons() {
    final ArrayList<Class<?>> classes = this.getClasses("buttons");
    final Procedure1<Class<?>> _function = new Procedure1<Class<?>>() {
      public void apply(final Class<?> clazz) {
        try {
          Object _newInstance = clazz.newInstance();
          final ButtonEventHandler handler = ((ButtonEventHandler) _newInstance);
          World _instance = World.getInstance();
          InteractionHandler _interactionHandler = _instance.getInteractionHandler();
          _interactionHandler.bind(handler);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
    IterableExtensions.<Class<?>>forEach(classes, _function);
  }
  
  public void initCommands() {
    final ArrayList<Class<?>> classes = this.getClasses("commands");
    final Procedure1<Class<?>> _function = new Procedure1<Class<?>>() {
      public void apply(final Class<?> clazz) {
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
    IterableExtensions.<Class<?>>forEach(classes, _function);
  }
  
  public ArrayList<Class<?>> getClasses(final String dir) {
    File _file = new File(("bin/" + dir), "/");
    final String[] files = _file.list();
    final ArrayList<Class<?>> classes = CollectionLiterals.<Class<?>>newArrayList();
    if (((Iterable<String>)Conversions.doWrapArray(files))!=null) {
      final Procedure1<String> _function = new Procedure1<String>() {
        public void apply(final String file) {
          try {
            boolean _and = false;
            boolean _endsWith = file.endsWith(".class");
            if (!_endsWith) {
              _and = false;
            } else {
              boolean _contains = file.contains("$");
              boolean _not = (!_contains);
              _and = _not;
            }
            if (_and) {
              int _lastIndexOf = file.lastIndexOf(".");
              String _substring = file.substring(0, _lastIndexOf);
              String _plus = ((dir + ".") + _substring);
              final Class<?> clazz = Class.forName(_plus);
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
                _and_1 = _not_2;
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
      IterableExtensions.<String>forEach(((Iterable<String>)Conversions.doWrapArray(files)), _function);
    }
    return classes;
  }
  
  public Bootstrap() {
    this.initButtons();
    this.initCommands();
    this.initObjects();
  }
}
