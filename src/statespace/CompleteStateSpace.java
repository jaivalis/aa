package statespace;

import environment.Coordinates;
import environment.Environment.action;
import environment.State;
import environment.Util;
import policy.Policy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompleteStateSpace extends StateSpace implements Iterable<State>, Iterator<State> {
	private State[/*preyX*/][/*preyY*/][/*predX*/][/*predY*/] states;
	private int iter_pos = 0;
	
	public CompleteStateSpace() {
		this.states = new State[Util.DIM][Util.DIM][Util.DIM][Util.DIM];
		this.initStates(); 
	}

    @Override
	protected void initStates() {
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

    @Override
	public State getState(Coordinates preyC, Coordinates predC) {
        return this.states[preyC.getX()][preyC.getY()][predC.getX()][predC.getY()];
    }

    @Override
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
    @Override
	public double getActionReward(State s, action a) {
		State newState = this.getNextState(s, a);
		return newState.getStateReward();
	}
    @Override
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
