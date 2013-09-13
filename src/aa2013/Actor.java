package aa2013;

import aa2013.Environment.action;

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
}
