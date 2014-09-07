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
public final class PluginService implements Service {

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
		try {
			Class<?> clazz = Class.forName("plugin.Bootstrap");
			Constructor<?> bootstrap = clazz.getConstructor(World.class);
			bootstrap.newInstance(world);
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}