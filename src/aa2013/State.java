package aa2013;

/**
 * a cell is a state for the predator
 */
public class State {

	private Actor actor;
	private double stateValue;
	
	public State() { this.actor = null; this.stateValue = 0.0f; }
	
	/**
	 * set the actor connected to this state
	 * @param Actor actor ; set as null for removal
	 */
	public void setActor(Actor actor) { this.actor = actor; }
	
	/**
	 * value of V for the state (for the policy evaluation algorithm)
	 * @param double stateValue
	 */
	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
	public void increaseStateValue(double stateValue) { this.stateValue += stateValue; }
	
	public double getStateValue() {	return this.stateValue; }
	
	public String toString() { return ""; }
}
