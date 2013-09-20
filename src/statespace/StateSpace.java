package statespace;

import action.PreyAction;
import environment.Coordinates;
import environment.Environment;
import environment.ProbableTransitions;
import environment.State;
import policy.Policy;

public abstract class StateSpace {
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
    /****************************** \abstract functions ****************************/


    /**
     * returns the new State that occurs after predator takes action a.
     */
    public State getNextState(State s, Environment.action a) {
        Coordinates predNew = s.getPredatorCoordinates();
        predNew = predNew.getShifted(a);
        return this.getState(s.getPreyCoordinates(), predNew);
    }

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
    public ProbableTransitions getProbableTransitions(State s, Environment.action a) {
        ProbableTransitions ret = new ProbableTransitions();
        // the first two index are for the predator
        Coordinates predatorNewPos = s.getPredatorCoordinates().getShifted(a);
        // the last two indexes are for the prey
        Coordinates preyCurrPos = s.getPreyCoordinates();

        // where could the prey be going?
        for(Environment.action act : Environment.action.values()) {
            PreyAction tmp = new PreyAction();
            double p = tmp.getActionProbability(act);
            Coordinates preyPossiblePos = preyCurrPos.getShifted(act);
            ret.add(this.getState(predatorNewPos, preyPossiblePos), p);
        }
        return ret;
    }
}
