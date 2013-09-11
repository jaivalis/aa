package aa2013;

import java.text.DecimalFormat;

import aa2013.Board.action;

public class State {

	private Actor actor;		// null if state cell is empty.
	private double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).
	private double stateReward;	// Corresponds to value R (for the policy evaluation algorithm).
	private int x, y;			// coordinates of cell.
	
	DecimalFormat twoDForm = new DecimalFormat("#.##");
	
	public State(int x, int y) { 
		this.x = x;
		this.y = y;
		this.actor = null; 
		this.stateValue = 0.0f; 
	}
	
	public Actor getActor()	{ return this.actor; }
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public double getStateValue() {	return this.stateValue; }
	public double getStateReward() { return this.stateReward; }
	
	// set to null for removal
	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
	public void setActor(Actor actor) { this.actor = actor; }
	public void setStateReward(double stateReward) { this.stateReward = stateReward; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }

	@Override
	public String toString() { return "(" + x + ", "+ y + ") V = " + Double.valueOf(twoDForm.format(stateValue)); }
	
	public action getTransitionAction(State other){
		int xOther = other.getX(), yOther = other.getY();		
		if (this.x < xOther) { return action.NORTH; }
		if (this.x > xOther) { return action.SOUTH; }
		if (this.y < yOther) { return action.WEST; }
		if (this.y > yOther) { return action.EAST; }
		return action.WAIT;
	}
}
