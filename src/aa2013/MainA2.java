package aa2013;

import environment.Algorithms;
import environment.Q;
import policy.EpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;
import statespace.CompleteStateSpace;

public class MainA2 {

    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();

        Algorithms env = new Algorithms(ss);
        EpsilonGreedyPolicy pi = new EpsilonGreedyPolicy(ss);
        Q q = env.Q_Learning(pi);
        q.print();
    }
}
