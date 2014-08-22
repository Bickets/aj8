package org.apollo.net;

import io.netty.util.AttributeKey;

import org.apollo.net.session.Session;

/**
 * Holds various network-related constants such as port numbers.
 *
 * @author Graham
 */
public final class NetworkConstants {

    /**
     * The service port.
     */
    public static final int SERVICE_PORT = 43594;

    /**
     * The JAGGRAB port.
     */
    public static final int JAGGRAB_PORT = 43595;

    /**
     * The HTTP port.
     */
    public static final int HTTP_PORT = 80;

    /**
     * The terminator of a string as specified by RS2 protocol.
     */
    public static final int STRING_TERMINATOR = '\n';

    /**
     * The number of seconds before a connection becomes idle.
     */
    public static final int IDLE_TIME = 15;

    /**
     * An {@link AttributeKey} which represents a current {@link Session}
     * attribute.
     */
    public static final AttributeKey<Session> NETWORK_SESSION = AttributeKey.valueOf("session.KEY");

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private NetworkConstants() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}