package policy;

import java.util.HashMap;

import action.PossibleActions;
import environment.Cell;
import environment.Environment.action;
import environment.State;

public abstract class Policy {
	protected HashMap<State, PossibleActions> stateActionMapping;
	
	public action getAction(Cell s) {
		return this.stateActionMapping.get(s).getAction();
	}
	
	public String getActionString(Cell s) {
		switch (this.stateActionMapping.get(s).getAction()) {
			case NORTH: return "^";
			case SOUTH:	return "V";
			case EAST:	return ">";
			case WEST:	return "<";
			case WAIT:	return "-";
			default:	return "?";			
		}
	}
	
	public double getActionProbability(State s, action a) {
		return this.stateActionMapping.get(s).getActionProbability(a);
	}
	
	/**
	 * For State s, set action probability of Action a to 1.0 and all the rest to 0.
	 * (Deterministic policy) 
	 */
	public void setUniqueAction(State s, action a) {
		if (a == null) { return; }
		this.stateActionMapping.get(s).setAllActionProbabilitiesTo(0.0);
		this.stateActionMapping.get(s).setActionProbability(a, 1.0);
	}

	public void initializeStateValues(double d) {
		for (State state : stateActionMapping.keySet()) {
			state.setStateValue(d);
		}		
	}
}
