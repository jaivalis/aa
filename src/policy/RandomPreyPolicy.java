package policy;

import java.util.HashMap;
import java.util.Random;

import action.PossibleActions;
import action.PreyAction;
import actor.Predator;
import actor.Prey;
import environment.Cell;
import environment.Coordinates;
import environment.Environment.action;
import environment.State;

/**
 * changed according to new State definition Sep 17.
 * @author aivalis
 */
public class RandomPreyPolicy extends Policy {

	public RandomPreyPolicy(State g) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		
		for (int i = 0; i < environment.Util.DIM; i++) {
			for (int j = 0; j < environment.Util.DIM; j++) {
				for (int k = 0; k < environment.Util.DIM; k++) {
					for (int l = 0; l < environment.Util.DIM; l++) {
						State currState = new State();
						
						Coordinates preyC = new Coordinates(i, j);
						Coordinates predC = new Coordinates(k, l); 

						currState.setActor(new Predator(currState, predC));
						currState.setActor(new Prey(currState, preyC));
						this.stateActionMapping.put(currState, new PreyAction());
					}
				}
			}
		}
	}
	
	/**
	 * copy constructor
	 */
	public RandomPreyPolicy(Policy p) {
		this.stateActionMapping = p.stateActionMapping;
	}
	
	@Override
	public action getAction(Cell s) {
		Random r = new Random();
		float randint = r.nextFloat();

		if (randint < 0.8)	{ return action.WAIT; }
		if (randint < 0.85)	{ return action.NORTH; }
		if (randint < 0.9)	{ return action.SOUTH; }
		if (randint < 0.95)	{ return action.EAST; }
		return action.WEST;
	}

	@Override
	public double getActionProbability(State s, action a) {
		return this.stateActionMapping.get(s).getActionProbability(a);
	}

}
