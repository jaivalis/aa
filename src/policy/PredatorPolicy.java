package policy;

import action.PossibleActions;
import action.PredatorAction;
import environment.environment.state.State;
import statespace.CompleteStateSpace;

import java.util.HashMap;

public class PredatorPolicy extends Policy {
	
	public PredatorPolicy(CompleteStateSpace ss) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		for (State s : ss) {
			this.stateActionMapping.put(s, new PredatorAction());
		}
	}
	
	/** copy constructor */
//	public PredatorPolicy(Policy p) { this.stateActionMapping = p.stateActionMapping; }
}
