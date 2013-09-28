package policy;

import java.util.HashMap;

import environment.Environment.action;

import state.State;

import action.PossibleActions;
import action.StateAction;

public class EpsilonGreedyPolicy extends Policy {
	HashMap<StateAction,Double> q = null;
	public void setQ(HashMap<StateAction,Double> q) {
		this.q = q;
	}
	
	public action getAction(State s) {
		PossibleActions possibleActions = this.stateActionMapping.get(s);
		return super.getAction(s);
	}
}