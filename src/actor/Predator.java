package actor;

import environment.Coordinates;
import policy.PredatorPolicy;
import statespace.CompleteStateSpace;

public class Predator extends Actor {
	
	public Predator(Coordinates c, CompleteStateSpace ss) {
		this.coordinates = c;
		this.policy = new PredatorPolicy(ss);
	}
	
	/** copy constructor */
	public Predator(Predator p) {
		this.coordinates = p.coordinates;
		this.policy = p.policy;
	}

	@Override
	public String toString() { return "Predator (" + this.coordinates.getX() + ", " + this.coordinates.getY() + ")"; }
}
