package policy;

import action.PossibleActions;
import action.PredatorAction;
import state.State;
import statespace.StateSpace;

import java.util.HashMap;

public class PredatorPolicy extends Policy {

	public PredatorPolicy(StateSpace ss) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		for (State s : ss) {
			this.stateActionMapping.put(s, new PredatorAction());
		}
	}
	
	/** copy constructor */
//	public PredatorPolicy(Policy p) { this.stateActionMapping = p.stateActionMapping; }
}
