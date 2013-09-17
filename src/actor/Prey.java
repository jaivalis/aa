package actor;

import policy.RandomPreyPolicy;
import environment.Coordinates;
import environment.StateSpace;


public class Prey extends Actor {
	private boolean alive;

	public Prey(Coordinates c, StateSpace ss) {
		this.coordinates = c;
		this.alive = true;
		this.policy = new RandomPreyPolicy(ss);
	}
	
	/** copy constructor */
	public Prey(Prey p) {
		this.coordinates = p.coordinates;
		this.alive = p.alive;
		this.policy = new RandomPreyPolicy(p.policy);
	}

	@Override
	public String toString() { return "Prey (" + this.getCoordinates().getX() + ", " + this.getCoordinates().getY() + ")"; }

	public boolean getAlive() { return alive; }
	public void setAlive(boolean a) { this.alive = a; }
}
