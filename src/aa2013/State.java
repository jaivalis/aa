package aa2013;

import java.text.DecimalFormat;

import aa2013.Board.action;

public class State {

	private Actor actor;
	private double stateValue;
	private double stateReward;
	private int x, y;
	
	DecimalFormat twoDForm = new DecimalFormat("#.##");
	
	public State(int x, int y) { 
		this.x = x;
		this.y = y;
		this.actor = null; 
		this.stateValue = 0.0f; 
	}	
	/**
	 * set the actor connected to this state
	 * @param Actor actor ; set as null for removal
	 */
	public void setActor(Actor actor) { this.actor = actor; }
	public Actor getActor()	{ return this.actor; }
	/**
	 * value of V for the state (for the policy evaluation algorithm)
	 * @param double stateValue
	 */
	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
	public void setStateReward(double stateReward) { this.stateReward = stateReward; }
	
	public void incrementStateValue(double v) { this.stateValue += v; }

	public double getStateValue() {	return this.stateValue; }
	public double getStateReward() { return this.stateReward; }

	public int getX() { return this.x; }
	public int getY() { return this.y; }
	
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
