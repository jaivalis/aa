package aa2013;

import java.text.DecimalFormat;

public class State {

	private Actor actor;		// null if state cell is empty.
	private double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).
	private double stateReward;	// Corresponds to value R (for the policy evaluation algorithm).
	private Coordinates coordinates; // coordinates of cell.
	
	DecimalFormat twoDForm = new DecimalFormat("#.###");
	
	public State(Coordinates c) { 
		this.coordinates = c;
		this.actor = null; 
		this.stateValue = 0.0f; 
	}
	
	public Actor getActor()	{ return this.actor; }
	public Coordinates getCoordinates() { return this.coordinates; }
	public double getStateValue() {	return this.stateValue; }
	public double getStateReward() { return this.stateReward; }
	
	// set to null for removal
	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
	public void setActor(Actor actor) { this.actor = actor; }
	public void setStateReward(double stateReward) { this.stateReward = stateReward; }
	public void setCoordinates(Coordinates c) { this.coordinates = c; }

	@Override
	public String toString() { 
		return "(" + this.getCoordinates().getX() + ", "+ this.getCoordinates().getY() + ") V = " + twoDForm.format(stateValue);
	}
		
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof State)) { return false; }
	    State otherState = (State) other;
		return this.getCoordinates().equals(otherState.getCoordinates());
	}
}
