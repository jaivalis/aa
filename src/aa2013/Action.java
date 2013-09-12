package aa2013;

import java.util.HashMap;

import aa2013.Environment.action;
public abstract class Action {
	
	protected HashMap<action, Double> actionProbability;
	
	public abstract action getAction();

	public double getActionProbability(action a) { return this.actionProbability.get(a); }	
	public void setActionProbability(action a, double p) { this.actionProbability.put(a, p); }
}
