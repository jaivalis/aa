package statespace;

import environment.Coordinates;
import environment.Algorithms;
import environment.ProbableTransitions;
import environment.Algorithms.action;
import policy.Policy;
import state.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
    public abstract Algorithms.action getTransitionAction(State s, State s_prime);

    public abstract void initializeStateValues(double d);

    public abstract void printActions(Policy policy);
    /****************************** /abstract functions ****************************/


    /**
     * returns the new State that occurs after predator takes action a.
     */
    public abstract State getNextState(State s, Algorithms.action a);

    /**
     * Returns the Reward that would be acquired if action a was taken in State s.
     * @param s; Current state.
     * @param a; Action to be taken.
     */
    public double getActionReward(State s, Algorithms.action a) {
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
    public abstract ProbableTransitions getProbableTransitions(State s, Algorithms.action a);

    public State produceStochasticTransition(State s, Algorithms.action a) {
		Random rand = new Random();
		float randfl = rand.nextFloat();
		ProbableTransitions probableTransitions = this.getProbableTransitions(s, a);
		Iterator<State> itS = probableTransitions.getStates().iterator();
		float sum = 0.0f;
		State currS = null;
		do {
			if(!itS.hasNext()){
				// Exception is not thrown, just printed with stacktrace and program is aborted
				new Exception("probabilities do not seem to sum up to 1.0").printStackTrace();
				System.exit(0);
			}
			currS = itS.next();
			sum += probableTransitions.getProbability(currS);
		} while(randfl > sum);
		return currS;
    }

	public abstract ArrayList<State> getNeighbors(State s);

    /**
     * Returns a random state from the StateSpace used for Q-Learing.
     */
    public abstract State getRandomState();

    protected abstract int length();
}
