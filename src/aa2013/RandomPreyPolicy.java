package aa2013;

import java.util.HashMap;
import java.util.Random;

import aa2013.Environment.action;

public class RandomPreyPolicy extends Policy {

	public RandomPreyPolicy(Grid g) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		
		for (int i = 0; i < g.getDim(); i++) {
			for (int j = 0; j < g.getDim(); j++) {
				Coordinates pos = new Coordinates(i,j);
				this.stateActionMapping.put(g.getState(pos), new PreyAction());
			}
		}
	}
	
	@Override
	public action getAction(State s) {
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
