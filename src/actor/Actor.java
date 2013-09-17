package actor;

import policy.Policy;
import environment.Coordinates;
import environment.Environment.action;
import environment.State;

public abstract class Actor {
	protected Coordinates coordinates;
	protected Policy policy;
	
	public Policy getPolicy() { return this.policy; }

	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	public void move(Coordinates c) {
		this.coordinates = c;
	}
	
	public action getNextMoveDirection(State s) { 
		return this.policy.getAction(s);
	}
	
	/**
	 * updates the coordinates according to the action taken.
	 */
	public void move(action a) { this.coordinates.shift(a); }
}
