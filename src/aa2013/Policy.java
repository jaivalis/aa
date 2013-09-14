package aa2013;

import java.util.HashMap;

import aa2013.Environment.action;

public abstract class Policy {
	protected HashMap<State, PossibleActions> stateActionMapping;
	
	public action getAction(State s) {
		return this.stateActionMapping.get(s).getAction();
	}
	
	public String getActionString(State s) {
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
	
	public void setUniqueAction(State s, action a) {
		if (a == null) { return; } // bug fix (?) 1
		this.stateActionMapping.get(s).setAllActionProbabilitiesTo(0.0);
		this.stateActionMapping.get(s).setActionProbability(a, 1.0);
	}
}
