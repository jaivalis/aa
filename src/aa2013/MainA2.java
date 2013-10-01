package aa2013;

import environment.Algorithms;
import environment.Q;
import policy.EpsilonGreedyPolicy;
import policy.SoftmaxPolicy;
import statespace.CompleteStateSpace;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

public class MainA2 {

    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();

        Algorithms algos = new Algorithms(ss);
        EpsilonGreedyPolicy pi = new EpsilonGreedyPolicy(ss);
        Q q = algos.Q_Learning(pi);
//        System.out.println(q);
//        q.printMaxActionsGrid();
        algos.evaluateQLearning();
    }
}
