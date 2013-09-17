package environment;

import java.text.DecimalFormat;

import actor.Actor;

public class Cell {
	private Actor actor;		// null if state cell is empty.
	private Coordinates coordinates; // coordinates of cell.
	
	DecimalFormat twoDForm = new DecimalFormat("#.###");
	
	public Cell(Coordinates c) { 
		this.coordinates = c;
		this.actor = null; 
//		this.stateValue = 0.0f; 
	}
	
	public Actor getActor()	{ return this.actor; }
	public Coordinates getCoordinates() { return this.coordinates; }
//	public double getStateValue() {	return this.stateValue; }
//	public double getStateReward() { return this.stateReward; }
//	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
//	public void setStateReward(double stateReward) { this.stateReward = stateReward; }
	
	// set to null for removal

	public void setActor(Actor actor) { this.actor = actor; }
	public void setCoordinates(Coordinates c) { this.coordinates = c; }

//	public String toString() { 
//		return "(" + this.getCoordinates().getX() + ", "+ this.getCoordinates().getY() + ") V = " + twoDForm.format(stateValue);
//	}
		
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Cell)) { return false; }
	    Cell otherState = (Cell) other;
		return this.getCoordinates().equals(otherState.getCoordinates());
	}
}
