package org.apollo.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apollo.game.model.World;
import org.apollo.service.Service;

/**
 * The plugin service is responsible for initializing the bootstrap plugin --
 * The bootstrap plugin is essential for the other plugins, it essentially
 * "bootstraps" all of the other plugins together by providing necessary
 * functionality used throughout all of the plugins. It wraps Apollo's
 * Java-style API into an Xtend-style API
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PluginService extends Service {

    /**
     * The world.
     */
    private final World world;

    /**
     * Constructs a new {@link PluginService}.
     *
     * @param world The world.
     */
    public PluginService(World world) {
	this.world = world;
    }

    @Override
    public void start() {
	/*
	 * This may make some people go "what" in their heads, so let me explain
	 * exactly what's going on: Xtend is a flexible and expressive dialect
	 * of Java, which compiles into Java 5 compatible source code. After our
	 * plugins are compiled we compile the Java plugin into Java byte code
	 * which gets added to the main class path. Therefore this call is
	 * perfectly legal.
	 */
	try {
	    Class<?> clazz = Class.forName("Bootstrap");
	    Constructor<?> bootstrap = clazz.getConstructor(World.class);
	    bootstrap.newInstance(world);
	} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	    e.printStackTrace();
	}
    }

}
