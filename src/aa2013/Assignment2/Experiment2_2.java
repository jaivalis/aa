package aa2013.Assignment2;

import environment.Algorithms;
import environment.Q;
import environment.Util;
import policy.EpsilonGreedyPolicy;
import statespace.ReducedStateSpace;
import statespace.StateSpace;

/**
 * Second Experiment exercise 2
 */
public class Experiment2_2 {

    /**
     * Experiment on Q-Learning:
     *      + different epsilon values for ε-Greedy learning.
     *      + different initial Q values.
     *  - alpha is set to 0.8
     *  - gamma is set to 0.8
     * which are the the optimal values obtained from the previous experiment.
     *
     * Outputs to stdout an array with latex-type format to be included to the report.
     */
    public static void main(String[] args) {
        StateSpace ss = new ReducedStateSpace();
        Algorithms algos = new Algorithms(ss);

        double simulations = 100;  // many simulations ensure higher precision.
        double savedEpsilon = Util.epsilon; // FIXME: dirty hack!

        double alpha = 0.8; // as found to be optimal in previous task.
        double gamma = 0.8; // as found to be optimal in previous task.
        for (double epsilon = 0.1; epsilon <= 0.95; epsilon += 0.1) {
            Util.epsilon = epsilon;
            EpsilonGreedyPolicy egp = new EpsilonGreedyPolicy(algos.getStateSpace()); // Predator learn with variant ε.
            for (float initialQValue = 15; initialQValue >= -15; initialQValue -= 15) {
            	for(int episodeCount = 0; episodeCount < Util.EPISODE_COUNT; episodeCount += 50) {
            		// 1. train
            		Util.epsilon = savedEpsilon; // we need a stochastic epsilon policy for the learning, for exploration
            		Q newQ = algos.Q_Learning(egp, initialQValue, alpha, gamma, Util.EPISODE_COUNT);
            		Util.epsilon = 0.0; // now it has already learned, so we can use a stochastic policy
            		((EpsilonGreedyPolicy) algos.getPredator().getPolicy()).setQ(newQ);

            		// 2. simulate & output results
            		double averageRounds = algos.getSimulationAverageRounds(simulations);
            		System.out.print(averageRounds + " & ");
            	}System.out.println("\\\\");
            }
        }
    }
}
