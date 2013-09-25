package statespace;

import environment.Coordinates;
import environment.Environment;
import environment.ProbableTransitions;
import policy.Policy;
import state.State;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class StateSpace implements Iterable<State>, Iterator<State>  {
    /****************************** abstract functions ****************************/
    /**
     * Called from the constructor. Initializes the state Container
     */
    protected abstract void initStates();

    /**
     * Returns the state the corresponds to the grid defined by the arguments.
     * @param i; Prey x coordinate.
     * @param j; Prey y coordinate.
     * @param k; Predator x coordinate.
     * @param l; Predator y coordinate.
     */
    public abstract State getState(Coordinates preyC, Coordinates predC);

    /**
     * Returns the action required for prey to move from state s to state s_prime.
     * @param s
     * @param s_prime
     * @return
     */
    public abstract Environment.action getTransitionAction(State s, State s_prime);

    public abstract void initializeStateValues(double d);

    public abstract void printActions(Policy policy);
    /****************************** /abstract functions ****************************/


    /**
     * returns the new State that occurs after predator takes action a.
     */
    public abstract State getNextState(State s, Environment.action a);

    /**
     * Returns the Reward that would be acquired if action a was taken in State s.
     * @param s; Current state.
     * @param a; Action to be taken.
     */
    public double getActionReward(State s, Environment.action a) {
        State newState = this.getNextState(s, a);
        return newState.getStateReward();
    }

    /**
     * returns a structure that contains all possible states associated
     * with the predator in state s, wanting to perform action a
     * @param s state of the predator
     * @param a action that the predator might perform
     * @return ProbableTransition a structure containing possible states and probabilities
     */
    public abstract ProbableTransitions getProbableTransitions(State s, Environment.action a);

	public abstract ArrayList<State> getNeighbors(State s);

    /**
     * Returns a random state from the StateSpace used for Q-Learing.
     */
    public abstract State getRandomState();

    protected abstract int getStateSpaceSize();
}
