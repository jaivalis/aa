package state;

import environment.Coordinates;
import environment.Util;

public abstract class State {
    protected Coordinates preyC;
    protected Coordinates predC;
    protected double stateValue;	// Corresponds to value V (for the policy evaluation algorithm).

    public double getStateValue() {	return this.stateValue; }

    public double getStateReward() {
        if (this.predC.equals(this.preyC)) {
            return Util.PREYREWARD;
        } return 0.0;
    }

    public void setStateValue(double stateValue) { this.stateValue = stateValue; }

    public void setPrey(Coordinates c) { this.preyC = c; }

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
	    if (!(other instanceof CompleteState)) { return false; }
	    CompleteState otherState = (CompleteState) other;
		return this.predC == otherState.predC && this.preyC == otherState.preyC;
	}

    @Override
    public int hashCode() {
    	String hashString = "1" + this.predC.getX() + this.predC.getY() + this.preyC.getX() + this.preyC.getY();
        return Integer.parseInt(hashString);
    }

    public Coordinates getPreyCoordinates() { return this.preyC; }
}
