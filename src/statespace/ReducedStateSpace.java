package statespace;

import environment.Coordinates;
import environment.Environment;
import environment.ProbableTransitions;
import environment.Util;
import policy.Policy;
import state.ReducedState;
import state.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * For this StateSpace we make the assumption that the predator stays in a fixed position and
 * only the prey moves.
 */
public class ReducedStateSpace extends StateSpace implements Iterable<State>, Iterator<State> {
    private int iter_pos;
    private ReducedState[][] states;

    public ReducedStateSpace() {
        this.states = new ReducedState[Util.DIM][Util.DIM];
        this.initStates();
    }

    @Override
    protected void initStates() {
        for (int i = 0; i < Util.DIM; i++) {
            for (int j = 0; j < Util.DIM; j++) {
                Coordinates preyC = new Coordinates(i, j);
                this.states[i][j] = new ReducedState(preyC); // predCoordinates fixed to (0,0)
            }
        }
    }

    @Override
    public ReducedState getState(Coordinates preyC, Coordinates predC) {
        return this.states[preyC.getX()][preyC.getY()];
    }

    @Override
    public Environment.action getTransitionAction(State s, State s_prime) {
        ReducedState rs = (ReducedState) s;
        ReducedState rs_prime = (ReducedState) s_prime;
        return rs.getTransitionAction(rs_prime);
    }

    @Override
    public void initializeStateValues(double d) {
        for (State s : this) { s.setStateValue(d); }
    }

    @Override
    public void printActions(Policy policy) {
        // TODO
    }

    @Override
    public State getNextState(State s, Environment.action a) {
        // TODO
        ReducedState rs = (ReducedState) s;
        return null;
    }

    @Override
    public ProbableTransitions getProbableTransitions(State s, Environment.action a) {
        // TODO
        return null;
    }

    /******************************* Iterator Related ************************************/
    @Override
    public Iterator iterator() {
        this.resetIterator();
        return this;
    }

    private void resetIterator() { this.iter_pos = 0; }

    @Override
    public boolean hasNext() { return this.iter_pos < (Math.pow(Util.DIM, 2)); }

    @Override
    public ReducedState next() {
        if(this.hasNext()){
            int tmp = this.iter_pos;
            tmp = tmp / Util.DIM;
            int j = tmp % Util.DIM;
            tmp = tmp / Util.DIM;
            int i = tmp % Util.DIM;
            this.iter_pos++;
            return this.states[i][j];
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() { }
    /******************************* Iterator Related ************************************/

	@Override
	public ArrayList<State> getNeighbors(State s) {
		// TODO Auto-generated method stub
		return null;
	}
}
