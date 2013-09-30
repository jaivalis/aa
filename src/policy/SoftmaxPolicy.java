package policy;

import action.PossibleActions;
import action.StateAction;
import environment.Algorithms;
import environment.Q;
import environment.Util;
import state.State;
import statespace.StateSpace;

import java.util.HashSet;

/**
 * SoftMax policy tries to vary the action probabilities as a graded function of estimated value. The greedy action
 * is still given the highest selection probability, but all the others are ranked and weighted according to their
 * value estimates. These are called SoftMax action selection rules. The most common SoftMax method uses a Gibbs, or
 * Boltzmann, distribution. It chooses action 'a' the t-th play with probability:
 *
 * exp(Q_t(a) / tau) / sum{ exp(Q_t(b) / tau) }
 * tau = temperature
 */
public class SoftmaxPolicy extends PredatorPolicy {
    Q q = null;

    public SoftmaxPolicy(StateSpace ss) {
        super(ss);
    }

    public void setQ(Q q) {
        this.q = q;
    }

    @Override
    public Algorithms.action getAction(State s) {
        HashSet<StateAction> stateActions = this.q.getStateActions(s);
        PossibleActions possibleActions = this.stateActionMapping.get(s);

        double numerator, denominator = 0, prob;

        for (StateAction pa : stateActions) {
            denominator += Math.exp(q.get(pa.getS(), pa.getA()) / Util.tau);
        }

        for (StateAction pa : stateActions) {
            numerator = Math.exp(q.get(pa.getS(), pa.getA()) / Util.tau);
            prob = numerator / denominator;

            possibleActions.setActionProbability(pa.getA(), prob); // action probability to prob.
        }

        // stochastic query to get action for state s
        return super.getAction(s);
    }
}
