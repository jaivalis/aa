package aa2013;

import java.util.Random;

import aa2013.Board.direction;

public class Predator extends Actor {

	public Predator(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() { return "Predator (" + this.x + ", " + this.y + ")"; }

	/**
	 * Random moving policy.
	 */
	@Override
	public direction getNextMoveDirection() {
		Random r = new Random();
		int randint = r.nextInt(100);

		if (randint < 20) { return direction.WAIT; }
		if (randint < 40) { return direction.NORTH; }
		if (randint < 60) { return direction.SOUTH; }
		if (randint < 80) {	return direction.EAST; }
		return direction.WEST;
	}
}
