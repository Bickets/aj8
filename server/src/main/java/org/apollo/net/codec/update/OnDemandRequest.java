package org.apollo.net.codec.update;

/**
 * Represents a single 'on-demand' request.
 *
 * @author Graham
 */
public final class OnDemandRequest implements Comparable<OnDemandRequest> {

    /**
     * High priority - used in-game when data is required immediately but has
     * not yet been received.
     */
    public static final int HIGH_PRIORITY = 0;

    /**
     * Medium priority - used while loading the 'bare minimum' required to run
     * the game.
     */
    public static final int MEDIUM_PRIORITY = 1;

    /**
     * Low priority - used when a file is not required urgently. The client
     * login screen says "loading extra files.." when low priority loading is
     * being performed.
     */
    public static final int LOW_PRIORITY = 2;

    /**
     * The requested files index.
     */
    private final int index;

    /**
     * The requested files id.
     */
    private final int id;

    /**
     * The request priority.
     */
    private final int priority;

    /**
     * Creates the 'on-demand' request.
     *
     * @param fileDescriptor The file descriptor.
     * @param priority The priority.
     */
    public OnDemandRequest(int index, int id, int priority) {
	this.index = index;
	this.id = id;
	this.priority = priority;
    }

    /**
     * Returns the requested files index.
     */
    public int getIndex() {
	return index;
    }

    /**
     * Returns the requested files id.
     */
    public int getId() {
	return id;
    }

    /**
     * Gets the priority.
     *
     * @return The priority.
     */
    public int getPriority() {
	return priority;
    }

    @Override
    public int compareTo(OnDemandRequest o) {
	return priority - o.priority;
    }

}
