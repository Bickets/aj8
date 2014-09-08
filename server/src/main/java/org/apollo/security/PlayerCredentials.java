package org.apollo.security;

import org.apollo.util.NameUtil;
import org.apollo.util.TextUtil;

/**
 * Holds the credentials for a player.
 *
 * @author Graham
 */
public final class PlayerCredentials {

	/**
	 * The players username.
	 */
	private final String username;

	/**
	 * The players displayed username.
	 */
	private final String displayUsername;

	/**
	 * The players username encoded as a long.
	 */
	private final long encodedUsername;

	/**
	 * The players password.
	 */
	private final String password;

	/**
	 * The hash of the players username.
	 */
	private final int usernameHash;

	/**
	 * The computer's unique identifier.
	 */
	private final int uid;

	/**
	 * The connecting host address.
	 */
	private final String address;

	/**
	 * Creates a new {@link PlayerCredentials} object with the specified name,
	 * password and uid.
	 *
	 * @param username The players username.
	 * @param password The players password.
	 * @param usernameHash The hash of the players username.
	 * @param uid The computer's uid.
	 * @param address The connection host address.
	 */
	public PlayerCredentials(String username, String password, int usernameHash, int uid, String address) {
		this.username = username;
		displayUsername = TextUtil.capitalize(username);
		encodedUsername = NameUtil.encodeBase37(username);
		this.password = password;
		this.usernameHash = usernameHash;
		this.uid = uid;
		this.address = address;
	}

	/**
	 * Gets the players username.
	 *
	 * @return The players username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the players displayed username..
	 *
	 * @return The players displayed username.
	 */
	public String getDisplayUsername() {
		return displayUsername;
	}

	/**
	 * Gets the players username encoded as a long.
	 *
	 * @return The username as encoded by {@link NameUtil#encodeBase37(String)}.
	 */
	public long getEncodedUsername() {
		return encodedUsername;
	}

	/**
	 * Gets the players password.
	 *
	 * @return The players password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the username hash.
	 *
	 * @return The username hash.
	 */
	public int getUsernameHash() {
		return usernameHash;
	}

	/**
	 * Gets the computer's uid.
	 *
	 * @return The computer's uid.
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Gets the connection host address.
	 *
	 * @return The connecting host address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

}