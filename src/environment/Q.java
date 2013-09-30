package environment;

import java.util.ArrayList;
import java.util.HashMap;

import state.State;
import action.StateAction;
import environment.Environment.action;

public class Q {
	HashMap<StateAction,Double> sa_d = new HashMap<StateAction,Double>();
	HashMap<State, ArrayList<StateAction>> s_sa = new HashMap<State,ArrayList<StateAction>>();

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
		ArrayList<StateAction> sa_list = this.s_sa.get(s);
		if(sa_list == null){
			sa_list = new ArrayList<StateAction>();
		}
        sa_list.add(sa);
		this.s_sa.put(s, sa_list);
	}

    /**
     * Returns an arrayList containing all the stateAction pairs associated to a state.
     * @param s; State in question
     * @return ArrayList<StateAction>; List of StateActions
     */
	public ArrayList<StateAction> getStateActions(State s) {
		ArrayList<StateAction> sa_list = this.s_sa.get(s);
		return sa_list;
	}

    /**
     * Returns the Q value for taking action a, in state s.
     * @param s; State in question
     * @param a; Action to be taken
     * @return double; Q value.
     */
	public double get(State s, action a) {
		ArrayList<StateAction> sa_list = this.getStateActions(s);
		for(StateAction sa : sa_list) {
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
		ArrayList<StateAction> sa_list = this.getStateActions(s);
		double max = Double.NEGATIVE_INFINITY;
		action argmax_a = null;
		for(StateAction sa : sa_list) {
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
		ArrayList<StateAction> sa_list = this.getStateActions(s);
		double max = Double.NEGATIVE_INFINITY;
		for(StateAction sa : sa_list) {
			Double val = this.sa_d.get(sa);
			if(val > max) {
				max = val;
			}
		} return max;
	}
}
