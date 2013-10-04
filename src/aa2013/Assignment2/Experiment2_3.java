package aa2013.Assignment2;

import environment.Algorithms;
import environment.Q;
import environment.Util;
import policy.QEpsilonGreedyPolicy;
import policy.SoftmaxPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

/**
 * Third Experiment exercise 2
 */
public class Experiment2_3 {

    /**
     * Experiment on Softmax:
     *      + different α learning rates.
     *      + different γ discount factors.
     *  - epsilon is set to 0.1
     *  - initialQValue is optimistically set to 15.0
     *
     * Outputs to stdout an array with latex-type format to be included to the report.
     */
    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);

        double optimisticInitialQ = 15;
        double gamma = 0.9;
        double alpha = 0.5;
        double simulations = 100;  // many simulations ensure higher precision.
        SoftmaxPolicy smp = new SoftmaxPolicy(algos.getStateSpace()); // Predator learn

        double savedEpsilon = Util.epsilon; // FIXME: dirty hack!
        	for (double tau = 0.1; tau <= 0.9; tau += 0.1) {
               	for(int episodeCount = 50; episodeCount < Util.EPISODE_COUNT; episodeCount += 50) {
            		// 1. train
               		Util.tau = tau;
            		Util.epsilon = savedEpsilon; // we need a stochastic epsilon policy for the learning, for exploration
            		Q newQ = algos.Q_Learning(smp, optimisticInitialQ, alpha, gamma, episodeCount);
            		Util.epsilon = 0.0; // now it has already learned, so we can use a stochastic policy
            		((QEpsilonGreedyPolicy) algos.getPredator().getPolicy()).setQ(newQ);

            		// 2. simulate & output results
            		double averageRounds = algos.getSimulationAverageRounds(simulations);
            		System.out.println(tau +", "+averageRounds);
            	}
            } System.out.println("\\\\");
        }
}