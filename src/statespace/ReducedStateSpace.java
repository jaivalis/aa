package statespace;

import environment.Coordinates;
import environment.Environment;
import environment.State;
import environment.Util;
import policy.Policy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReducedStateSpace extends StateSpace implements Iterable<State>, Iterator<State> {
    private int iter_pos;
    private State[][] states;

    public ReducedStateSpace() {
        this.states = new State[Util.DIM][Util.DIM];
        this.initStates();
    }

    @Override
    protected void initStates() {
        for (int i = 0; i < Util.DIM; i++) {
            for (int j = 0; j < Util.DIM; j++) {
                this.states[i][j] = new State();
            }
        }
    }

    @Override
    public State getState(Coordinates preyC, Coordinates predC) {
        return null;  // TODO
    }

    @Override
    public Environment.action getTransitionAction(State s, State s_prime) {
        return null;  // TODO
    }

    @Override
    public void initializeStateValues(double d) {
        // TODO
    }

    @Override
    public double getActionReward(State s, Environment.action a) {
        return 0;  // TODO
    }

    @Override
    public void printActions(Policy policy) {
        // TODO
    }

    /******************************* Iterator Related ************************************/
    @Override
    public Iterator<State> iterator() {
        this.resetIterator();
        return this;
    }

    private void resetIterator() { this.iter_pos = 0; }

    @Override
    public boolean hasNext() { return this.iter_pos < (Math.pow(Util.DIM, 2)); }

    @Override
    public State next() {
        if(this.hasNext()){
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() { }
    /******************************* Iterator Related ************************************/
}
