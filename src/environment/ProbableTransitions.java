package environment;

import state.State;

import java.util.HashMap;
import java.util.Set;

/**
 * contains some states and probabilities associates to jump into them
 * @author stablum
 *
 */
public class ProbableTransitions {
	private HashMap<State, Double> possibilities = new HashMap<State, Double>();
	
	public void add(State s, Double p) {
		this.possibilities.put(s,p);
	}
	
	public Set<State> getStates() {
		Set<State> ret = this.possibilities.keySet();
		return ret;
	}
	
	public Double getProbability(State s) {
		return this.possibilities.get(s);
	}
	
	public int size() {
		return this.possibilities.size();
	}
}
