
package org.apollo.plugin;

import org.apollo.Service;

/**
 * The plugin service is responsible for initializing the bootstrap plugin --
 * The bootstrap plugin is essential for the other plugins, it essentially "bootstraps" all of the
 * other plugins together by providing necessary functionality used throughout all of the plugins.
 * It wraps Apollo's Java-style API into an Xtend-style API
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PluginService extends Service
{

	/**
	 * Constructs a new {@link PluginService}.
	 * @throws ClassNotFoundException If the specified class was not found.
	 * @throws IllegalAccessException If we are unable to access the specified class.
	 * @throws InstantiationException If something goes wrong during instantiation.
	 */
	public PluginService() throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		init();
	}


	/**
	 * Initializes the plugin service.
	 * @throws ClassNotFoundException If the specified class was not found.
	 * @throws IllegalAccessException If we are unable to access the specified class.
	 * @throws InstantiationException If something goes wrong during instantiation.
	 */
	private void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		/*
		 * This may make some people go "what" in their heads, so let me explain exactly what's going on:
		 * Xtend is a flexible and expressive dialect of Java, which compiles into Java 5 compatible source code.
		 * After our plugins are compiled we compile the Java plugin into Java byte code which gets added to the main class path.
		 * Therefore this call is perfectly legal. 
		 */
		Class< ? > clazz = Class.forName( "Bootstrap" );
		clazz.newInstance();
	}


	@Override
	public void start()
	{
		/* empty - here for consistency with other services */
	}

}
