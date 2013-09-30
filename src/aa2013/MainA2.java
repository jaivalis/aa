package aa2013;

import environment.Environment;
import environment.Q;
import policy.EpsilonGreedyPolicy;
import policy.SoftmaxPolicy;
import statespace.CompleteStateSpace;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

public class MainA2 {

    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();

        Environment env = new Environment(ss);
        SoftmaxPolicy pi = new SoftmaxPolicy(ss);
        Q q = env.Q_Learning(pi);
        q.print();
    }
}
