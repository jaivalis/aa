package environment;

import java.util.HashMap;

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
	
	public State[] getStates() {
		return (State[]) this.possibilities.keySet().toArray();
	}
	
	public Double getProbability(State s) {
		return this.possibilities.get(s);
	}
}
