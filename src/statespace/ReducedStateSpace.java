package statespace;

import environment.Coordinates;
import environment.Environment;
import environment.State;
import environment.Util;
import policy.Policy;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReducedStateSpace extends StateSpace implements Iterable<State>, Iterator<State> {

    public ReducedStateSpace() {

    }

    @Override
    protected void initStates() {
        // TODO
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
