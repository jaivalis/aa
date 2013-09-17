package actor;

import policy.PredatorPolicy;
import environment.Coordinates;

public class Predator extends Actor {
	
	public Predator(Coordinates c) {
		this.coordinates = c;
		this.policy = new PredatorPolicy();
	}
	
	/**
	 * copy constructor
	 */
	public Predator(Predator p) {
		this.coordinates = p.coordinates;
		this.policy = new PredatorPolicy();
	}

	@Override
	public String toString() { return "Predator (" + this.coordinates.getX() + ", " + this.coordinates.getY() + ")"; }
}
