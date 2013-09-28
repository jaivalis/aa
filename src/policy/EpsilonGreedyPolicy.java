package policy;

import state.State;
import action.PossibleActions;
import environment.Environment;
import environment.Environment.action;
import environment.Q;
import environment.Util;

public class EpsilonGreedyPolicy extends Policy {
	Q q = null;
	public void setQ(Q q) {
		this.q = q;
	}
	
	public action getAction(State s) {
		
		Double epsilon_frac = Util.epsilon/((double)Environment.action.values().length);
		PossibleActions possibleActions = this.stateActionMapping.get(s);
		
		// set all probabilities to epsilon divided by number of actions
		possibleActions.setAllActionProbabilitiesTo(epsilon_frac);
		
		// find maximum action and set it to the greedy probability value (1 - epsilon + epsilon/size(A))
		action max_a = q.getArgmaxA(s);
		double greedy_prob = 1 - Util.epsilon + epsilon_frac;
		possibleActions.setActionProbability(max_a, greedy_prob);
		
		// stocastic query to get action for state s
		return super.getAction(s);
	}
}