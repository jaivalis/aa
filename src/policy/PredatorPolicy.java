package policy;

import java.util.HashMap;

import action.PossibleActions;
import action.PredatorAction;
import environment.Coordinates;
import environment.State;

/**
 * changed according to new State definition Sep 17.
 * @author aivalis
 */
public class PredatorPolicy extends Policy {
	
	public PredatorPolicy() {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		for (int i = 0; i < environment.Util.DIM; i++) {
			for (int j = 0; j < environment.Util.DIM; j++) {
				for (int k = 0; k < environment.Util.DIM; k++) {
					for (int l = 0; l < environment.Util.DIM; l++) {
						State state = new State();
						
						Coordinates preyC = new Coordinates(i, j);
						Coordinates predC = new Coordinates(k, l);

						state.setPrey(preyC);
						state.setPredator(predC);
						System.out.println(state.hashCode());
						this.stateActionMapping.put(state, new PredatorAction());
					}
				}
			}
		}
	}
	
	/** copy constructor */
	public PredatorPolicy(Policy p) {
		this.stateActionMapping = p.stateActionMapping;
	}
}
