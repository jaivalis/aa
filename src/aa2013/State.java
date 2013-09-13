package aa2013;

import java.text.DecimalFormat;

import aa2013.Environment.action;

public class State {

	private Actor actor;		// null if state cell is empty.
	private double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).
	private double stateReward;	// Corresponds to value R (for the policy evaluation algorithm).
	private Coordinates coordinates; // coordinates of cell.
	
	DecimalFormat twoDForm = new DecimalFormat("#.##");
	
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
	public String toString() { return "(" + this.getCoordinates().getX() + ", "+ this.getCoordinates().getY() + ") V = " + Double.valueOf(twoDForm.format(stateValue)); }
	
	public action getTransitionAction(State other){
		int xOther = other.getCoordinates().getX(), yOther = other.getCoordinates().getY();
		int x = this.getCoordinates().getX();
		int y = this.getCoordinates().getX();
		if (x < xOther) { return action.NORTH; }
		if (x > xOther) { return action.SOUTH; }
		if (y < yOther) { return action.WEST; }
		if (y > yOther) { return action.EAST; }
		return action.WAIT;
	}
	
	public boolean sameAs(State other) {
		return this.getCoordinates().equals(other.getCoordinates());
	}
}
