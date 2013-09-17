package actor;

import environment.Coordinates;
import environment.Grid;
import policy.RandomPreyPolicy;


public class Prey extends Actor {
	private boolean alive;

	public Prey(Grid g, Coordinates c) {
		this.coordinates = c;
		this.alive = true;
		this.policy = new RandomPreyPolicy(g);
	}

	@Override
	public String toString() { return "Prey (" + this.getCoordinates().getX() + ", " + this.getCoordinates().getY() + ")"; }

	public boolean getAlive() { return alive; }
	public void setAlive(boolean a) { this.alive = a; }
}
