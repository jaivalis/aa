package policy;

import action.LearnedAction;
import action.PossibleActions;
import environment.Algorithms;
import environment.Coordinates;
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

    public String getActionString(State s) {
        switch (this.stateActionMapping.get(s).getRandomAction()) {
            case NORTH: return "^";
            case SOUTH:	return "V";
            case EAST:	return ">";
            case WEST:	return "<";
            case WAIT:	return "-";
            default:	return "?";
        }
    }

	public double getActionProbability(State s, action a) {
		return this.stateActionMapping.get(s).getActionProbability(a);
	}

	/**
	 * gets action with maximum probability of being chosen, given a state
	 * @param State s
	 * @return
	 */
	public Algorithms.action getArgMaxActionProbability(State s) {
		Algorithms.action argmax_a = null;
		double max = Double.NEGATIVE_INFINITY;
		for(Algorithms.action a : Algorithms.action.values()) {
			Double p = this.stateActionMapping.get(s).getActionProbability(a);
			if(p>max){
				max = p;
				argmax_a = a;
			}
		}
		return argmax_a;
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

    /**
     * Initializes all State actions to a given arbitrary action.
     * @param ac The action to which all the states are initialized.
     */
    public void initializeActionsArbitrarily(action ac) {
        for (State state : this.stateActionMapping.keySet()) {
            this.stateActionMapping.get(state).makeActionDeterministic(ac);
        }
    }
	
    public void printMaxActionsGrid() {
		State[][] states = new State[11][11];
		for(State s : this.stateActionMapping.keySet()){
			Coordinates c = s.getPreyCoordinates();
			states[c.getX()][c.getY()] = s;
		}
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 11; j++){
				action a = this.getArgMaxActionProbability(states[i][j]);
				System.out.print(a.getArrow() + "\t");
				// System.out.print(this.board[i][j].getStateValue() + "\t");
			}
			System.out.println();
		}
	}
}
