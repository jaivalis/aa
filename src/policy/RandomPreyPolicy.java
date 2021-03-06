package policy;

import action.PossibleActions;
import action.PreyAction;
import state.State;
import statespace.StateSpace;

import java.util.HashMap;
import java.util.Random;

import environment.Algorithms.action;

public class RandomPreyPolicy extends Policy {

	public RandomPreyPolicy(StateSpace stateSpace) {
		this.stateActionMapping = new HashMap<State, PossibleActions>();
		for (State s : stateSpace) {
			this.stateActionMapping.put(s, new PreyAction());
		}
	}
	
	/** copy constructor */
	public RandomPreyPolicy(Policy p) {	this.stateActionMapping = p.stateActionMapping; }
	
	@Override
	public action getAction(State s) {
		Random r = new Random();
		float randfloat = r.nextFloat();

		if (randfloat < 0.8)	{ return action.WAIT; }
		if (randfloat < 0.85)	{ return action.NORTH; }
		if (randfloat < 0.9)	{ return action.SOUTH; }
		if (randfloat < 0.95)	{ return action.EAST; }
		return action.WEST;
	}

	@Override
	public double getActionProbability(State s, action a) {
		return this.stateActionMapping.get(s).getActionProbability(a);
	}
}