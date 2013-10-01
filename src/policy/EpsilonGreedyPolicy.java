package policy;

import action.PossibleActions;
import environment.Algorithms;
import environment.Algorithms.action;
import environment.Q;
import environment.Util;
import state.State;
import statespace.StateSpace;

public class EpsilonGreedyPolicy extends PredatorPolicy {
	Q q = null;

    public EpsilonGreedyPolicy(StateSpace ss) {
    	super(ss);
    }
    
    public void setQ(Q q) {
    	this.q = q;
    }

    @Override
	public action getAction(State s) {
		
		if(this.q == null) {
			new Exception("EpsilonGreedyPolicy: this.q is not set. Please call setQ before!").printStackTrace();
			System.exit(0);
		}
		
		Double epsilon_frac = Util.epsilon/((double)Algorithms.action.values().length);
		PossibleActions possibleActions = this.stateActionMapping.get(s);
		if(possibleActions == null) {
			new Exception("this.stateActionMapping.get(s)").printStackTrace();
			System.exit(0);
		}
		
		// set all probabilities to epsilon divided by number of actions
		possibleActions.setAllActionProbabilitiesTo(epsilon_frac);
		
		// find maximum action and set it to the greedy probability value (1 - epsilon + epsilon/size(A))
		action max_a = this.q.getArgmaxA(s);
		double greedy_prob = 1 - Util.epsilon + epsilon_frac;
		possibleActions.setActionProbability(max_a, greedy_prob);
		
		// stochastic query to get action for state s
		return super.getAction(s);
	}
}