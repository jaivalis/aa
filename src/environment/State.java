package environment;

import actor.Actor;
import actor.Predator;
import actor.Prey;
import environment.Environment.action;

public class State {	
	private Coordinates preyC, predC;	
	private double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).
	
	public State() {}

	/**
	 * Constructor method.
	 * @param i; prey x coordinate.
	 * @param j; prey y coordinate.
	 * @param k; predator x coordinate.
	 * @param l; predator y coordinate.
	 */
	public State(int i, int j, int k, int l) {
		this.preyC = new Coordinates(i, j);
		this.predC = new Coordinates(k, l);
	}

    public State(Coordinates preyC, Coordinates predC) {
        this.preyC = preyC;
        this.predC = predC;
    }
	
	public State(Prey p, Predator pp) {
		this.preyC = p.getCoordinates();
		this.predC = pp.getCoordinates();
	}

	/**
	 * returns the new State that occurs after predator takes action a.
	 */
	public State getNextState(action a) {
		State nextState = new State();
		nextState.setPrey(this.preyC);
		nextState.setPredator(this.predC.getShifted(a));
		return nextState;
	}

	public double getStateValue() {	return this.stateValue; }

	public double getStateReward() {
		if (this.predC.equals(this.preyC)) {
			return Util.PREYREWARD;
		} return 0.0;
	}
	
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
		ret += "Prey : " + this.preyC + " Predator : " + this.predC + " Value = " + this.stateValue;
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
    	String hashString = "1" + this.predC.getX() + this.predC.getY() + this.preyC.getX() + this.preyC.getY();
        return Integer.parseInt(hashString);
    }

	public Coordinates getPreyCoordinates() { return this.preyC; }
	public Coordinates getPredatorCoordinates() { return this.predC; }
}
