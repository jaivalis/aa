package action;

import environment.Algorithms.action;
import state.State;

import java.util.ArrayList;
import java.util.List;

public class StateAction {
	private State s;
	private action a;
	
	public StateAction(State s, action a) {
		this.s = s;
		this.a = a;
	}

	public State getS() {
		return s;
	}
	
	public action getA() {
		return a;
	}
	
	public String toString() {
		return "("+this.s.toString()+","+this.a.toString()+")";
	}
	
    @Override
	public boolean equals(Object other) {
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof StateAction)) { return false; }
	    StateAction otherStateAction = (StateAction) other;
		return this.s.equals(otherStateAction.getS()) && this.a.equals(otherStateAction.getA());
	}

    @Override
    public int hashCode() {
    	String hashString = "" + this.s.hashCode() + this.a.hashCode();
        return hashString.hashCode();
    }
}
