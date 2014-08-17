package org.apollo.game.model.pf;

import java.util.HashSet;
import java.util.Set;

import org.apollo.game.model.Position;

/**
 * An implementation of a {@link PathFinder} which uses the A* search algorithm.
 *
 * @author Graham
 * @author Hadyn Richard
 */
public final class AStarPathFinder extends PathFinder {

    /**
     * The cost of moving in a straight line.
     */
    private static final int COST_STRAIGHT = 10;

    /**
     * Represents a node used by the A* algorithm.
     *
     * @author Graham
     */
    private final class Node implements Comparable<Node> {

	/**
	 * The parent node.
	 */
	private Node parent;

	/**
	 * The cost.
	 */
	private int cost;

	/**
	 * The heuristic.
	 */
	private int heuristic;

	/**
	 * The depth.
	 */
	private int depth;

	/**
	 * The x coordinate.
	 */
	private final int x;

	/**
	 * The y coordinate.
	 */
	private final int y;

	/**
	 * Creates a node.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Node(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent The parent.
	 */
	public void setParent(Node parent) {
	    this.parent = parent;
	}

	/**
	 * Gets the parent node.
	 *
	 * @return The parent node.
	 */
	public Node getParent() {
	    return parent;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost The cost.
	 */
	public void setCost(int cost) {
	    this.cost = cost;
	}

	/**
	 * Gets the cost.
	 *
	 * @return The cost.
	 */
	public int getCost() {
	    return cost;
	}

	/**
	 * Gets the X coordinate.
	 *
	 * @return The X coordinate.
	 */
	public int getX() {
	    return x;
	}

	/**
	 * Gets the Y coordinate.
	 *
	 * @return The Y coordinate.
	 */
	public int getY() {
	    return y;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + cost;
	    result = prime * result + depth;
	    result = prime * result + heuristic;
	    result = prime * result + (parent == null ? 0 : parent.hashCode());
	    result = prime * result + x;
	    result = prime * result + y;
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
		return true;
	    }
	    if (obj == null) {
		return false;
	    }
	    if (getClass() != obj.getClass()) {
		return false;
	    }
	    Node other = (Node) obj;
	    if (cost != other.cost) {
		return false;
	    }
	    if (depth != other.depth) {
		return false;
	    }
	    if (heuristic != other.heuristic) {
		return false;
	    }
	    if (parent == null) {
		if (other.parent != null) {
		    return false;
		}
	    } else if (!parent.equals(other.parent)) {
		return false;
	    }
	    if (x != other.x) {
		return false;
	    }
	    if (y != other.y) {
		return false;
	    }
	    return true;
	}

	@Override
	public int compareTo(Node node) {
	    return cost - node.cost;
	}

    }

    /**
     * The traversal map used for making sure any direction is traversable.
     */
    private static final TraversalMap TRAVERSAL_MAP = TraversalMap.getInstance();

    /**
     * A set of open nodes.
     */
    private final Set<Node> closed = new HashSet<>();

    /**
     * A set of closed nodes.
     */
    private final Set<Node> open = new HashSet<>();

    /**
     * Represents the current node.
     */
    private Node current;

    /**
     * An array of coordinate nodes.
     */
    private Node[][] nodes;

    @Override
    public Path find(Position position, int height, int width, int length, int srcX, int srcY, int dstX, int dstY, int size) {
	if (dstX < 0 || dstY < 0 || dstX >= width || dstY >= length) {
	    return null; // out of range
	}

	nodes = new Node[width][length];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < length; y++) {
		nodes[x][y] = new Node(x, y);
	    }
	}

	open.add(nodes[srcX][srcY]);

	while (open.size() > 0) {
	    current = getLowestCost();
	    if (current == nodes[dstX][dstY]) {
		break;
	    }
	    open.remove(current);
	    closed.add(current);

	    int x = current.getX();
	    int y = current.getY();

	    // south
	    if (y > 0 && TRAVERSAL_MAP.isTraversableSouth(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x][y - 1];
		examineNode(n);
	    }
	    // west
	    if (x > 0 && TRAVERSAL_MAP.isTraversableWest(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x - 1][y];
		examineNode(n);
	    }
	    // north
	    if (y < length - 1 && TRAVERSAL_MAP.isTraversableNorth(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x][y + 1];
		examineNode(n);
	    }
	    // east
	    if (x < width - 1 && TRAVERSAL_MAP.isTraversableEast(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x + 1][y];
		examineNode(n);
	    }
	    // south west
	    if (x > 0 && y > 0 && TRAVERSAL_MAP.isTraversableSouthWest(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x - 1][y - 1];
		examineNode(n);
	    }
	    // north west
	    if (x > 0 && y < length - 1 && TRAVERSAL_MAP.isTraversableNorthWest(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x - 1][y + 1];
		examineNode(n);
	    }

	    // south east
	    if (x < width - 1 && y > 0 && TRAVERSAL_MAP.isTraversableSouthEast(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x + 1][y - 1];
		examineNode(n);
	    }
	    // north east
	    if (x < width - 1 && y < length - 1 && TRAVERSAL_MAP.isTraversableNorthEast(height, position.getX() + x, position.getY() + y, size)) {
		Node n = nodes[x + 1][y + 1];
		examineNode(n);
	    }

	}

	if (nodes[dstX][dstY].getParent() == null) {
	    return null;
	}

	Path p = new Path();
	Node n = nodes[dstX][dstY];
	while (n != nodes[srcX][srcY]) {
	    p.addFirst(new Position(n.getX() + position.getX(), n.getY() + position.getY()));
	    n = n.getParent();
	}

	return p;
    }

    /**
     * Returns the node that has the lowest cost.
     *
     * @return The node with the lowest cost.
     */
    private Node getLowestCost() {
	Node curLowest = null;
	for (Node n : open) {
	    if (curLowest == null) {
		curLowest = n;
	    } else {
		if (n.getCost() < curLowest.getCost()) {
		    curLowest = n;
		}
	    }
	}
	return curLowest;
    }

    /**
     * Examines a node, if the cost of the next step is less than the current
     * nodes cost the node will be removed from both open and closed node sets.
     * If neither set contains the node it is added and the cost of the new node
     * is that of the next step.
     *
     * @param n The node to examine.
     */
    private void examineNode(Node n) {
	int heuristic = estimateDistance(current, n);
	int nextStepCost = current.getCost() + heuristic;
	if (nextStepCost < n.getCost()) {
	    open.remove(n);
	    closed.remove(n);
	}
	if (!open.contains(n) && !closed.contains(n)) {
	    n.setParent(current);
	    n.setCost(nextStepCost);
	    open.add(n);
	}
    }

    /**
     * Estimates a distance between the two nodes.
     *
     * @param src The source node.
     * @param dst The distance node.
     * @return The distance.
     */
    public int estimateDistance(Node src, Node dst) {
	int deltaX = Math.abs(src.getX() - dst.getX());
	int deltaY = Math.abs(src.getY() - dst.getY());
	return (deltaX + deltaY) * COST_STRAIGHT;
    }

}