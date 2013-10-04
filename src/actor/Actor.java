package actor;

import environment.Coordinates;
import environment.Algorithms.action;
import policy.Policy;
import state.State;

public abstract class Actor {
	protected Coordinates coordinates;
	protected Policy policy;
	
	public Policy getPolicy() { return this.policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }

	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	/**
	 * Move the specific actor to the given coordinates.
	 * @param c; Coordinates to move Actor to.
	 */
	public void setCoordinates(Coordinates c) { this.coordinates = c; }
	/**
	 * Updates the coordinates according to the action taken.
	 */
	public void move(action a) { this.coordinates = this.coordinates.getShifted(a); }
	/**
	 * Move the Actor according to an action picked from the policy of the Actor.
	 * @param s; The current state.
	 */
	public void move(State s) {	this.move(this.getNextMoveDirection(s)); }
	
	/**
	 * Returns the action the actor would take in state s.
	 * @param s; The state in question.
	 */
	public action getNextMoveDirection(State s) { return this.policy.getAction(s); }
}
