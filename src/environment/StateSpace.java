package environment;

import java.util.Iterator;
import java.util.NoSuchElementException;

import action.PreyAction;
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
	 * returns a structure that contains all possible states associated
	 * with the predator in state s, wanting to perform action a
	 * @param s state of the predator
	 * @param a action that the predator might perform
	 * @return ProbableTransition a structure containing possible states and probabilities
	 */
	public ProbableTransitions getProbableTransitions(State s, action a) {
		ProbableTransitions ret = new ProbableTransitions();
		// the first two index are for the predator
		Coordinates predatorNewPos = s.getPredatorCoordinates().shift(a);
		// the last two indexes are for the prey
		Coordinates preyCurrPos = s.getPreyCoordinates();
		
		// where could the prey be going?
		for(action act : Environment.action.values()) {
			PreyAction tmp = new PreyAction();
			double p = tmp.getActionProbability(a);
			Coordinates preyPossiblePos = preyCurrPos.shift(a);
			ret.add(this.getState(predatorNewPos, preyPossiblePos), p);
		}
		return ret;
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
