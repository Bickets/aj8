package org.apollo.game.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * A queue of {@link Direction}s which a {@link GameCharacter} will follow.
 *
 * @author Graham
 */
public final class WalkingQueue {

    /**
     * The maximum size of the queue. If any additional steps are added, they
     * are discarded.
     */
    private static final int MAXIMUM_SIZE = 128;

    /**
     * Represents a single point in the queue.
     *
     * @author Graham
     */
    private final class Point {

	/**
	 * The point's position.
	 */
	private final Position position;

	/**
	 * The direction to walk to this point.
	 */
	private final Direction direction;

	/**
	 * Creates a point.
	 *
	 * @param position The position.
	 * @param direction The direction.
	 */
	public Point(Position position, Direction direction) {
	    this.position = position;
	    this.direction = direction;
	}

	@Override
	public String toString() {
	    return getClass().getName() + " [direction=" + direction + ", position=" + position + "]";
	}

    }

    /**
     * The gameCharacter whose walking queue this is.
     */
    private final GameCharacter gameCharacter;

    /**
     * The queue of directions.
     */
    private final Deque<Point> points = new ArrayDeque<>();

    /**
     * The old queue of directions.
     */
    private final Deque<Point> oldPoints = new ArrayDeque<>();

    /**
     * Flag indicating if this queue (only) should be ran.
     */
    private boolean runningQueue;

    /**
     * Creates a walking queue for the specified gameCharacter.
     *
     * @param gameCharacter The gameCharacter.
     */
    public WalkingQueue(GameCharacter gameCharacter) {
	this.gameCharacter = gameCharacter;
    }

    /**
     * Called every pulse, updates the queue.
     */
    public void pulse() {
	Position position = gameCharacter.getPosition();

	Direction first = Direction.NONE;
	Direction second = Direction.NONE;

	Point next = points.poll();
	if (next != null) {
	    Direction direction = Direction.between(position, next.position);
	    boolean traversable = Direction.isTraversable(position, direction, gameCharacter.size());
	    if (traversable) {
		first = direction;
		position = next.position;
		if (runningQueue/* or run toggled AND enough energy */) {
		    next = points.poll();
		    if (next != null) {
			direction = Direction.between(position, next.position);
			traversable = Direction.isTraversable(position, direction, gameCharacter.size());
			if (traversable) {
			    second = direction;
			    position = next.position;
			}
		    }
		}
	    }

	    if (!traversable) {
		clear();
	    }
	}

	gameCharacter.setDirections(first, second);
	gameCharacter.setPosition(position);
    }

    /**
     * Sets the running queue flag.
     *
     * @param running The running queue flag.
     */
    public void setRunningQueue(boolean running) {
	runningQueue = running;
    }

    /**
     * Adds the first step to the queue, attempting to connect the server and
     * client position by looking at the previous queue.
     *
     * @param clientConnectionPosition The first step.
     * @return {@code true} if the queues could be connected correctly,
     *         {@code false} if not.
     */
    public boolean addFirstStep(Position clientConnectionPosition) {
	Position serverPosition = gameCharacter.getPosition();

	int deltaX = clientConnectionPosition.getX() - serverPosition.getX();
	int deltaY = clientConnectionPosition.getY() - serverPosition.getY();

	if (Direction.isConnectable(deltaX, deltaY)) {
	    clear();

	    addStep(clientConnectionPosition);
	    return true;
	}

	Queue<Position> travelBackQueue = new ArrayDeque<>();

	Point oldPoint;
	while ((oldPoint = oldPoints.pollLast()) != null) {
	    Position oldPosition = oldPoint.position;

	    deltaX = oldPosition.getX() - serverPosition.getX();
	    deltaY = oldPosition.getX() - serverPosition.getY();

	    travelBackQueue.add(oldPosition);

	    if (Direction.isConnectable(deltaX, deltaY)) {
		clear();

		travelBackQueue.forEach(this::addStep);

		addStep(clientConnectionPosition);
		return true;
	    }
	}

	oldPoints.clear();
	return false;
    }

    /**
     * Adds a step to the queue.
     *
     * @param step The step to add.
     */
    public void addStep(Position step) {
	Point last = getLast();

	int x = step.getX();
	int y = step.getY();

	int deltaX = Math.abs(x - last.position.getX());
	int deltaY = Math.abs(y - last.position.getY());

	int max = Math.max(deltaX, deltaY);

	for (int i = 0; i < max; i++) {
	    if (deltaX < 0) {
		deltaX++;
	    } else if (deltaX > 0) {
		deltaX--;
	    }

	    if (deltaY < 0) {
		deltaY++;
	    } else if (deltaY > 0) {
		deltaY--;
	    }

	    addStep(x - deltaX, y - deltaY);
	}
    }

    /**
     * Adds a step.
     *
     * @param x The x coordinate of this step.
     * @param y The y coordinate of this step.
     */
    private void addStep(int x, int y) {
	if (points.size() >= MAXIMUM_SIZE) {
	    return;
	}

	Point last = getLast();
	Direction direction = Direction.between(new Position(x, y), last.position);

	if (direction != Direction.NONE) {
	    Point p = new Point(new Position(x, y, gameCharacter.getPosition().getHeight()), direction);
	    points.add(p);
	    oldPoints.add(p);
	}
    }

    /**
     * Gets the last point.
     *
     * @return The last point.
     */
    private Point getLast() {
	Point last = points.peekLast();
	if (last == null) {
	    return new Point(gameCharacter.getPosition(), Direction.NONE);
	}
	return last;
    }

    /**
     * Clears the walking queue.
     */
    public void clear() {
	points.clear();
	oldPoints.clear();
    }

    /**
     * Gets the size of the queue.
     *
     * @return The size of the queue.
     */
    public int size() {
	return points.size();
    }

}