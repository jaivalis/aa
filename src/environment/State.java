package environment;

import actor.Actor;
import actor.Predator;
import actor.Prey;
import environment.Environment.action;

public class State {
//	private Cell[][] cells; obsolete
//	private Prey prey;
//	private Predator predator;
	
	private Coordinates preyC, predC;
	
	private double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).
	
	public State() {}
	
//	public Cell getState(Coordinates pos) { return this.cells[pos.getX()][pos.getY()]; }

	/**
	 * returns the new State that occurs after predator takes action a.
	 */
	public State getNextState(action a) {
		State nextState = new State();
		nextState.setPrey(this.preyC);
		nextState.setPredator(this.predC.shift(a));
		return nextState;
	}
	
//	public Cell getState(Actor a) { return this.getState(a.getCoordinates()); }

	public double getStateValue() {	return this.stateValue; }
	public double getStateReward() { 
		if (this.predC.equals(this.preyC)) {
			return Util.PREYREWARD;
		}
//		if (this.prey.getCoordinates().equals(predator)) {
//			return Util.PREYREWARD;
//		} 
		return 0.0;
	}
	
//	private void collisionDetection() {
//		if (this.predC.equals(this.preyC)) {
//			return;
//		}
////		if (predator.getCoordinates().equals(prey.getCoordinates())) { prey.setAlive(false); }
//	}
	
	public void setStateValue(double stateValue) { this.stateValue = stateValue; }
	
	/**
	 * Set Actor a to Coordinates c. Will update stateReward of state if actor is instance of Prey.
	 * @param a
	 * @param c
	 */
//	public void setActor(Actor a, Coordinates c) {
//		if (a instanceof Prey) {
//			this.cells[c.getX()][c.getY()].setStateReward(PREYREWARD);
//		}
//		this.cells[c.getX()][c.getY()].setActor(a);
//	}
	public void setPrey(Coordinates c) {
		this.preyC = c;
	}
	public void setPredator(Coordinates c) {
		this.predC = c;
	}
	public void setActor(Actor a) {
		if (a instanceof Prey) {
			this.preyC = a.getCoordinates();
//			this.prey = (Prey) a;
		} else if (a instanceof Predator) {
			this.predC = a.getCoordinates();
//			this.predator = (Predator) a;
		}
	}
		
//	public void initializeStateValues(double d) {
//		for(int i = 0; i < this.cells.length; i++) {
//			for(int j = 0; j < this.cells[i].length; j++) {
//				this.cells[i][j].setStateValue(d);
//			}
//		}
//	} 
	
//	/**
//	 * Update the board according to Actor move.
//	 */
//	public void moveActor(Actor a, Coordinates c) {
//		Coordinates oldPos = a.getCoordinates();
//		this.cells[oldPos.getX()][oldPos.getY()].setActor(null);
//
//		if (a instanceof Prey) { // move reward
//			this.cells[oldPos.getX()][oldPos.getY()].setStateReward(0);
//			this.cells[c.getX()][c.getY()].setStateReward(this.PREYREWARD);
//		}
//		a.move(c);
//		this.cells[oldPos.getX()][oldPos.getY()].setActor(a);
//	}
//
//	/**
//	 * Returns the Reward obtained by taking action a in State s. 
//	 */
//	public double getActionReward(Cell s, action a) {
//		Cell nearby = this.getNextState(s, a);
//		return nearby.getStateReward();
//	}
//
//	/**
//	 * Returns the State that will occur after taking action a in State s
//	 */
//	public State getNextState(action a) {
//		Coordinates nearbyPos = this.nearbyCoordinates(s.getCoordinates(), a);
//		Cell ret = this.getState(nearbyPos);
//		return ret;
//	}
//
//	/**
//	 * Returns the coordinates of the block to be visited next according to the action chosen.
//	 */
//	public Coordinates nearbyCoordinates(Coordinates orig, action a) {
//		Coordinates dest = null;
//		switch (a) {
//			case WAIT:
//				dest = orig.getCopy();
//				break;
//			case NORTH:
//				dest = new Coordinates(
//					orig.getX() - 1 < 0 
//						? dim - 1 
//						: orig.getX() - 1,
//					orig.getY()
//				);
//				break;
//			case SOUTH:
//				dest = new Coordinates(
//					orig.getX() + 1 > dim - 1 
//						? 0 
//						: orig.getX() + 1,
//					orig.getY()
//				);
//				break;
//			case EAST:
//				dest = new Coordinates(
//					orig.getX(),
//					orig.getY()+1 > dim-1
//						? 0
//					    : orig.getY() + 1
//					);
//				break;
//			case WEST:
//				dest = new Coordinates(
//					orig.getX(),
//					orig.getY() - 1 < 0
//						? dim - 1
//						: orig.getY() - 1
//				);
//				break;
//		}
//		return dest;
//	}
//	
//	/**
//	 * Returns the action required to move from this state to state other.
//	 * (requires this to be neighboring to other)
//	 */
//	public action getTransitionAction(Cell s1, Cell s2) {		
//		int x = s1.getCoordinates().getX(), y = s1.getCoordinates().getY();
//		int xOther = s2.getCoordinates().getX(), yOther = s2.getCoordinates().getY();
//		if (x == xOther	&& y == yOther) { return action.WAIT; }
//		if (x == xOther - 1 || (xOther == 0 && x == 10)) { return action.SOUTH; }
//		if (x == xOther + 1 || (xOther == dim-1 && x == 0)) { return action.NORTH; }
//		if (y == yOther - 1 || (yOther == 0 && y == 10)) { return action.EAST; }
//		if (y == yOther + 1 || (yOther == dim-1 && y == 0)) { return action.WEST; }
//		return null;
//	}
//	
//	/**
//	 * returns all the states that can be visited from coordinates x,y
//	 */
//	public ArrayList<Cell> getNeighbors(Coordinates c) {
//		ArrayList<Cell> neighbours = new ArrayList<Cell>();
//		
//		for(action a : Environment.action.values()) {
//			neighbours.add(this.getState(this.nearbyCoordinates(c, a)));
//		}
//		
//		return neighbours;
//	}
	
//	/**
//	 * used for debugging
//	 */
//	public String toString() {
//		String ret = "";
//		DecimalFormat twoDForm = new DecimalFormat("#.###");
//		for(int i = 0; i < this.cells.length; i++){
//			for(int j = 0; j < this.cells[i].length; j++){				
//				ret += twoDForm.format(this.cells[i][j].getStateValue()) + "\t";
//				// System.out.print(this.board[i][j].getStateValue() + "\t");
//			}
//			System.out.println();
//		}
//		ret += twoDForm.format(this.cells[5][5].getStateValue()) + "\t";
//		return ret;
//	}	
//
//	/**
//	 * used for debugging
//	 */
//	public void printActions(Policy policy) {
//		for(int i = 0; i < this.cells.length; i++){
//			for(int j = 0; j < this.cells[i].length; j++){
//				System.out.print(policy.getActionString(this.cells[i][j]) + "\t");
//				// System.out.print(this.board[i][j].getStateValue() + "\t");
//			}
//			System.out.println();
//		}
//	}
//	
	@Override
	public String toString() {
		String ret = "";
		ret += "Prey : " + this.preyC + " Predator : " + this.predC;
		return ret;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof State)) { return false; }
	    State otherState = (State) other;
		return this.predC == otherState.predC && this.preyC == otherState.preyC;		
	}
	
	/** Used by hashmap */
    @Override
    public int hashCode() {
    	String hashString = "1";
    	hashString += "" + this.predC.getX() + this.predC.getY() + this.preyC.getX() + this.preyC.getY();
    	int hash = Integer.parseInt(hashString);
        return hash;
    }

//	public void setPredator(Coordinates c) { this.predator = new Predator(this, c); }	
//	public void setPrey(Coordinates c) { this.prey = new Prey(this, c); }

//	public Predator getPredator() { return this.predator; }
//	public Prey getPrey() { return this.prey; }

}
