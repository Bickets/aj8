package common;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceSet;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure3;

@SuppressWarnings("all")
public abstract class Plugin {
  private final ThreadLocalRandom random = ThreadLocalRandom.current();
  
  /**
   * logic expressions
   */
  private final Function1<String, Boolean> isNum = new Function1<String, Boolean>() {
    public Boolean apply(final String str) {
      return str.matches("-?\\d+");
    }
  };
  
  private final Function1<String, Integer> toInt = new Function1<String, Integer>() {
    public Integer apply(final String str) {
      Integer _xifexpression = null;
      Boolean _isNum = Plugin.this.isNum(str);
      if ((_isNum).booleanValue()) {
        _xifexpression = Integer.valueOf(str);
      } else {
        throw new NumberFormatException();
      }
      return _xifexpression;
    }
  };
  
  private final Function1<Integer, Integer> randomNoZero = new Function1<Integer, Integer>() {
    public Integer apply(final Integer range) {
      Integer _apply = Plugin.this.exclusiveRandom.apply(Integer.valueOf(range));
      return ((_apply).intValue() + 1);
    }
  };
  
  private final Function1<Integer, Integer> exclusiveRandom = new Function1<Integer, Integer>() {
    public Integer apply(final Integer range) {
      return Plugin.this.random.nextInt(range);
    }
  };
  
  private final Function2<Integer, Integer, Integer> inclusiveRandom = new Function2<Integer, Integer, Integer>() {
    public Integer apply(final Integer min, final Integer max) {
      Integer _apply = Plugin.this.exclusiveRandom.apply(Integer.valueOf(((max - min) + 1)));
      return ((_apply).intValue() + min);
    }
  };
  
  private final Procedure3<Integer, Integer, List<Integer>> inclusiveRandomExcludes = new Procedure3<Integer, Integer, List<Integer>>() {
    public void apply(final Integer min, final Integer max, final List<Integer> excludes) {
      Integer result = Plugin.this.inclusiveRandom.apply(Integer.valueOf(min), Integer.valueOf(max));
      boolean _contains = excludes.contains(result);
      boolean _while = _contains;
      while (_while) {
        Integer _apply = Plugin.this.inclusiveRandom.apply(Integer.valueOf(min), Integer.valueOf(max));
        result = _apply;
        boolean _contains_1 = excludes.contains(result);
        _while = _contains_1;
      }
    }
  };
  
  private final Function1<Float, Float> randomFloat = new Function1<Float, Float>() {
    public Float apply(final Float range) {
      float _nextFloat = Plugin.this.random.nextFloat();
      return (_nextFloat * range);
    }
  };
  
  public Integer toInt(final String str) {
    return this.toInt.apply(str);
  }
  
  public Boolean isNum(final String str) {
    return this.isNum.apply(str);
  }
  
  public Integer random(final int range) {
    return this.exclusiveRandom.apply(Integer.valueOf(range));
  }
  
  public Integer randomExcludesZero(final int range) {
    return this.randomNoZero.apply(Integer.valueOf(range));
  }
  
  public Integer inclusiveRandom(final int min, final int max) {
    return this.inclusiveRandom.apply(Integer.valueOf(min), Integer.valueOf(max));
  }
  
  public void inclusiveRandomExcludes(final int min, final int max, final List<Integer> excludes) {
    this.inclusiveRandomExcludes.apply(Integer.valueOf(min), Integer.valueOf(max), excludes);
  }
  
  public Float random(final float range) {
    return this.randomFloat.apply(Float.valueOf(range));
  }
  
  public void closeInterfaces(final Player player) {
    InterfaceSet _interfaceSet = player.getInterfaceSet();
    _interfaceSet.close();
  }
}
