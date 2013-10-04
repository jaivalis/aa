package policy;

import state.State;
import statespace.StateSpace;
import action.LearnedAction;
import environment.Algorithms;
import environment.Util;
import environment.Algorithms.action;

public class MCEpsilonGreedyPolicy extends PredatorPolicy {
	public MCEpsilonGreedyPolicy(StateSpace ss) {
		super(ss);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Override
	 * For State s, set action probability of Action a to 1-epsilon+epsilon_frac and all the rest to epsilon_frac.
	 * (Non-deterministic epsilon-greedy policy) 
	 */
	public void setUniqueAction(State s, action a) {
		if (a == null) { return; }
		if(!this.stateActionMapping.containsKey(s)){
			this.stateActionMapping.put(s, new LearnedAction());
		}
		Double epsilon_frac = Util.epsilon/((double)Algorithms.action.values().length);
		double greedy_prob = 1 - Util.epsilon + epsilon_frac;

		this.stateActionMapping.get(s).setAllActionProbabilitiesTo(epsilon_frac);
		this.stateActionMapping.get(s).setActionProbability(a, greedy_prob);
	}
}
