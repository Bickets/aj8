package org.apollo.security;

import net.burtleburtle.bob.rand.IsaacAlgorithm;

/**
 * A pair of two {@link IsaacAlgorithm} random number generators used as a
 * stream cipher. One takes the role of an encoder for this endpoint, the other
 * takes the role of a decoder for this endpoint.
 *
 * @author Graham
 */
public final class IsaacRandomPair {

    /**
     * The random number generator used to encode data.
     */
    private final IsaacAlgorithm encodingRandom;

    /**
     * The random number generator used to decode data.
     */
    private final IsaacAlgorithm decodingRandom;

    /**
     * Creates the pair of random number generators.
     *
     * @param encodingRandom The random number generator used for encoding.
     * @param decodingRandom The random number generator used for decoding.
     */
    public IsaacRandomPair(IsaacAlgorithm encodingRandom, IsaacAlgorithm decodingRandom) {
	this.encodingRandom = encodingRandom;
	this.decodingRandom = decodingRandom;
    }

    /**
     * Gets the random number generator used for encoding.
     *
     * @return The random number generator used for encoding.
     */
    public IsaacAlgorithm getEncodingRandom() {
	return encodingRandom;
    }

    /**
     * Gets the random number generator used for decoding.
     *
     * @return The random number generator used for decoding.
     */
    public IsaacAlgorithm getDecodingRandom() {
	return decodingRandom;
    }

}
