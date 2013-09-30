package policy;

import action.PossibleActions;
import environment.Environment;
import environment.Q;
import environment.Util;
import state.State;
import statespace.StateSpace;

/**
 * SoftMax policy tries to vary the action probabilities as a graded function of estimated value. The greedy action
 * is still given the highest selection probability, but all the others are ranked and weighted according to their
 * value estimates. These are called softmax action selection rules. The most common softmax method uses a Gibbs, or
 * Boltzmann, distribution. It chooses action 'a' the t-th play with probability:
 * exp(Q(a) / tau) / sum{ exp(Q(b) / tau) }
 *
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
    public Environment.action getAction(State s) {

        if(this.q == null) {
            new Exception("SoftmaxPolicy: this.q is not set. Please call setQ before!").printStackTrace();
            System.exit(0);
        }
        // TODO

        return null;
    }
}
