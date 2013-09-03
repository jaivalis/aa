package aa2013;

import java.util.Random;

import aa2013.Board.direction;

public class Prey extends Actor {
	private boolean alive;

	public Prey(int x, int y) {
		this.x = x;
		this.y = y;
		this.alive = true;
	}

	@Override
	public String toString() { return "Prey (" + this.x + ", " + this.y + ")"; }

	public boolean getAlive() { return alive; }
	public void setAlive(boolean a) { this.alive = a; }

	/**
	 * Possibly called more than once so that prey won't move on predator 
	 */
	@Override
	public direction getNextMoveDirection() {
		Random r = new Random();
		int randint = r.nextInt(100);

		if (randint < 80) { return direction.WAIT; }
		if (randint < 85) { return direction.NORTH; }
		if (randint < 90) { return direction.SOUTH; }
		if (randint < 95) {	return direction.EAST; }
		return direction.WEST;
	}
}
