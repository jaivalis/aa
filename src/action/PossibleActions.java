package action;

import environment.Environment.action;

import java.util.HashMap;
import java.util.Iterator;

/**
 * this class represents a specific part of a policy: 
 * 	the association between *a single state* and a set of possible actions
 *  that might be good for that particular state.
 *  Each of the specified actions has a probability of choice associated.
 *  This means that the agent will choose randomly and according to
 *  that probability distribution an action.
 *  Please note that a deterministic choice CAN be implemented with this system:
 *  it's just enough to set the probability of an action to 1 and the other actions to 0.
 * @author Francesco Stablum
 *
 */
public abstract class PossibleActions {
	
	protected HashMap<action, Double> actionProbability;
	
	public abstract action getAction();

	public double getActionProbability(action a) { return this.actionProbability.get(a); }	
	public void setActionProbability(action a, double p) { this.actionProbability.put(a, p); }
	
	public void setAllActionProbabilitiesTo(double p) { 
		Iterator<action> it = this.actionProbability.keySet().iterator();
		while(it.hasNext()){
			this.actionProbability.put(it.next(), p);
		}
	}

	public String toString() {
		return this.actionProbability.toString();
	}
}
