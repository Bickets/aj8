package org.apollo.game.model.pf;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apollo.game.model.Position;

/**
 * An implementation of a {@link PathFinder} which uses the A* search algorithm.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Major | Suggestions, discussion
 */
public final class AStarPathFinder implements PathFinder {

	/**
	 * The cost of moving in a straight line.
	 */
	private static final int COST_STRAIGHT = 10;

	/**
	 * Represents a node used by the A* algorithm.
	 *
	 * @author Graham
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	private static final class Node implements Comparable<Node> {

		/**
		 * The parent node.
		 */
		private Node parent;

		/**
		 * The cost.
		 */
		private int cost;

		/**
		 * Whether or not this node is open.
		 */
		private boolean open = true;

		/**
		 * The position of this node.
		 */
		private final Position position;

		/**
		 * Constructs a new {@link Node}.
		 *
		 * @param position The position of this node.
		 */
		public Node(Position position) {
			this.position = Objects.requireNonNull(position);
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
		 * Tests whether or not this node is open.
		 */
		public boolean isOpen() {
			return open;
		}

		/**
		 * Closes this node.
		 */
		public void close() {
			open = false;
		}

		/**
		 * Returns the position of this node.
		 */
		public Position getPosition() {
			return position;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cost;
			result = prime * result + Objects.hashCode(parent);
			result = prime * result + position.hashCode();
			result = prime * result + Boolean.hashCode(open);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Node) {
				Node other = (Node) obj;
				return cost == other.cost && Objects.equals(parent, other.parent) && position.equals(other.position) && open == other.open;
			}

			return false;
		}

		@Override
		public int compareTo(Node node) {
			return cost - node.cost;
		}

	}

	/**
	 * The traversal map used for making sure any direction is traversable.
	 */
	private final TraversalMap traversalMap;

	/**
	 * Constructs a new {@link AStarPathFinder} with the specified traversal
	 * map.
	 *
	 * @param traversalMap The traversal map.
	 */
	public AStarPathFinder(TraversalMap traversalMap) {
		this.traversalMap = traversalMap;
	}

	@Override
	public Deque<Position> find(Position base, Position origin, Position destination, int size) {
		Map<Position, Node> nodes = new HashMap<>();

		Node start = new Node(origin);
		Node end = new Node(destination);

		nodes.put(start.getPosition(), start);
		nodes.put(end.getPosition(), end);

		Set<Node> open = new HashSet<>();
		Queue<Node> sorted = new PriorityQueue<>();

		open.add(start);
		sorted.add(start);

		do {
			Node active = getCheapest(sorted);

			if (active.equals(end)) {
				break;
			}

			open.remove(active);
			active.close();

			int x = active.getPosition().getX();
			int y = active.getPosition().getY();

			if (traversalMap.isTraversableSouth(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x, y - 1), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableWest(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x - 1, y), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableNorth(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x, y + 1), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableEast(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x + 1, y), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableSouthWest(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x - 1, y - 1), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableNorthWest(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x - 1, y + 1), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableSouthEast(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x + 1, y - 1), nodes);
				compare(active, other, open, sorted);
			}

			if (traversalMap.isTraversableNorthEast(base.getHeight(), base.getX() + x, base.getY() + y, size)) {
				Node other = createIfAbsent(new Position(x + 1, y + 1), nodes);
				compare(active, other, open, sorted);
			}

		} while (!open.isEmpty());

		Node active = end;

		Deque<Position> shortest = new ArrayDeque<>();

		if (active.getParent() != null) {
			Position position = active.getPosition();

			while (!active.equals(start)) {
				shortest.addFirst(new Position(position.getX() + base.getX(), position.getY() + base.getY()));
				active = active.getParent();
				position = active.getPosition();
			}
		}

		return shortest;
	}

	/**
	 * Gets the cheapest open {@link Node} from the {@link Queue}.
	 *
	 * @param nodes The queue of nodes.
	 * @return The cheapest node.
	 */
	private Node getCheapest(Queue<Node> nodes) {
		Node node = nodes.peek();
		while (!node.isOpen()) {
			nodes.poll();
			node = nodes.peek();
		}

		return node;
	}

	/**
	 * Compares the two specified {@link Node}s, adding the other node to the
	 * open {@link Set} if the estimation is cheaper than the current cost.
	 *
	 * @param active The active node.
	 * @param other The node to compare the active node against.
	 * @param open The set of open nodes.
	 * @param sorted The sorted {@link Queue} of nodes.
	 */
	private void compare(Node active, Node other, Set<Node> open, Queue<Node> sorted) {
		int cost = active.getCost() + estimateDistance(active.getPosition(), other.getPosition());

		if (other.getCost() > cost) {
			open.remove(other);
			other.close();
		} else if (other.isOpen() && !open.contains(other)) {
			other.setCost(cost);
			other.setParent(active);
			open.add(other);
			sorted.add(other);
		}
	}

	/**
	 * Creates a {@link Node} and inserts it into the specified {@link Map} if
	 * one does not already exist, then returns that node.
	 *
	 * @param position The {@link Position}.
	 * @param nodes The map of positions to nodes.
	 * @return The node.
	 */
	private Node createIfAbsent(Position position, Map<Position, Node> nodes) {
		Node existing = nodes.get(position);
		if (existing == null) {
			existing = new Node(position);
			nodes.put(position, existing);
		}

		return existing;
	}

	/**
	 * A heuristic to estimate the distance between two positions.
	 *
	 * @param position The starting position.
	 * @param destination The destination position.
	 * @return The estimated distance between the two positions.
	 */
	private int estimateDistance(Position position, Position destination) {
		int deltaX = position.getX() - destination.getX();
		int deltaY = position.getY() - destination.getY();
		return (Math.abs(deltaX) + Math.abs(deltaY)) * COST_STRAIGHT;
	}

}