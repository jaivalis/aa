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
    public Environment.action getAction(State s) {
        PossibleActions possibleActions = this.stateActionMapping.get(s);

        double numerator, denominator, prob;
        for (PossibleActions pa : possibleActions) {

            numerator = Math.exp();

            denominator = 0;
            prob = numerator / denominator;
            pa.setActionProbability(prob);
        }

        // set all probabilities to epsilon divided by number of actions


        // find maximum action and set it to the greedy probability value (1 - epsilon + epsilon/size(A))
        Environment.action max_a = this.q.getArgmaxA(s);
        double greedy_prob = 1 - Util.epsilon + epsilon_frac;
        possibleActions.setActionProbability(max_a, greedy_prob);

        // stochastic query to get action for state s
        return super.getAction(s);
    }
}
