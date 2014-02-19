
package org.apollo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

/**
 * A class which manages {@link Service}s.
 * @author Graham
 */
public final class ServiceManager
{

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger( ServiceManager.class.getName() );

	/**
	 * The service map.
	 */
	private Map<Class< ? extends Service>, Service> services = new HashMap<>();


	/**
	 * Creates and initializes the {@link ServiceManager}.
	 * @throws Exception if an error occurs.
	 */
	public ServiceManager() throws Exception
	{
		init();
	}


	/**
	 * Initializes this service manager.
	 * @throws Exception if an error occurs.
	 */
	@SuppressWarnings( "unchecked" )
	private void init() throws Exception
	{
		logger.info( "Registering services..." );

		File file = new File( "data/", "services.json" );
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createJsonParser( file );

		if( parser.nextToken() != JsonToken.START_OBJECT ) {
			throw new IOException();
		}

		while( parser.nextToken() != JsonToken.END_OBJECT ) {
			String name = parser.getCurrentName();

			switch( name ) {
				case "services":
					if( parser.nextToken() != JsonToken.START_ARRAY ) {
						throw new IOException();
					}

					while( parser.nextToken() != JsonToken.END_ARRAY ) {
						Class< ? extends Service> clazz = ( Class< ? extends Service> )Class.forName( parser.getText() );
						register( ( Class<Service> )clazz, clazz.newInstance() );
					}
					break;
			}
		}
	}


	/**
	 * Registers a service.
	 * @param <S> The type of service.
	 * @param clazz The service's class.
	 * @param service The service.
	 */
	private <S extends Service> void register( Class<S> clazz, S service )
	{
		logger.fine( "Registering service: " + clazz + "..." );
		services.put( clazz, service );
	}


	/**
	 * Gets a service.
	 * @param <S> The type of service.
	 * @param clazz The service class.
	 * @return The service.
	 */
	@SuppressWarnings( "unchecked" )
	public <S extends Service> S getService( Class<S> clazz )
	{
		return ( S )services.get( clazz );
	}


	/**
	 * Starts all the services.
	 */
	public void startAll()
	{
		logger.info( "Starting services..." );
		services.values().forEach( service -> {
			logger.fine( "Starting service: " + service + "..." );
			service.start();
		} );
	}


	/**
	 * Sets the context of all services.
	 * @param ctx The server context.
	 */
	public void setContext( ServerContext ctx )
	{
		for( Service s: services.values() ) {
			s.setContext( ctx );
		}
	}

}
