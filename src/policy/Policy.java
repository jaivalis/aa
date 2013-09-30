package policy;

import action.LearnedAction;
import action.PossibleActions;
import environment.Algorithms.action;
import state.State;

import java.util.HashMap;

public abstract class Policy {
	
	protected HashMap<State, PossibleActions> stateActionMapping;
	
	protected Policy(){
		this.stateActionMapping = new HashMap<State, PossibleActions>();
	}
	
	/**
	 * Chooses an stochastic action, according to the probabilities associated.
	 * @param s
	 * @return
	 */
	public action getAction(State s) {
        PossibleActions ac = this.stateActionMapping.get(s);
		return ac.getRandomAction();
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
		if(!this.stateActionMapping.containsKey(s)){
			this.stateActionMapping.put(s, new LearnedAction());
		}
		this.stateActionMapping.get(s).setAllActionProbabilitiesTo(0.0);
		this.stateActionMapping.get(s).setActionProbability(a, 1.0);
	}

	public void initializeStateValues(double d) {
		for (State state : this.stateActionMapping.keySet()) {
			state.setStateValue(d);
		}
	}
}
