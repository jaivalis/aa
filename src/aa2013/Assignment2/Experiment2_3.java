package aa2013.Assignment2;

import environment.Algorithms;
import environment.Q;
import environment.Util;
import policy.EpsilonGreedyPolicy;
import policy.SoftmaxPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

/**
 * Third Experiment exercise 2
 */
public class Experiment2_3 {

    /**
     * Experiment on Sarsa:
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
        double simulations = 100;  // many simulations ensure higher precision.
        SoftmaxPolicy smp = new SoftmaxPolicy(algos.getStateSpace()); // Predator learn

        double savedEpsilon = Util.epsilon; // FIXME: dirty hack!
        for (double gamma = 0.1; gamma <= 1.0; gamma += 0.2) {
        	for (double alpha = 0.1; alpha <= 0.6; alpha += 0.1) {
               	for(int episodeCount = 0; episodeCount < Util.EPISODE_COUNT; episodeCount += 50) {
            		// 1. train
            		Util.epsilon = savedEpsilon; // we need a stochastic epsilon policy for the learning, for exploration
            		Q newQ = algos.Q_Learning(smp, optimisticInitialQ, alpha, gamma, episodeCount);
            		Util.epsilon = 0.0; // now it has already learned, so we can use a stochastic policy
            		((EpsilonGreedyPolicy) algos.getPredator().getPolicy()).setQ(newQ);

            		// 2. simulate & output results
            		double averageRounds = algos.getSimulationAverageRounds(simulations);
            		System.out.print(averageRounds + " & ");
            	}
            } System.out.println("\\\\");
        }
    }
}