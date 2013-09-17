package actor;

import environment.Coordinates;
import environment.Grid;
import policy.PredatorPolicy;

public class Predator extends Actor {
	
	public Predator(Grid g, Coordinates c) {
		this.coordinates = c;
		this.policy = new PredatorPolicy(g);
	}

	@Override
	public String toString() { return "Predator (" + this.coordinates.getX() + ", " + this.coordinates.getY() + ")"; }
}
