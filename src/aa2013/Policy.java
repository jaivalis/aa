package aa2013;

import java.util.HashMap;

import aa2013.Environment.action;

public abstract class Policy {
	protected HashMap<State, Action> stateActionMapping;
	
	public action getAction(State s) {
		return this.stateActionMapping.get(s).getAction();
	}
	
	public double getActionProbability(State s, action a) {
		return this.stateActionMapping.get(s).getActionProbability(a);
	}
}
