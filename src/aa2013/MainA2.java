package aa2013;

import environment.Environment;
import environment.Q;
import policy.EpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;
import statespace.CompleteStateSpace;

public class MainA2 {

    public static void main(String[] args) {
        StateSpace ss = new CompleteStateSpace();

        Environment env = new Environment(ss);
        EpsilonGreedyPolicy pi = new EpsilonGreedyPolicy(ss);
        env.Q_Learning(pi);
    }

}
