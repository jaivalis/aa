package environment;

import action.StateAction;
import environment.Algorithms.action;
import state.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Q {
	HashMap<StateAction,Double> sa_d = new HashMap<StateAction,Double>();
	HashMap<State, HashSet<StateAction>> s_sa = new HashMap<State,HashSet<StateAction>>();

    /**
     * Sets the Q value of <state, action> to a given value.
     * @param s; State to set.
     * @param a; action of State to set.
     * @param d; value to set Q(s,a) to.
     */
	public void set(State s, action a, double d) {
		StateAction sa = new StateAction(s,a);
		this.sa_d.put(sa, d);
		
		// structure for cheap state-action retrieval
		HashSet<StateAction> sa_set = this.s_sa.get(s);
		if(sa_set == null){
			sa_set = new HashSet<StateAction>();
		}
        sa_set.add(sa);
		this.s_sa.put(s, sa_set);
	}

    /**
     * Returns an arrayList containing all the stateAction pairs associated to a state.
     * @param s; State in question
     * @return ArrayList<StateAction>; List of StateActions
     */
	public HashSet<StateAction> getStateActions(State s) {
		HashSet<StateAction> sa_set = this.s_sa.get(s);
		return sa_set;
	}

    /**
     * Returns the Q value for taking action a, in state s.
     * @param s; State in question
     * @param a; Action to be taken
     * @return double; Q value.
     */
	public double get(State s, action a) {
		HashSet<StateAction> sa_set = this.getStateActions(s);
		for(StateAction sa : sa_set) {
			if(sa.getA().equals(a)) {
				return this.sa_d.get(sa);
			}
		} // Unexpected behavior. bug. (Not reachable anymore)
		new Exception("[Bug in : Q.get()] "+s+" - "+a).printStackTrace();
		return -1;
	}

    /**
     * Returns the action that maximizes the return in state s.
     * @param s; State in question.
     * @return action; The optimal action.
     */
	public action getArgmaxA(State s) {
		HashSet<StateAction> sa_set = this.getStateActions(s);
		double max = Double.NEGATIVE_INFINITY;
		action argmax_a = null;
		for(StateAction sa : sa_set) {
			Double val = this.sa_d.get(sa);
			if(val > max) {
				max = val;
				argmax_a = sa.getA();
			}
		} return argmax_a;
	}

    /**
     * Returns the Q value that would be obtained if the optimal action was taken in a given state.
     * @param s; State in question.
     * @return double; The max Q value.
     */
	public double getMax(State s) {
		HashSet<StateAction> sa_set = this.getStateActions(s);
		double max = Double.NEGATIVE_INFINITY;
		for(StateAction sa : sa_set) {
			Double val = this.sa_d.get(sa);
			if(val > max) {
				max = val;
			}
		} return max;
	}

	public String toString() {
        String ret = "";
		for(State s : this.s_sa.keySet()){
			ret += s;
			HashSet<StateAction> sa_set = this.s_sa.get(s);
			for(StateAction sa : sa_set) {
				action a = sa.getA();
				Double d = this.sa_d.get(sa);
				ret += a.getShortName()+":"+d;
			}
		} return ret;
	}
	
	public void printMaxActionsGrid() {
		State[][] states = new State[11][11];
		for(State s : this.s_sa.keySet()){
			Coordinates c = s.getPreyCoordinates();
			states[c.getX()][c.getY()] = s;
		}
		for(int i = 0; i < states.length; i++){
			for(int j = 0; j < states[i].length; j++){
				action a = this.getArgmaxA(states[i][j]);
				System.out.print(a.getArrow() + "\t");
				// System.out.print(this.board[i][j].getStateValue() + "\t");
			}
			System.out.println();
		}
	}
}
