package environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import policy.Policy;
import environment.Environment.action;

public class StateSpace implements Iterable<State>, Iterator<State> {

	private State[/*preyX*/][/*preyY*/][/*predX*/][/*predY*/] states;
	
	private int iter_pos = 0;
	
	public StateSpace() {
		this.states = new State[Util.DIM][Util.DIM][Util.DIM][Util.DIM];
		this.initStates(); 
	}

	private void initStates() {
		for(int i = 0; i < Util.DIM; i++) {
			for(int j = 0; j < Util.DIM; j++) {
				for(int k = 0; k < Util.DIM; k++) {
					for(int l = 0; l < Util.DIM; l++) {
						states[i][j][k][l] = new State(i, j, k, l);
					}
				}
			}
		}
	}
	
	public void setStateValue(int i, int j, int k, int l, double stateValue) {
		this.states[i][j][k][l].setStateValue(stateValue);
	}
	
	public State getState(int i, int j, int k, int l) { return this.states[i][j][k][l]; }
	public State getState(Coordinates preyC, Coordinates predC) { return this.states[preyC.getX()][preyC.getY()][predC.getX()][predC.getY()]; }

	/**
	 * returns the new State that occurs after predator takes action a.
	 */
	public State getNextState(State s, action a) {
		Coordinates predNew = s.getPredatorCoordinates();
		predNew = predNew.shift(a);
		return this.getState(s.getPreyCoordinates(), predNew);
	}
	
	/** 
	 * Returns the action required for prey to move from state s to state s_prime.
	 * @param s
	 * @param s_prime
	 * @return
	 */
	public action getTransitionAction(State s, State s_prime) {
		Coordinates c = s.getPredatorCoordinates();
		Coordinates c_prime = s_prime.getPredatorCoordinates();
		return c.getTransitionAction(c_prime);
	}
	
	/**
	 * Returns all the possible adjacent states to state s.
	 * @param s
	 * @return
	 */
	public ArrayList<State> getNeighbors(State s) {
		ArrayList<State> ret = new ArrayList<State>();
		for (action a : action.values()) {
			ret.add(this.getNextState(s, a));
		}
		return ret;
	}
	
	public double getActionReward(State s, action a) {
		State newState = this.getNextState(s, a);
		return newState.getStateReward();
	}
	
	public void initializeStateValues(double d) {
		for (State s : this) { s.setStateValue(0.0); }
	}
	
	public void printActions(Policy policy) {
		//TODO
	}
	
	/******************************* Iterator Related ************************************/
	@Override
	public Iterator<State> iterator() {
		this.resetIterator();
		return this;
	}

	private void resetIterator() { this.iter_pos = 0; }
	
	@Override
	public boolean hasNext() { return this.iter_pos < (Math.pow(Util.DIM, 4)); }

	@Override
	public State next() {
		if(this.hasNext()){
			int tmp = this.iter_pos;
			int l = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int k = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int j = tmp % Util.DIM;
			tmp = (int)(tmp / Util.DIM);
			int i = tmp % Util.DIM;
			this.iter_pos++;
			return this.states[i][j][k][l];
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() { }
	/******************************* Iterator Related ************************************/
}
