package org.apollo.io.player;

import static org.apollo.net.codec.login.LoginConstants.STATUS_ACCOUNT_DISABLED;
import static org.apollo.net.codec.login.LoginConstants.STATUS_OK;

/**
 * An enumeration representing the states of a players sanction.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum PlayerSanctionResponse {

    /**
     * A response indicating that the specified credentials are disabled from
     * logging in.
     */
    DISABLED(STATUS_ACCOUNT_DISABLED),

    /**
     * A response indicating that the specified credentials are muted and are
     * not able to speak.
     */
    MUTED(STATUS_OK),

    /**
     * A response indicating the credentials are OK, no sanctions are applied.
     */
    OK(STATUS_OK);

    /**
     * The integer value of the sanction response.
     */
    private final int status;

    /**
     * Constructs a new {@link PlayerSanctionResponse} with the specified
     * integer value.
     *
     * @param status The integer value of the response.
     */
    private PlayerSanctionResponse(int status) {
	this.status = status;
    }

    /**
     * Returns the integer value of the sanction response.
     */
    public final int getStatus() {
	return status;
    }

}