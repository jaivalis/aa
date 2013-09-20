package action;

import environment.Environment.action;

import java.util.HashMap;
import java.util.Iterator;
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
