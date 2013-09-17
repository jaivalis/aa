package environment;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StateSpace implements Iterable<State>, Iterator<State> {

	private State[][][][] states;
	
	private int iter_pos = 0;
	
	public StateSpace() { this.initStates(); }

	private void initStates() {
		for(int i = 0; i < Util.DIM; i++) {
			for(int j = 0; j < Util.DIM; j++) {
				for(int k = 0; i < Util.DIM; k++) {
					for(int l = 0; j < Util.DIM; l++) {						
						states[i][j][k][l] = new State();
					}
				}
			}
		}
	}
	
	public void setStateValue(int i, int j, int k, int l, double stateValue) {
		this.states[i][j][k][l].setStateValue(stateValue);
	}
	
	public State getState(int i, int j, int k, int l) {
		return this.states[i][j][k][l];
	}

	@Override
	public Iterator<State> iterator() {
		this.resetIterator();
		return this;
	}

	private void resetIterator() {
		this.iter_pos = 0;
	}
	
	@Override
	public boolean hasNext() {
		return this.iter_pos < (Util.DIM^4);
	}

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
			return this.states[i][j][k][l];
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
