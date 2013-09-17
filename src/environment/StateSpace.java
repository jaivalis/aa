package environment;

import java.util.Iterator;

public class StateSpace implements Iterable<State> {

	private State[][][][] states;
	
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
	
	public void setStateSpace(int i, int j, int k, int l, double stateValue) {
		this.states[i][j][k][l].setStateValue(stateValue);
	}
	
	public State getState(int i, int j, int k, int l) {
		return this.states[i][j][k][l];
	}

	@Override
	public Iterator<State> iterator() {
		return null;
	}
}
