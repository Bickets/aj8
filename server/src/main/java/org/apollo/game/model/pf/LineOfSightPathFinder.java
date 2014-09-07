package org.apollo.game.model.pf;

import org.apollo.game.model.Position;

/**
 * A basic {@link PathFinder} implementation which finds a line of sight of two
 * positions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Mikey Sasse
 */
public final class LineOfSightPathFinder extends PathFinder {

	@Override
	public Path find(Position source, Position destination) {
		int deltaX = destination.getX() - source.getX();
		int deltaY = destination.getY() - source.getY();

		while (deltaX >= 1 || deltaY >= 1 || deltaX <= -1 || deltaY <= -1) {
			deltaX = deltaX / 2;
			deltaY = deltaY / 2;
		}

		Position prev = source;
		Path path = new Path();

		while (!source.equals(destination)) {
			source = new Position(source.getX() + deltaX, source.getY() + deltaY);

			if (!prev.equals(source)) {
				path.addLast(source);
				prev = source;
			}
		}

		return path;
	}

}